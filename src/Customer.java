public class Customer {
  private static final int REGULAR_SATISFACTION_LOSS_PER_TURN = 8;
  // Zong Nan: I added a new MAX_SATISFACTION_SCORE
  private static final int MAX_SATISFACTION_SCORE = 100;

  private int customerId;
  private int satisfactionScore;
  private boolean isSeated;
  private boolean isOrderTaken;
  private boolean isPaid;
  private boolean hasLeft;
  private Order currentOrder;
  private Task currentTask;

  public Customer(int customerId, int satisfactionScore) {
    this.customerId = customerId;
    this.satisfactionScore = satisfactionScore;
    this.isPaid = false; 
    this.isSeated = false;
    this.isOrderTaken = false;
    Task.TaskType taskType = Task.TaskType.SEAT_CUSTOMER;
    this.currentTask = new Task(taskType);
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getSatisfactionScore() {
    return satisfactionScore;
  }

  public Order getCurrentOrder() {
    return currentOrder;
  }

  public Task getCurrentTask() {
    return currentTask;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean isPaid) {
    this.isPaid = isPaid;
  }

  public void setCurrentOrder(Order currentOrder) {
    this.currentOrder = currentOrder;
    this.isOrderTaken = currentOrder != null;
    if (currentTask != null) {
      currentTask.setOrder(currentOrder);
    }
  }

  public boolean isOrderTaken() {
    return isOrderTaken;
  }

  public boolean hasLeft() {
    return hasLeft;
  }

  // Satisfaction lost at the end of every turn spent waiting for service (Regular customer)
  public int getSatisfactionLossPerTurn() {
    return REGULAR_SATISFACTION_LOSS_PER_TURN;
  }

  // ----- Behaviour each customer type can override (polymorphism) -----
  public String getTypeName() {
    return "Regular";
  }

  // Regular customers pick from the menu (seeded random)
  public MenuItem chooseMenuItem(Menu menu) {
    return menu.getRandomMenuItem();
  }

  // What this customer pays, in cents. Regular customers pay full price.
  public int calculateBill(int fullPriceCents) {
    return fullPriceCents;
  }

  // True while the customer is waiting to be seated, to order, or for their dish to be served.
  // Customers who have been served (eating or ready to pay), paid, or left are not waiting.
  public boolean isAwaitingService() {
    return !hasLeft && !isPaid && currentTask != null
        && currentTask.getTaskType() != Task.TaskType.PROCESS_PAYMENT;
  }

  // Applies the end-of-turn satisfaction loss, never going below 0
  public void loseSatisfaction() {
    satisfactionScore = Math.max(0, satisfactionScore - getSatisfactionLossPerTurn());
  }

  /**
   * Add satisfaction back, up to 100. HappyHour calls this for customers who are waiting.
   */
  public void recoverSatisfaction(int amount) {
    satisfactionScore = Math.min(satisfactionScore + amount, MAX_SATISFACTION_SCORE);
  }

  // The customer walks out without paying; they no longer have any task
  public void leave() {
    hasLeft = true;
    currentTask = null;
  }

  public void advaceToNextTask() {
    if (currentTask != null) {
      currentTask.advanceToNextTaskType();
      if (currentTask.isCompleted()) {
        currentTask = null; // No more tasks for this customer
      }
    }
  }
}
