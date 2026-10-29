public class Table {
  private boolean isOccupied;
  private Customer currentCustomer;

  public Table() {
    this.isOccupied = false;
    this.currentCustomer = null;
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void occupyTable(Customer customer) {
    this.isOccupied = true;
    this.currentCustomer = customer;
  }

  public void vacateTable() {
    this.isOccupied = false;
    this.currentCustomer = null;
  }

  public Customer getCurrentCustomer() {
    return currentCustomer;
  }

  public String toString() {
    if (isOccupied) {
      return "Table is occupied by Customer ID: " + currentCustomer.getCustomerId();
    } else {
      return "Table is unoccupied.";
    }
  }
}
