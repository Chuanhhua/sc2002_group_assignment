public class GameController {
    private final int maxTurns;

    public GameController(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public void run() {
        for (int turn = 1; turn <= maxTurns; turn++) {
          System.out.println("Hello world! " + turn); //placeholder for game logic
        }
    }
}
