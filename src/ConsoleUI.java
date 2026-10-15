import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public void showTurnStart(int turn) {
        System.out.println();
        System.out.println("===== Turn " + turn + " =====");
    }

    public void showTurnEnd(int turn) {
        System.out.println("----- End of turn " + turn + " -----");
    }

    public void showFinalResult(boolean victory) {
        System.out.println();
        System.out.println(victory ? "VICTORY!" : "DEFEAT.");
    }

    public void showMessage(String message) {
        System.out.println(message);
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
}
