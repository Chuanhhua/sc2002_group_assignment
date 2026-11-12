public class VIPCustomer extends Customer {
    public VIPCustomer(int customerId, int satisfactionScore){
        super(customerId, satisfactionScore);
    }

    @Override
    public int getSatisfactionLossPerTurn() {
        return 5;
    }

    @Override
    public String getTypeName() {
        return "VIP";
    }

    // VIPs pay 10% less
    @Override
    public int calculateBill(int fullPriceCents) {
        return (int) Math.round(fullPriceCents * 0.9);
    }

}