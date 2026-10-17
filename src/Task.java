public class Task {
  public enum TaskType {
    SEAT_CUSTOMER,
    TAKE_ORDER,
    PREPARE_ORDER,
    SERVE_ORDER,
    PROCESS_PAYMENT;

    public TaskType next() {
      switch (this) {
        case SEAT_CUSTOMER:
          return TAKE_ORDER;
        case TAKE_ORDER:
          return PREPARE_ORDER;
        case PREPARE_ORDER:
          return SERVE_ORDER;
        case SERVE_ORDER:
          return PROCESS_PAYMENT;
        case PROCESS_PAYMENT:
          return null;
        default:
          throw new IllegalStateException("Unexpected task type: " + this);
      }
    }
  }

  private TaskType taskType;
  private boolean isCompleted;
  private int preparationUnitsRemaining;
  private Order order;

  public Task(TaskType taskType) {
    this.taskType = taskType;
    this.isCompleted = false;
    this.preparationUnitsRemaining = 0;
  }

  public int getPreparationUnitsRemaining() {
    return preparationUnitsRemaining;
  }

  public void setPreparationUnitsRemaining(int preparationUnitsRemaining) {
    this.preparationUnitsRemaining = preparationUnitsRemaining;
  }

  public TaskType getTaskType() {
    return taskType;
  }

  public void advanceToNextTaskType() {
    // If the task is already completed, we don't need to advance to the next task
    // type
    if (isCompleted) {
      return;
    }

    // If currrent task is PREPARE_ORDER, we need to handle the preparation units
    if (taskType == TaskType.PREPARE_ORDER) {
      // If there are still preparation units remaining, we decrement the count
      if (preparationUnitsRemaining > 1) {
        preparationUnitsRemaining--;
        order.advanceUnit();
        return;
      }
      // If there is only 1 preparation unit remaining, we decrement the count and
      // mark the order as prepared by calling advanceUnit() on the order
      else if (preparationUnitsRemaining == 1) {
        preparationUnitsRemaining--;
        order.advanceUnit();
        taskType = taskType.next();
        return;
      }
    }
    // If the current task is not PREPARE_ORDER, we simply move to the next task
    // type since there are no preparation units to handle
    else {
      TaskType nextTaskType = taskType.next();
      if (nextTaskType == null) {
        isCompleted = true;
        return;
      }
      taskType = nextTaskType;
      // If the next task is PREPARE_ORDER, we need to set the preparation units
      if (taskType == TaskType.PREPARE_ORDER) {
        if (order != null) {
          preparationUnitsRemaining = order.getRemainingPreparationUnits();
        }
      } else {
        preparationUnitsRemaining = 0;
      }
    }
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }

  public String toString() {
    return "Task{" +
        "taskType=" + taskType +
        ", isCompleted=" + isCompleted +
        '}';
  }
}
