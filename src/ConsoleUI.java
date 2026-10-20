import java.util.Scanner;
import java.util.List;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public void showGameIntro(int turns) {
        System.out.println("Welcome to the Restaurant Management Game!");
        System.out.println(
                "You are the manager of a restaurant, and your goal is to serve customers efficiently and maximize revenue.");
        System.out.println("You will have a limited number of turns to manage your staff and serve customers.");
        System.out.println("\nObjective by the end of turn " + turns + ":");
        System.out.println(" - At least 4 paid customers");
        System.out.println(" - Revenue of at least $45");
        System.out.println(" - Average satisfaction of paid customers of at least 60");
        System.out.println("Good luck!");
    }

    public void showTurnStart(int turn) {
        System.out.println();
        System.out.println("===== Turn " + turn + " =====");
    }

    public void showTurnStatus(int turn, double revenue, double averagePaidSatisfaction,
            List<Customer> waitingCustomers, Table[] tables, List<Customer> customers, List<Task> availableTasks) {
        // Display the turn number, revenue, average satisfaction
        showTurnStart(turn);
        System.out.printf("Revenue: $%.2f%n", revenue);
        System.out.printf("Average satisfaction of paid customers: %.2f%n", averagePaidSatisfaction);

        // Display waiting queue
        System.out.println("Waiting queue:");
        if (waitingCustomers.isEmpty()) {
            System.out.println("  Empty");
        } else {
            for (Customer customer : waitingCustomers) {
                System.out.println("  Customer " + customer.getCustomerId());
            }
        }

        // Display occupied tables
        System.out.println("Occupied tables:");
        boolean hasOccupiedTable = false;
        for (int i = 0; i < tables.length; i++) {
            if (tables[i].isOccupied()) {
                hasOccupiedTable = true;
                System.out.println("  Table " + (i + 1) + ": " + tables[i]);
            }
        }
        if (!hasOccupiedTable) {
            System.out.println("  None");
        }

        // Display order status
        System.out.println("Orders:");
        if (customers.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Customer customer : customers) {
                Order order = customer.getCurrentOrder();
                String orderStatus;
                if (order == null) {
                    orderStatus = "No order";
                } else if (order.isServed()) {
                    orderStatus = "Served";
                } else if (order.isPrepared()) {
                    orderStatus = "Prepared";
                } else {
                    orderStatus = "Preparing (" + order.getRemainingPreparationUnits()
                            + " units remaining)";
                }
                System.out.println("  Customer " + customer.getCustomerId()
                        + ": satisfaction=" + customer.getSatisfactionScore()
                        + ", order=" + orderStatus);
            }
        }

        // Display available tasks for staff
        System.out.println("Staff tasks available:");
        if (availableTasks.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Task task : availableTasks) {
                System.out.println("  " + task);
            }
        }
    }

    public void showTurnEnd(int turn) {
        System.out.println("----- End of turn " + turn + " -----");
    }

    public void showFinalResult(boolean victory) {
        System.out.println();
        System.out.println(victory ? "VICTORY!" : "DEFEAT.");
    }

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
            System.out.println("Please enter a number from " + min + " to " + max + ".");
        }
    }

    public int getWaiterTaskChoice() {
        Scanner input = new Scanner(System.in);
        System.out.println("You are now the waiter!");
        System.out.println("0. Wait (skip this turn)");
        System.out.println("1. Seat a customer");
        System.out.println("2. Take an order");
        System.out.println("3. Serve a dish");
        System.out.println("4. Process payment");
        System.out.println("Enter the number of the task you want to perform:");
        int taskChoice = input.nextInt();
        input.close();
        return taskChoice;
    }

    
}
