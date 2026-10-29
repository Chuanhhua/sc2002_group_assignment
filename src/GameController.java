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

    private Customer findCustomerByOrder(Order order) {
        for (Customer customer : allCustomers) {
            if (customer.getCurrentOrder() == order) {
                return customer;
            }
        }
        return null;
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
                    waitingCustomers, tables, allCustomers);
            
            // Keep asking until the waiter performs a valid task, so the turn only advances then
            boolean isWaiterTasked = false;
            while (!isWaiterTasked) {
                int taskChoice = consoleUI.getWaiterTaskChoice();
                switch (taskChoice) {
                    case 0:
                        // Wait (skip this turn)
                        isWaiterTasked = true;
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
                        List<Integer> tablesReadyToOrder = new ArrayList<>();
                        for (int i = 0; i < tables.length; i++) {
                            if (tables[i].isOccupied()) {
                                Task task = tables[i].getCurrentCustomer().getCurrentTask();
                                if (task != null && task.getTaskType() == Task.TaskType.TAKE_ORDER) {
                                    tablesReadyToOrder.add(i);
                                }
                            }
                        }
                        if (tablesReadyToOrder.isEmpty()) {
                            System.out.println("No occupied tables are waiting to order.");
                            break;
                        }
                        int tableIndex = consoleUI.getTableChoice(tables, tablesReadyToOrder, "take the order from");
                        Customer orderingCustomer = tables[tableIndex].getCurrentCustomer();
                        // The customer picks their dish at random; every order contains exactly 1 of it
                        MenuItem orderedItem = menu.getRandomMenuItem();
                        Order order = new Order(orderedItem, 1);
                        allOrders.add(order);
                        // Attach the order before advancing so PREPARE_ORDER picks up its preparation units
                        orderingCustomer.setCurrentOrder(order);
                        orderingCustomer.advaceToNextTask();
                        isWaiterTasked = true;
                        System.out.println("Order taken from Customer " + orderingCustomer.getCustomerId() + " at Table "
                                + (tableIndex + 1) + ": " + orderedItem.getItemName() + ".");
                        break;
                    case 3:
                        // Serve a dish
                        List<Order> preparedOrders = new ArrayList<>();
                        for (Order existingOrder : allOrders) {
                            if (existingOrder.isPrepared() && !existingOrder.isServed()) {
                                preparedOrders.add(existingOrder);
                            }
                        }
                        if (preparedOrders.isEmpty()) {
                            System.out.println("No orders are ready to be served.");
                            break;
                        }
                        List<Customer> preparedOrderCustomers = new ArrayList<>();
                        for (Order preparedOrder : preparedOrders) {
                            preparedOrderCustomers.add(findCustomerByOrder(preparedOrder));
                        }
                        int orderIndex = consoleUI.getOrderChoice(preparedOrders, preparedOrderCustomers, "serve");
                        Order orderToServe = preparedOrders.get(orderIndex);
                        Customer servedCustomer = preparedOrderCustomers.get(orderIndex);
                        orderToServe.setServed(true);
                        orderToServe.setServedTurn(turn);
                        servedCustomer.advaceToNextTask();
                        isWaiterTasked = true;
                        System.out.println("Served " + orderToServe.getMenuItem().getItemName() + " to Customer "
                                + servedCustomer.getCustomerId() + ".");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            runChefPhase();
            runCashierPhase(turn);
            consoleUI.showTurnEnd(turn);
        }
    }

    // Each chef picks one unprepared order and advances it by 1 preparation unit
    private void runChefPhase() {
        List<Order> ordersWorkedOn = new ArrayList<>();
        for (int i = 0; i < chefs.length; i++) {
            List<Order> ordersToPrepare = new ArrayList<>();
            List<Customer> ordersToPrepareCustomers = new ArrayList<>();
            for (Order existingOrder : allOrders) {
                // Skip orders another chef already worked on this turn
                if (!existingOrder.isPrepared() && !ordersWorkedOn.contains(existingOrder)) {
                    ordersToPrepare.add(existingOrder);
                    ordersToPrepareCustomers.add(findCustomerByOrder(existingOrder));
                }
            }
            if (ordersToPrepare.isEmpty()) {
                System.out.println("No orders are available to prepare.");
                consoleUI.waitForEnter("Press Enter to continue...");
                return;
            }
            consoleUI.showChefTurn(i + 1);
            int orderIndex = consoleUI.getOrderChoice(ordersToPrepare, ordersToPrepareCustomers, "prepare");
            Order orderToPrepare = ordersToPrepare.get(orderIndex);
            Customer orderingCustomer = ordersToPrepareCustomers.get(orderIndex);
            // The customer's PREPARE_ORDER task advances the order by 1 unit and moves to SERVE_ORDER when done
            orderingCustomer.advaceToNextTask();
            ordersWorkedOn.add(orderToPrepare);
            if (orderToPrepare.isPrepared()) {
                System.out.println(orderToPrepare.getMenuItem().getItemName() + " for Customer "
                        + orderingCustomer.getCustomerId() + " is ready to be served.");
            } else {
                System.out.println("Prepared 1 unit of " + orderToPrepare.getMenuItem().getItemName()
                        + " for Customer " + orderingCustomer.getCustomerId() + " ("
                        + orderToPrepare.getRemainingPreparationUnits() + " units remaining).");
            }
        }
    }

    // Each cashier automatically takes payment from the oldest customer who finished eating.
    // A customer served in turn T eats during turn T + 1 and can only pay from turn T + 2.
    private void runCashierPhase(int turn) {
        for (int i = 0; i < cashiers.length; i++) {
            Customer payingCustomer = null;
            // allCustomers is in arrival order, so the first match is the oldest customer
            for (Customer customer : allCustomers) {
                Order order = customer.getCurrentOrder();
                if (!customer.isPaid() && order != null && order.isServed() && turn - order.getServedTurn() >= 2) {
                    payingCustomer = customer;
                    break;
                }
            }
            if (payingCustomer == null) {
                System.out.println("No customers are ready to pay.");
                consoleUI.waitForEnter("Press Enter to continue...");
                return;
            }
            consoleUI.waitForEnter("Customer " + payingCustomer.getCustomerId()
                    + " is ready to pay. Press Enter to process payment...");
            Order paidOrder = payingCustomer.getCurrentOrder();
            double paymentAmount = paidOrder.getTotalPrice();
            totalRevenue += paymentAmount;
            payingCustomer.setPaid(true);
            paidOrder.setPaid(true);
            payingCustomer.advaceToNextTask();
            for (Table table : tables) {
                if (table.getCurrentCustomer() == payingCustomer) {
                    table.vacateTable();
                    break;
                }
            }
            System.out.printf("Cashier processed payment of $%.2f from Customer %d.%n", paymentAmount,
                    payingCustomer.getCustomerId());
        }
    }

}
