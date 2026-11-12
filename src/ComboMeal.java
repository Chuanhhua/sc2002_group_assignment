/**
 * Stage 3: A combo containing two existing menu items.
 * Price is 90% of the combined price.
 * Preparation units are added together.
 */
public class ComboMeal extends MenuItem {
    private final MenuItem firstItem;
    private final MenuItem secondItem;

    public ComboMeal(MenuItem firstItem, MenuItem secondItem) {
        super(
            getComboName(firstItem, secondItem),
            getComboPrice(firstItem, secondItem),
            getComboPreparationUnits(firstItem, secondItem)
        );

        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    private static String getComboName(MenuItem first, MenuItem second) {
        validateItems(first, second);
        return first.getItemName() + " + " + second.getItemName();
    }

    private static int getComboPrice(MenuItem first, MenuItem second) {
        validateItems(first, second);
        long total = (long) first.getPriceCents() + second.getPriceCents();
        return Math.toIntExact((total * 9 + 5) / 10);
    }

    private static int getComboPreparationUnits(MenuItem first, MenuItem second) {
        validateItems(first, second);
        return Math.addExact(
            first.getPreparationUnits(),
            second.getPreparationUnits()
        );
    }

    private static void validateItems(MenuItem first, MenuItem second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Combo items cannot be null");
        }
    }

    public MenuItem getFirstItem() {
        return firstItem;
    }

    public MenuItem getSecondItem() {
        return secondItem;
    }
}
