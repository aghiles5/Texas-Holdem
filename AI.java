import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 02/20/2019
 */

public class AI extends Player{

  // This method pushes the AI decisions to SuperPlayer class
  public void getDecisionAI() {
    // This implements the randomness of the AI
    Random choice = new Random();
    int decision = choice.nextInt(6);

    if (decision == 0) {
      super.check("C");
    }

    else if (decision == 1) {
      super.fold("F");
    }

    else if (decision == 2) {
      // super.BetRaise("B", bet);
    }

    else if (decision == 3) {
      // super.BetRaise("R", bet);
    }

    else if (decision == 4) {
      // super.call("C", something, something);
    }

    else if (decision == 5) {
      super.allIn("A");
    }

  }

  // This method will randomly bet and check if AI has sufficient money
  // This method will eventually take an argument
  public int checkAIBets(/* money */) {
    Random bet = new Random();
    int betting = bet.nextInt(/* money */);

    return 0;
  }

}
