import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final int maxTurns;
    private final Chef[] chefs;
    private final Waiter[] waiters;
    private final Cashier[] cashiers;
    private final Menu menu;
    private final ConsoleUI consoleUI = new ConsoleUI();
    private double totalRevenue;
    private Table[] tables;
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Customer> waitingCustomers = new ArrayList<>();
    private List<Order> allOrders = new ArrayList<>();

    public GameController(int maxTurns, int noOfTables, int noOfChefs, int noOfWaiters, int noOfCashiers,
            int menuSize) {
        this.maxTurns = maxTurns;
        this.tables = new Table[noOfTables];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Table();
        }
        this.chefs = new Chef[noOfChefs];
        for (int i = 0; i < chefs.length; i++) {
            chefs[i] = new Chef();
        }
        this.waiters = new Waiter[noOfWaiters];
        for (int i = 0; i < waiters.length; i++) {
            waiters[i] = new Waiter();
        }
        this.cashiers = new Cashier[noOfCashiers];
        for (int i = 0; i < cashiers.length; i++) {
            cashiers[i] = new Cashier();
        }
        this.menu = new Menu(menuSize);
    }

    public GameController(boolean isDefault) {
        this(18, 2, 1, 1, 1, 3); // Default values
        menu.addMenuItem("Salad", 9.00, 1);
        menu.addMenuItem("Burger", 15.00, 1);
        menu.addMenuItem("Pasta", 18.00, 2);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public List<Customer> getWaitingCustomers() {
        return waitingCustomers;
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }

    public double getAveragePaidSatisfactionScore() {
        if (allCustomers.isEmpty()) {
            return 0.0;
        }
        int totalScore = 0;
        int paidCustomerCount = 0;
        for (Customer customer : allCustomers) {
            if (customer.isPaid()) {
                totalScore += customer.getSatisfactionScore();
                paidCustomerCount++;
            }
        }
        return paidCustomerCount > 0 ? (double) totalScore / paidCustomerCount : 0.0;
    }

    public List<Task> getAllPendingsTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Customer customer : allCustomers) {
            Task task = customer.getCurrentTask();
            if (task != null && !task.isCompleted()) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    public void run() {
        consoleUI.showGameIntro(maxTurns);
        for (int turn = 1; turn <= maxTurns; turn++) {
            if (turn <= 16 && (turn - 1) % 3 == 0) {
                Customer customer = new Customer(allCustomers.size() + 1, 100);
                allCustomers.add(customer);
                waitingCustomers.add(customer);
            }
            consoleUI.showTurnStatus(turn, totalRevenue, getAveragePaidSatisfactionScore(),
                    waitingCustomers, tables, allCustomers, getAllPendingsTasks());
        }
    }

}
