import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 02/20/2019
 */

public class AI extends Player{

  // This is an array of CPU player names
  private static String[] cpuName = {"Adventurous Alonzo", "Butcher Boone", "Clever Clayton", "Dickhead Dallas", "Easy Earle", "Frenchman Frank", "Gallant Gary", "Hearty Henry", "Idiot Ignacio", "Prospector Patrick", "Magnificent Mick"};

  // This constructor will generate random names for the number of CPU players
  public AI(int numOfAI) {
    Random name = new Random();

    /**
     * This loop takes the argument of the contructor and chooses random names for the CPU players
     * and removes the name if it is chosen for the CPU so no duplicate CPU players exists
     */
    for (int i = 0; i < numOfAI; i++) {
      int rName = name.nextInt(Array.getLength(cpuName));
      super.name = cpuName[rName];
      cpuName = ArrayUtils.removeElement(cpuName, rName);
    }
  }

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
      // update this call of method
      // money argument needs updating
      checkAIBets(decision, money);
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

    /**
     * This handle the AI's betting decisions
     * If the bets are 0 or it is the same as its stack, the method will run again 
     * until the bet is not 0 and lower than it's stack
     */
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

    /**
     * This handles the AI's raising decisions
     * If the bets are 0 or it is the same as its stack, the method will run again 
     * until the bet is not 0 and lower than it's stack
     */
    else if (playDecision == 3) {
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

    // This handles AI's call action which matches the previous player's bet
    // This statement will have to be updated in the future
    else if (playDecision == 4) {
      return 0;
    }
    
    return 0;
  }

}
