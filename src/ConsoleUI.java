import java.util.Scanner;
import java.util.List;

public class ConsoleUI {
    private static final int WIDTH = 64;
    private final Scanner scanner = new Scanner(System.in);

    // =========== formatting helpers =========
    private static String line(char c) {
        return String.valueOf(c).repeat(WIDTH);
    }
    // centralise texts
    private static String center(String text){
        int padding = Math.max(0, (WIDTH - text.length())/2);
        return " ".repeat(padding) + text;
    }
    // format money values to 2dp
    public static String money(double amount){
        return String.format("$%.2f", amount);
    }

    // showing the average satisfaction only once someone has paid; before that would show N/A
    private static String average(double averageSatisfaction, int paidCustomerCount){
        return paidCustomerCount == 0 ? "N/A" : String.format("%.1f", averageSatisfaction);
    }

    // format plural words
    private static String plural(int count, String word){
        return count + " " + word + (count == 1 ? "" : "s");
    }

    // a thin divider that announces which role is doing the work now
    public void showPhase(String title){
        System.out.println();
        System.out.println("--- " + title + " " + "-".repeat(Math.max(0, WIDTH - title.length() - 5)));
    }

    // usual information
    public void showMessage(String message){
        System.out.println(" " + message);
    }

    // confirmation message for the player
    public void showSuccess(String message){
        System.out.println("\t[OK] " + message);
    }

    // show warning message for wrong/invalid choice
    public void showWarning(String message){
        System.out.println("\t[!] " + message);
    }

    // =========== Game Start ============
    public void showGameIntro(int turns, MenuItem[] menuItems, int requiredPaidCustomers, double requiredRevenue,
                              double requiredAverageSatisfaction) {
        System.out.println(line('='));
        System.out.println(center("RESTAURANT RUSH"));
        System.out.println(line('='));
        System.out.println("Welcome to the Restaurant Management Game!");
        System.out.println("You are the manager of a restaurant, and your goal is to serve customers efficiently and maximize revenue.");
        System.out.println();
        for (MenuItem item : menuItems){
            if (item != null){
                System.out.printf("     %-8s %7s     %s%n", item.getItemName(), money(item.getPrice()), plural(item.getPreparationUnits(), "prep unit"));
            }
        }
        System.out.println();
        System.out.println("Objective by the end of turn " + turns + ":");
        System.out.println(" - At least " + requiredPaidCustomers + " paid customers");
        System.out.println(" - Revenue of at least " + money(requiredRevenue));
        System.out.printf(" - Average satisfaction of paid customers of at least %.0f%n", requiredAverageSatisfaction);
        System.out.println("Good luck!");
    }

    // ========= start of turn ===========

    public void showTurnHeader(int turn, int maxTurns) {
        System.out.println();
        System.out.println();
        System.out.println(line('='));
        System.out.println(center("TURN " + turn + " of " + maxTurns));
        System.out.println(line('='));
    }

    public void showArrival(Customer customer){
        System.out.println("\t[+] Customer " + customer.getCustomerId() + " arrives and joins the waiting queue.");
    }

    public void showTurnStatus(int turn, double revenue, int paidCustomerCount, double averagePaidSatisfaction,
                               List<Customer> waitingCustomers, Table[] tables) {
        System.out.println();
        // Display the turn number, revenue, average satisfaction
        System.out.println("\tRevenue: " + money(revenue) + "\t|\tPaid: " + paidCustomerCount + "\t|\tAverage satisfaction of paid customers: " + average(averagePaidSatisfaction, paidCustomerCount));
        System.out.println();
        // Display waiting queue
        System.out.println("\tWAITING QUEUE:");
        if (waitingCustomers.isEmpty()) {
            System.out.println("\t\t(Empty)");
        } else {
            for (Customer customer : waitingCustomers) {
                System.out.printf("\t\tCustomer %-3d  Satisfaction [%3d] -- Waiting for a table%n", customer.getCustomerId(), customer.getSatisfactionScore());
            }
        }

        System.out.println();

        // Display occupied tables
        System.out.println("\tTables:");
        for (int i = 0; i < tables.length; i++) {
            if (!tables[i].isOccupied()) {
                System.out.println("\t\tTable " + (i + 1) +" : (free)");
                continue;
            }
            Customer customer = tables[i].getCurrentCustomer();
            System.out.printf("\t\tTable %d : Customer %3d  Satisfaction %3d   %s%n", i+1, customer.getCustomerId(), customer.getSatisfactionScore(), describeStatus(customer, turn));
        }

    }

