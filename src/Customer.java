public class Customer {
  private static final int REGULAR_SATISFACTION_LOSS_PER_TURN = 8;

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

  public void setSatisfactionScore(int satisfactionScore) {
    this.satisfactionScore = satisfactionScore;
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

  public int getSatisfactionLossPerTurn() {
    return REGULAR_SATISFACTION_LOSS_PER_TURN;
  }

  public boolean isAwaitingService() {
    return !hasLeft && !isPaid && currentTask != null && currentTask.getTaskType() != Task.TaskType.PROCESS_PAYMENT;
  }

  public void loseSatisfaction() {
    satisfactionScore = Math.max(0, satisfactionScore - getSatisfactionLossPerTurn());
  }

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
