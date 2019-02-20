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
      // update this call of method
      // money argument needs updating
      checkAIBets(decision, money);
      // super.BetRaise("B", bet);
    }

    else if (decision == 3) {
      // update this call of method
      // money argument needs updating
      checkAIBets(decision, money);
      // super.BetRaise("R", bet);
    }

    else if (decision == 4) {
      // super.call("C", something, something);
    }

    else if (decision == 5) {
      // Update this
      super.allIn("A", something);
    }

  }

  // This method will randomly bet and check if AI has sufficient money
  // money argument must ge retrieved from somewhere
  public int checkAIBets(int playDecision, int money) {
    Random bet = new Random();
    // int betting = bet.nextInt(money + 1);

    if (playDecision == 2) {
      int betting = bet.nextInt(money + 1);
      
      if (betting == 0) {
        // must retrieve money
        // money argument is not working
        checkAIBets(playDecision, money);
      }

      else if (betting == money) {
        checkAIBets(playDecision, money);
      }

      else {
        return 0;
      }
    }

    return 0;
  }

}
