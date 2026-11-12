public class MenuItem {
  private final String itemName;
  private final int priceCents;
  private final int preparationUnits;

  public MenuItem(String itemName, int priceCents, int preparationUnits) {
    this.itemName = itemName;
    this.priceCents = priceCents;
    this.preparationUnits = preparationUnits;
  }

  public String getItemName() {
    return itemName;
  }

  public int getPriceCents() {
    return priceCents;
  }

  public int getPreparationUnits() {
    return preparationUnits;
  }

  public String toString() {
    return "MenuItem{" +
        "itemName = '" + itemName + '\'' +
        ", priceCents = $" + String.format("%.2f", priceCents) +
        ", preparationUnits = " + preparationUnits +
        '}';
  }
}
