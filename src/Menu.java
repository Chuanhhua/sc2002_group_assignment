import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Menu {
  private final Random random = new Random();
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

  public MenuItem[] getMenuItems() {
    return menuItems;
  }

  // Picks one of the added menu items at random, ignoring empty slots
  public MenuItem getRandomMenuItem() {
    List<MenuItem> availableItems = new ArrayList<>();
    for (MenuItem item : menuItems) {
      if (item != null) {
        availableItems.add(item);
      }
    }
    if (availableItems.isEmpty()) {
      return null;
    }
    return availableItems.get(random.nextInt(availableItems.size()));
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
