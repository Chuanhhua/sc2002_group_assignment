import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            
            int taskChoice = consoleUI.getWaiterTaskChoice();
            boolean isWaiterTasked = false;
            while (isWaiterTasked){
                switch (taskChoice) {
                    case 0:
                        // Wait (skip this turn)
                        break;
                    case 1:
                        // Seat a customer
                        if (!waitingCustomers.isEmpty()) {
                            for (Table table : tables) {
                                if (!table.isOccupied()) {
                                    Customer customerToSeat = waitingCustomers.get(0);
                                    table.occupyTable(customerToSeat);
                                    customerToSeat.advaceToNextTask();
                                    isWaiterTasked = true;
                                    waitingCustomers.remove(0);
                                    System.out.println("Customer " + customerToSeat.getCustomerId() + " has been seated.");
                                    break;
                                }
                            }
                            if (!isWaiterTasked) {
                                System.out.println("No available tables to seat the customer.");
                            }
                        } else {
                            System.out.println("No customers are waiting to be seated.");
                        }
                        break;
                    case 2:
                        // Take an order
                        for (Table table : tables) {
                            if (table.isOccupied() && table.getCustomer().getCurrentTask() == null) {
                                Order order = new Order(table.getCustomer(), menu);
                                allOrders.add(order);
                                table.getCustomer().setCurrentTask(new Task(Task.TaskType.TAKE_ORDER));
                                break;
                            }
                        }
                        break;
                    case 3:
                        // Serve a dish
                        for (Chef chef : chefs) {
                            if (chef.hasPreparedDish()) {
                                Order orderToServe = chef.getPreparedOrder();
                                for (Table table : tables) {
                                    if (table.isOccupied() && table.getCustomer().equals(orderToServe.getCustomer())) {
                                        table.getCustomer().setCurrentTask(new Task(Task.TaskType.SERVE_ORDER));
                                        chef.clearPreparedOrder();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    case 4:
                        // Process payment
                        for (Table table : tables) {
                            if (table.isOccupied() && table.getCustomer().getCurrentTask() == null) {
                                double paymentAmount = table.getCustomer().getTotalBill();
                                totalRevenue += paymentAmount;
                                table.getCustomer().setPaid(true);
                                table.clearTable();
                                break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

}
