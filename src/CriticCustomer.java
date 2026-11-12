public class CriticCustomer extends Customer {
    public CriticCustomer(int customerId, int satisfactionScore){
        super(customerId, satisfactionScore);
    }
    
    @Override
    public int getSatisfactionLossPerTurn() {
        return 12;
    }

    @Override
    public String getTypeName() {
        return "Critic";
    }

    // Special behaviour: a Critic always orders the most expensive dish
    @Override
    public MenuItem chooseMenuItem(Menu menu) {
        return menu.getMostExpensiveItem();
    }
}