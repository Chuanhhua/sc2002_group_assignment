public class MenuItem {
  private final String itemName;
  private final double price;
  private final int preparationUnits;

  public MenuItem(String itemName, double price, int preparationUnits) {
    this.itemName = itemName;
    this.price = price;
    this.preparationUnits = preparationUnits;
  }

  public String getItemName() {
    return itemName;
  }

  public double getPrice() {
    return price;
  }

  public int getPreparationUnits() {
    return preparationUnits;
  }

  public String toString() {
    return "MenuItem{" +
        "itemName = '" + itemName + '\'' +
        ", price = $" + String.format("%.2f", price) +
        ", preparationUnits = " + preparationUnits +
        '}';
  }
}
