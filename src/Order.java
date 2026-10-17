public class Order {
  private MenuItem menuItem;
  private int quantity;
  private int remainingPreparationUnits;
  private boolean isServed;
  private boolean isPrepared;

  public Order(MenuItem menuItem, int quantity) {
    this.menuItem = menuItem;
    this.quantity = quantity;
    this.isServed = false;
    this.isPrepared = false;
    this.remainingPreparationUnits = menuItem.getPreparationUnits() * quantity;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getQuantity() {
    return quantity;
  }

  public boolean isServed() {
    return isServed;
  }

  public boolean isPrepared() {
    return isPrepared;
  }

  public void setServed(boolean served) {
    isServed = served;
  }

  public int getRemainingPreparationUnits() {
    return remainingPreparationUnits;
  }

  public void advanceUnit() {
    if (remainingPreparationUnits == 1) {
      remainingPreparationUnits--;
      isPrepared = true;
    } else if (remainingPreparationUnits > 1) {
      remainingPreparationUnits--;
    }
  }
}
