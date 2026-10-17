public class Menu {
  private MenuItem[] menuItems;

  public Menu(int menuSize) {
    menuItems = new MenuItem[menuSize];
  }

  public void addMenuItem(String name, double price, int preparationUnits) {
    // Add the menu item to the menuItems array
    for (int i = 0; i < menuItems.length; i++) {
      if (menuItems[i] == null && !doesItemExist(name)) {
        menuItems[i] = new MenuItem(name, price, preparationUnits);
        break;
      }
    }
  }

  private boolean doesItemExist(String name) {
    for (MenuItem item : menuItems) {
      if (item != null && item.getItemName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Menu:\n");
    for (MenuItem item : menuItems) {
      if (item != null) {
        sb.append(item.toString()).append("\n");
      }
    }
    return sb.toString();
  }
}
