/**
 * Stage 3: A combo containing two existing menu items.
 * Price is 90% of the combined price.
 * Preparation units are added together.
 */

public class ComboMeal extends MenuItem {

    private MenuItem firstItem;
    private MenuItem secondItem;

    public ComboMeal(MenuItem firstItem, MenuItem secondItem) {
        super(
            firstItem.getItemName() + " + " + secondItem.getItemName(),
            calculatePrice(firstItem, secondItem),
            firstItem.getPreparationUnits() + secondItem.getPreparationUnits()
        );

        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    private static int calculatePrice(MenuItem first, MenuItem second) {
        int totalPrice = first.getPriceCents() + second.getPriceCents();
        return (int) Math.round(totalPrice * 0.9);
    }

    public MenuItem getFirstItem() {
        return firstItem;
    }

    public MenuItem getSecondItem() {
        return secondItem;
    }
}

