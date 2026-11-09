public class VIPCustomer extends Customer {
    public VIPCustomer(int customerId, int satisfactionScore){
        super(customerId, satisfactionScore);
    }

    @Override
    public int getSatisfactionLossPerTurn() {
        return 5;
    }
}