    // One short phrase for where a seated customer is in the flow
    private String describeStatus(Customer customer, int turn) {
        Order order = customer.getCurrentOrder();
        if (order == null) {
            return "Ready to order";
        }
        String dish = order.getMenuItem().getItemName();
        if (order.isPaid()) {
            return dish + ": paid";
        }
        if (order.isServed()) {
            // Served on turn T: eats during T+1, ready to pay from T+2
            return turn - order.getServedTurn() >= 2 ? dish + ": READY TO PAY" : dish + ": eating";
        }
        if (order.isPrepared()) {
            return dish + ": READY to serve";
        }
        return dish + ": in kitchen (" + plural(order.getRemainingPreparationUnits(), "unit") + " left)";
    }

    public void showStaffTasks(int waitingCount, int freeTableCount, int readyToOrderCount,
                               int readyToServeCount, int toCookCount, int readyToPayCount){
        System.out.println();
        System.out.println("\tSTAFF TASKS AVAILABLE");
        System.out.println("\tWaiter : seat " + (waitingCount > 0 && freeTableCount > 0 ? "yes" : "no")
                + "\t|\ttake order " + readyToOrderCount + "\t|\tserve " + readyToServeCount);
        System.out.println("\tChef   : " + plural(toCookCount, "order") + " to cook");
        System.out.println("\tCashier: " + plural(readyToPayCount, "customer") + " ready to pay (automatic)");
    }

