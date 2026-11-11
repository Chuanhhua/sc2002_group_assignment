public class CriticCustomer extends Customer {
    public CriticCustomer(int customerId, int satisfactionScore){
        super(customerId, satisfactionScore);
    }
    
    @Override
    public int getSatisfactionLossPerTurn() {
        return 12;
    }
}