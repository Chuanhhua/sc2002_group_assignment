import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    // Objectives the player must meet by the end of the last turn to win
    private static final int REQUIRED_PAID_CUSTOMERS = 4;
    private static final int REQUIRED_REVENUE_CENTS = 4500; // $45.00
    private static final double REQUIRED_AVERAGE_SATISFACTION = 60.0;

    private final int noOfChefs;
    private final int noOfWaiters;
    private final int noOfCashiers;

    private final int maxTurns;
    private final Menu menu;
    private final ConsoleUI consoleUI = new ConsoleUI();
    private int totalRevenueCents;
    private Table[] tables;
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Customer> waitingCustomers = new ArrayList<>();
    private List<Order> allOrders = new ArrayList<>();

    public GameController(int maxTurns, int noOfTables, int noOfChefs, int noOfWaiters, int noOfCashiers,
                          int menuSize) {

        this.noOfChefs = noOfChefs;
        this.noOfWaiters = noOfWaiters;
        this.noOfCashiers = noOfCashiers;

        this.maxTurns = maxTurns;
        this.tables = new Table[noOfTables];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new Table();
        }
        this.menu = new Menu(menuSize);
    }

    public GameController(boolean isDefault) {
    this(18, 2, 1, 1, 1, 4);

    menu.addMenuItem("Salad", 900, 1);
    menu.addMenuItem("Burger", 1500, 1);
    menu.addMenuItem("Pasta", 1800, 2);

    MenuItem[] items = menu.getMenuItems();
    menu.addComboMeal(items[0], items[2]);
    }


    // ====== Turn Sequence ====

    public void run() {
        consoleUI.showGameIntro(maxTurns, menu.getMenuItems(), REQUIRED_PAID_CUSTOMERS, REQUIRED_REVENUE_CENTS, REQUIRED_AVERAGE_SATISFACTION);

        // Adds VIPCustomer on the 4th turn,, CriticCustomer on the 7th turn, and regular customers on the 1st, 10th, 13th, and 16th turns
        for (int turn = 1; turn <= maxTurns; turn++) {
            consoleUI.showTurnHeader(turn, maxTurns);

            if (turn <= 16 && (turn - 1) % 3 == 0) {
                Customer customer;
                if (turn == 4) {
                    customer = new VIPCustomer(allCustomers.size() + 1, 100);
                } else if (turn == 7) {
                    customer = new CriticCustomer(allCustomers.size() + 1, 100);
                } else {
                    customer = new Customer(allCustomers.size() + 1, 100);
                }
                allCustomers.add(customer);
                waitingCustomers.add(customer);
                consoleUI.showArrival(customer);
            }
            consoleUI.showTurnStatus(turn, totalRevenueCents, getPaidCustomerCount(), getAveragePaidSatisfactionScore(),
                    waitingCustomers, tables);
            consoleUI.showStaffTasks(waitingCustomers.size(), countFreeTables(), getTablesReadyToOrder().size(),
                    getOrdersReadyToServe().size(), getOrdersToCook().size(), countCustomersReadyToPay(turn));

            // Steps 2-3: the Waiter acts before the Chef
            runWaiterPhase(turn);
            runChefPhase();

            // Step 4: eating and payment
            runCashierPhase(turn);

            // Step 5: waiting customers lose satisfaction, then show the end-of-turn results
            consoleUI.showPhase("END OF TURN " + turn);
            int[] satisfactionBefore = recordSatisfaction();
            runSatisfactionPhase();
            showSatisfactionChanges(satisfactionBefore);
            consoleUI.showTurnResults(turn, maxTurns, getServedCount(), getDepartedCount(), totalRevenueCents,
                    getPaidCustomerCount(), getAveragePaidSatisfactionScore());
        }

        // Step 6: result
        int paidCustomerCount = getPaidCustomerCount();
        double averagePaidSatisfaction = getAveragePaidSatisfactionScore();
        consoleUI.showGameSummary(maxTurns, allCustomers, totalRevenueCents, paidCustomerCount, averagePaidSatisfaction,
                REQUIRED_PAID_CUSTOMERS, REQUIRED_REVENUE_CENTS, REQUIRED_AVERAGE_SATISFACTION);
        boolean victory = paidCustomerCount >= REQUIRED_PAID_CUSTOMERS && totalRevenueCents >= REQUIRED_REVENUE_CENTS
                && averagePaidSatisfaction >= REQUIRED_AVERAGE_SATISFACTION;
        consoleUI.showFinalResult(victory);
    }

    // ======= Waiter =======
    // Keeps asking until Waiter does a valid task (or waits)
    // A failed or cancelled choice does not use up the Waiter's task.
    private void runWaiterPhase(int turn) {
        for (int i = 0; i < noOfWaiters; i++) {
            consoleUI.showPhase(noOfWaiters > 1 ? "WAITER " + (i + 1) : "WAITER");
            boolean isWaiterTasked = false;
            while (!isWaiterTasked) {
                int taskChoice = consoleUI.getWaiterTaskChoice(waitingCustomers.size(), countFreeTables(),
                        getTablesReadyToOrder().size(), getOrdersReadyToServe().size());
                switch (taskChoice) {
                    case 0:
                        consoleUI.showMessage("The Waiter waits this turn.");
                        isWaiterTasked = true;
                        break;
                    case 1:
                        isWaiterTasked = seatNextCustomer();
                        break;
                    case 2:
                        isWaiterTasked = takeOrder();
                        break;
                    case 3:
                        isWaiterTasked = serveOrder(turn);
                        break;
                    default:
                        consoleUI.showWarning("Invalid choice. Please try again.");
                }
            }
        }
    }

    // Seats the customer at the front of the queue at the first free table
    private boolean seatNextCustomer() {
        if (waitingCustomers.isEmpty()) {
            consoleUI.showWarning("No customers are waiting to be seated. Choose another task.");
            return false;
        }
        for (int i = 0; i < tables.length; i++) {
            if (!tables[i].isOccupied()) {
                Customer customerToSeat = waitingCustomers.remove(0);
                tables[i].occupyTable(customerToSeat);
                customerToSeat.advaceToNextTask();
                consoleUI.showSuccess("Customer " + customerToSeat.getCustomerId() + " is seated at Table "
                        + (i + 1) + ".");
                return true;
            }
        }
        consoleUI.showWarning("All tables are occupied. Choose another task.");
        return false;
    }

    private boolean takeOrder() {
        List<Integer> tablesReadyToOrder = getTablesReadyToOrder();
        if (tablesReadyToOrder.isEmpty()) {
            consoleUI.showWarning("No seated customer is ready to order. Choose another task.");
            return false;
        }
        int tableIndex = consoleUI.getTableChoice(tables, tablesReadyToOrder, "take the order from");
        if (tableIndex < 0) {
            return false; // cancelled, the Waiter can still choose a task
        }
        Customer orderingCustomer = tables[tableIndex].getCurrentCustomer();
        // The customer object decides what it orders and what it pays (no type checks here).
        // The price is locked now, when the order is taken; every order contains exactly 1 dish.
        MenuItem orderedItem = orderingCustomer.chooseMenuItem(menu);
        int fullPriceCents = orderedItem.getPriceCents();
        int priceCents = orderingCustomer.calculateBill(fullPriceCents);
        Order order = new Order(orderedItem, 1, priceCents);
        allOrders.add(order);
        // Attach the order before advancing so PREPARE_ORDER picks up its preparation units
        orderingCustomer.setCurrentOrder(order);
        orderingCustomer.advaceToNextTask();
        String price = ConsoleUI.money(priceCents);
        if (priceCents != fullPriceCents) {
            price += ", was " + ConsoleUI.money(fullPriceCents);
        }
        consoleUI.showSuccess("Customer " + orderingCustomer.getCustomerId() + " (" + orderingCustomer.getTypeName()
                + ") at Table " + (tableIndex + 1) + " orders " + orderedItem.getItemName() + " (" + price + ", "
                + orderedItem.getPreparationUnits() + " prep unit" + (orderedItem.getPreparationUnits() == 1 ? "" : "s")
                + ").");
        return true;
    }

    // Only dishes that were already READY before the Waiter acts can be served,
    // because the Chef always acts after the Waiter.
    private boolean serveOrder(int turn) {
        List<Order> readyOrders = getOrdersReadyToServe();
        if (readyOrders.isEmpty()) {
            consoleUI.showWarning("No dishes are ready to serve. Choose another task.");
            return false;
        }
        List<Customer> readyOrderCustomers = new ArrayList<>();
        for (Order readyOrder : readyOrders) {
            readyOrderCustomers.add(findCustomerByOrder(readyOrder));
        }
        int orderIndex = consoleUI.getOrderChoice(readyOrders, readyOrderCustomers, "Which dish do you want to serve?",
                "Cancel - choose another task");
        if (orderIndex < 0) {
            return false; // cancelled, the Waiter can still choose a task
        }
        Order orderToServe = readyOrders.get(orderIndex);
        Customer servedCustomer = readyOrderCustomers.get(orderIndex);
        orderToServe.setServed(true);
        orderToServe.setServedTurn(turn);
        servedCustomer.advaceToNextTask();
        consoleUI.showSuccess("Served " + orderToServe.getMenuItem().getItemName() + " to Customer "
                + servedCustomer.getCustomerId() + ". They eat next turn and pay the turn after.");
        return true;
    }

    // ===================== Chef =====================

    // Each Chef advances one order by 1 preparation unit, or waits
    private void runChefPhase() {
        List<Order> ordersWorkedOn = new ArrayList<>();
        for (int i = 0; i < noOfChefs; i++) {
            consoleUI.showPhase(noOfChefs > 1 ? "CHEF " + (i + 1) : "CHEF");
            List<Order> ordersToPrepare = new ArrayList<>();
            List<Customer> ordersToPrepareCustomers = new ArrayList<>();
            for (Order existingOrder : getOrdersToCook()) {
                // Skip orders another chef already worked on this turn
                if (!ordersWorkedOn.contains(existingOrder)) {
                    ordersToPrepare.add(existingOrder);
                    ordersToPrepareCustomers.add(findCustomerByOrder(existingOrder));
                }
            }
            if (ordersToPrepare.isEmpty()) {
                consoleUI.showMessage("Nothing to cook. The Chef waits.");
                continue;
            }
            int orderIndex = consoleUI.getOrderChoice(ordersToPrepare, ordersToPrepareCustomers,
                    "Which order should the Chef cook?", "Wait");
            if (orderIndex < 0) {
                consoleUI.showMessage("The Chef waits this turn.");
                continue;
            }
            Order orderToPrepare = ordersToPrepare.get(orderIndex);
            Customer orderingCustomer = ordersToPrepareCustomers.get(orderIndex);
            // The customer's PREPARE_ORDER task advances the order by 1 unit and moves to SERVE_ORDER when done
            orderingCustomer.advaceToNextTask();
            ordersWorkedOn.add(orderToPrepare);
            String dish = orderToPrepare.getMenuItem().getItemName();
            if (orderToPrepare.isPrepared()) {
                consoleUI.showSuccess(dish + " for Customer " + orderingCustomer.getCustomerId()
                        + " is READY. It can be served from next turn.");
            } else {
                consoleUI.showSuccess("Cooked 1 unit of " + dish + " for Customer " + orderingCustomer.getCustomerId()
                        + " (" + orderToPrepare.getRemainingPreparationUnits() + " left).");
            }
        }
    }

    // ===================== Cashier =====================

    // Each Cashier automatically takes payment from the oldest customer who has finished eating.
    // A customer served in turn T eats during turn T + 1 and can only pay from turn T + 2.
    private void runCashierPhase(int turn) {
        for (int i = 0; i < noOfCashiers; i++) {
            consoleUI.showPhase(noOfCashiers > 1 ? "CASHIER " + (i + 1) + " (automatic)" : "CASHIER (automatic)");
            Customer payingCustomer = findOldestCustomerReadyToPay(turn);
            if (payingCustomer == null) {
                consoleUI.showMessage("No customer is ready to pay.");
                continue;
            }
            Order paidOrder = payingCustomer.getCurrentOrder();
            int paymentCents = paidOrder.getPriceCents();
            totalRevenueCents += paymentCents;
            payingCustomer.setPaid(true);
            paidOrder.setPaid(true);
            payingCustomer.advaceToNextTask();
            int tableNumber = freeTableOf(payingCustomer);
            consoleUI.showSuccess("Customer " + payingCustomer.getCustomerId() + " paid "
                    + ConsoleUI.money(paymentCents) + " (satisfaction " + payingCustomer.getSatisfactionScore()
                    + " recorded). Table " + tableNumber + " is free.");
        }
    }

    // ===================== Satisfaction =====================

    // At turn end, every waiting or seated customer who has not been served loses satisfaction.
    // A customer who reaches 0 leaves without paying: their unfinished order is cancelled and
    // their table is freed. Customers who are served, eating or ready to pay lose nothing.
    private void runSatisfactionPhase() {
        for (Customer customer : allCustomers) {
            if (!customer.isAwaitingService()) {
                continue;
            }
            customer.loseSatisfaction();
            if (customer.getSatisfactionScore() > 0) {
                continue;
            }
            customer.leave();
            waitingCustomers.remove(customer);
            Order unfinishedOrder = customer.getCurrentOrder();
            if (unfinishedOrder != null) {
                // Removing it stops chefs from preparing it and waiters from serving it
                allOrders.remove(unfinishedOrder);
            }
            for (Table table : tables) {
                if (table.getCurrentCustomer() == customer) {
                    table.vacateTable();
                    break;
                }
            }
            System.out.println("Customer " + customer.getCustomerId()
                    + " ran out of patience and left without paying.");
        }
    }

    // Remembers every customer's satisfaction before runSatisfactionPhase() changes it,
    // so the end-of-turn display can show who lost how much (e.g. 100 -> 92).
    private int[] recordSatisfaction() {
        int[] satisfaction = new int[allCustomers.size()];
        for (int i = 0; i < allCustomers.size(); i++) {
            satisfaction[i] = allCustomers.get(i).getSatisfactionScore();
        }
        return satisfaction;
    }

    private void showSatisfactionChanges(int[] satisfactionBefore) {
        for (int i = 0; i < satisfactionBefore.length; i++) {
            Customer customer = allCustomers.get(i);
            if (customer.getSatisfactionScore() < satisfactionBefore[i]) {
                consoleUI.showSatisfactionLoss(customer, satisfactionBefore[i], customer.getSatisfactionScore());
            }
        }
    }
    // ===================== Queries used by the phases and the display =====================

    private int countFreeTables() {
        int count = 0;
        for (Table table : tables) {
            if (!table.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    private List<Integer> getTablesReadyToOrder() {
        List<Integer> tablesReadyToOrder = new ArrayList<>();
        for (int i = 0; i < tables.length; i++) {
            if (tables[i].isOccupied()) {
                Task task = tables[i].getCurrentCustomer().getCurrentTask();
                if (task != null && task.getTaskType() == Task.TaskType.TAKE_ORDER) {
                    tablesReadyToOrder.add(i);
                }
            }
        }
        return tablesReadyToOrder;
    }

    private List<Order> getOrdersReadyToServe() {
        List<Order> readyOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.isPrepared() && !order.isServed()) {
                readyOrders.add(order);
            }
        }
        return readyOrders;
    }

    private List<Order> getOrdersToCook() {
        List<Order> ordersToCook = new ArrayList<>();
        for (Order order : allOrders) {
            if (!order.isPrepared()) {
                ordersToCook.add(order);
            }
        }
        return ordersToCook;
    }

    // allCustomers is in arrival order, so the first match is the oldest customer
    private Customer findOldestCustomerReadyToPay(int turn) {
        for (Customer customer : allCustomers) {
            if (isReadyToPay(customer, turn)) {
                return customer;
            }
        }
        return null;
    }

    private int countCustomersReadyToPay(int turn) {
        int count = 0;
        for (Customer customer : allCustomers) {
            if (isReadyToPay(customer, turn)) {
                count++;
            }
        }
        return count;
    }

    private boolean isReadyToPay(Customer customer, int turn) {
        Order order = customer.getCurrentOrder();
        return !customer.isPaid() && order != null && order.isServed() && turn - order.getServedTurn() >= 2;
    }

    // Frees the table the customer is sitting at and returns its number (1-based), or -1 if none
    private int freeTableOf(Customer customer) {
        for (int i = 0; i < tables.length; i++) {
            if (tables[i].getCurrentCustomer() == customer) {
                tables[i].vacateTable();
                return i + 1;
            }
        }
        return -1;
    }

    private Customer findCustomerByOrder(Order order) {
        for (Customer customer : allCustomers) {
            if (customer.getCurrentOrder() == order) {
                return customer;
            }
        }
        return null;
    }

    // ===================== Results =====================

    public int getTotalRevenueCents() {
        return totalRevenueCents;
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

    // Customers whose dish has been served (including those who have since paid)
    public int getServedCount() {
        int count = 0;
        for (Customer customer : allCustomers) {
            Order order = customer.getCurrentOrder();
            if (order != null && order.isServed()) {
                count++;
            }
        }
        return count;
    }

    // Customers who ran out of patience and left without paying
    public int getDepartedCount() {
        int count = 0;
        for (Customer customer : allCustomers) {
            if (customer.hasLeft()) {
                count++;
            }
        }
        return count;
    }

    // Uses the satisfaction recorded when each customer paid (it no longer changes after serving).
    // Returns 0.0 when nobody has paid; ConsoleUI shows that case as N/A.
    public double getAveragePaidSatisfactionScore() {
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

    public int getPaidCustomerCount() {
        int paidCustomerCount = 0;
        for (Customer customer : allCustomers) {
            if (customer.isPaid()) {
                paidCustomerCount++;
            }
        }
        return paidCustomerCount;
    }
}
