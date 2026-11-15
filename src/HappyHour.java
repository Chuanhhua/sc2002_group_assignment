import java.util.List;

/**
 * Happy Hour is a discount that the manager can switch on once per game
 *
 * Covers the turn it is switched on and the turn after.
 * An order taken in that window costs 80% of what the customer would otherwise pay,
 * and customers waiting for a table or for their food will get 15 satisfaction
 * back at the start of each of the two turns.
 *
 * Earlier orders keep their price, because Order stores it when the order is taken
 *
 */
public class HappyHour{
    /**
     * Define the variables
     */
    private static final int DURATION_TURNS = 2;
    private static final int PERCENT_TO_BE_PAID = 80;
    private static final int RECOVERY_SATISFACTION = 15;

    private boolean hasBeenUsed;
    private int remainingTurns;

    // Can the happyhour be activated?
    public boolean canBeUsed(){
        return !hasBeenUsed;
    }

    /**
     * Switches Happy Hour on, and throws if it has been used before
     * The UI only offers it while hasBeenUsed is true
     */
    public void activate() {
        if (hasBeenUsed) {
            throw new IllegalStateException("Happy Hour has already been used");
        }
        hasBeenUsed = true;
        remainingTurns = DURATION_TURNS;
    }

    public boolean isActive(){
        return remainingTurns > 0;
    }

    /**
     * Returns the number of turns remaining in the Happy Hour.
     */
    public int getRemainingTurns() {
        return remainingTurns;
    }

    /**
     * Takes 20% off while Happy Hour is active. Pass in the price, after the customer's own discount,
     * This ensures that the VIP's 10% comes first. Rounds up to the whole cent.
     * @param fullPriceCents
     */
    public int applyTo(int fullPriceCents){
        if (!isActive()){
            return fullPriceCents;
        }
        // Math ceiling rounds up to the nearest whole number, so we can use it to round up to the nearest cent
        return (int) Math.round(fullPriceCents * PERCENT_TO_BE_PAID / 100.0);
    }
    /**
     * Gives 15 satisfaction to every customer who is still waiting. isAwaitingService() is false
     * for customers who have left, paid or been served.
     * so they are skipped.
     */
    public void recoverWaitingCustomers(List<Customer> customers){
        if (!isActive()){
            return;
        }
        customers.stream()
                .filter(Customer::isAwaitingService)
                .forEach(customer -> customer.recoverSatisfaction(RECOVERY_SATISFACTION));
    }
    public void endTurn(){
        if (remainingTurns > 0){
            remainingTurns--;
        }
    }
}