    // ======= Input =======
    // Keeps asking until the player enters a whole number between min and max
    public int promptInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            showWarning("Please enter a number from " + min + " to " + max + ".");
        }
    }

    public int getWaiterTaskChoice(int waitingCount, int freeTableCount, int readyToOrderCount, int readyToServeCount) {
        System.out.println("You are now the waiter!");
        System.out.println("\t0. Wait (skip this turn)");
        System.out.printf("\t1. Seat a customer (%d waiting, %s)%n", waitingCount, plural(freeTableCount, "free table"));
        System.out.printf("\t2. Take an order   (%s ready)%n", plural(readyToOrderCount, "table"));
        System.out.printf("\t3. Serve a dish    (%d ready)%n", readyToServeCount);
        // Reuse the shared scanner; closing a Scanner on System.in would break later input
        return promptInt("Enter choice [0-3]: ", 0, 3);
    }

    // Returns the index of the chosen table, only accepting tables in eligibleTables.
    // action describes what the waiter is doing, e.g. "take the order from"
    public int getTableChoice(Table[] tables, List<Integer> eligibleTables, String action) {
        System.out.println("\tTables available to " + action + ":");
        System.out.println("\t\t0. Cancel - choose another task");
        for (int index : eligibleTables) {
            System.out.println("\t\t" + (index + 1) + ". Table " + (index + 1) + " (Customer "
                    + tables[index].getCurrentCustomer().getCustomerId() + ")");
        }
        while (true) {
            int tableNumber = promptInt("Enter Choice: ", 0, tables.length);
            if (tableNumber == 0) {
                return -1;
            }
            if (eligibleTables.contains(tableNumber - 1)) {
                return tableNumber - 1;
            }
            showWarning("Cannot " + action + " Table " + tableNumber + ".");
        }
    }

    // Returns the index of the chosen order in orders; customers[i] is the customer who placed orders[i].
    // action describes what is being done to the order, e.g. "serve"
    public int getOrderChoice(List<Order> orders, List<Customer> customers, String question, String zeroOption) {
        System.out.println("\t" + question);
        System.out.println("\t\t0. " + zeroOption);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String line = "\t\t" + (i + 1) + ". Customer " + customers.get(i).getCustomerId() + ": "
                    + order.getMenuItem().getItemName();
            if (!order.isPrepared()) {
                line += " (" + plural(order.getRemainingPreparationUnits(), "unit") + " left)";
            }
            System.out.println(line);
        }
        return promptInt("Choice [0-" + orders.size() + "]: ", 0, orders.size()) - 1;
    }

    // ===== END OF TURN =====

    public void showSatisfactionLoss(Customer customer, int before, int after) {
        String state = customer.hasLeft() ? "has left" : "is still waiting";
        System.out.println("\tCustomer " + customer.getCustomerId() + " " + state + ": satisfaction "
                + before + " -> " + after);
    }

    public void showTurnResults(int turn, int maxTurns, int servedCount, int departedCount, double revenue,
                                int paidCustomerCount, double averagePaidSatisfaction) {
        System.out.println();
        System.out.println("\tServed: " + servedCount
                + "\t|\tLeft unhappy: " + departedCount
                + "\t|\tRevenue: " + money(revenue)
                + "\t|\tAvg satisfaction: " + average(averagePaidSatisfaction, paidCustomerCount)
                + "\t|\tTurns used: " + turn + "/" + maxTurns);
    }

    // ====== GAME END =====


    public void showGameSummary(int turns, List<Customer> customers, double revenue, int paidCustomerCount,
                                double averagePaidSatisfaction, int requiredPaidCustomers, double requiredRevenue,
                                double requiredAverageSatisfaction) {
        System.out.println();
        System.out.println();
        System.out.println(line('='));
        System.out.println(center("GAME OVER - SUMMARY"));
        System.out.println(line('='));
        System.out.println("\tTurns played: " + turns + "\t|\tCustomers arrived: " + customers.size());
        System.out.println();
        System.out.println("\tCUSTOMERS:");
        if (customers.isEmpty()) {
            System.out.println("\t\tNone");
        }
        for (Customer customer : customers) {
            Order order = customer.getCurrentOrder();
            Task task = customer.getCurrentTask();
            String status;
            if (customer.hasLeft()) {
                status = "Left without paying";
            } else if (customer.isPaid()) {
                status = String.format("Paid $%.2f for %s", order.getTotalPrice(), order.getMenuItem().getItemName());
            } else if (task != null) {
                status = "Unpaid, " + task.toString().toLowerCase();
            } else {
                status = "Unpaid";
            }
            System.out.printf("\tCustomer %-3d  Satisfaction %3d  %s%n", customer.getCustomerId(),
                    customer.getSatisfactionScore(), status);
        }
        System.out.println();
        System.out.println("\tOBJECTIVES: ");
        System.out.printf("\t\tPaid customers  %8s / %-8s %s%n", paidCustomerCount, requiredPaidCustomers,
                objectiveStatus(paidCustomerCount >= requiredPaidCustomers));
        System.out.printf("\t\tRevenue  %8s / %-8s %s%n", money(revenue), money(requiredRevenue),
                objectiveStatus(revenue >= requiredRevenue));
        System.out.printf("\t\tAvg satisfaction  %8s / %-8.0f %s%n",
                average(averagePaidSatisfaction, paidCustomerCount), requiredAverageSatisfaction,
                objectiveStatus(paidCustomerCount > 0 && averagePaidSatisfaction >= requiredAverageSatisfaction));
    }

    private String objectiveStatus(boolean isMet) {
        return isMet ? " [MET]" : " [NOT MET]";
    }

    public void showFinalResult(boolean victory) {
        System.out.println();
        System.out.println(line('*'));
        System.out.println(center(victory ? "VICTORY! All three targets met." : "DEFEAT. Not all targets were met."));
        System.out.println(line('*'));
    }

}