/**
 * The host seats one queued customer per turn.
 * This is on top of the Waiter's task
 * and the Waiter can still seat customers too!
 */

public class Host {
    // Variable to check whether the host has sat someone down
    private boolean hasSeatedInThisTurn;

    /* Calls at the start of every turn so that the Host can seat again. */
    public void startTurn(){
        hasSeatedInThisTurn = false;
    }

    public boolean canSeatCustomer(){
        return !hasSeatedInThisTurn;
    }

    /**
     * Seat customer and move them on to ordering.
     * The caller will take the customer out of the waiting q
     * @throws IllegalStateException if the host has already sat someone in this turn
     * @throws IllegalArgumentException if the table is occupied
     */
    public void seatCustomer(Customer customer, Table table){
        if (hasSeatedInThisTurn){
            throw new IllegalStateException("Host has already sat someone in this turn");
        }
        if (table.isOccupied()){
            throw new IllegalArgumentException("Table is occupied");
        }
        table.occupyTable(customer);
        customer.advaceToNextTask();
        // Change the boolean value to true so that Host cannot seat another customer in this turn
        hasSeatedInThisTurn = true;
    }
}