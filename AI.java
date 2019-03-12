/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/11/2019
 */

 

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class AI extends Player{

  // This is an array of CPU player names
  public static String[] cpuName = {"AdventurousAlonzo", "ButcherBoone", "CleverClayton", "DickheadDallas", "EasyEarle", "FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio", "ProspectorPatrick", "MagnificentMick", "SpeedyGonzales"};

  /**
   * This constructor will generate random names for the number of CPU players
   * This takes the argument of the contructor and chooses random names for the CPU players
   * and removes the name if it is chosen for the CPU so there are no duplicate CPU players
   */
  public AI() {
    Random name = new Random();

    int rName = name.nextInt(Array.getLength(cpuName));
    super.name = cpuName[rName];
    cpuName = ArrayUtils.removeElement(cpuName, rName);
  }

  // This method pushes the AI decisions to Player class
  public void getDecision() {
    // This implements the randomness of the AI
    Random choice = new Random();
    int decision = choice.nextInt(1); // TMPORARY CHANGE

    Random betting = new Random();

    // AI randomly checks as a decision
    if (decision == 0) {
      super.check("C");
    }

    // AI randomly folds as a decision
    else if (decision == 1) {
      super.fold("F");
    }

    // THIS COMMENT IS TEMPORARY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /*else if (decision == 2) {
      checkAIBets(decision, super.getStack());
      int bet = betting.nextInt(super.getStack());
      super.BetRaise("B", bet);
    }

    else if (decision == 3) {
      checkAIBets(decision, super.getStack());
      int bet = betting.nextInt(super.getStack());
      super.BetRaise("R", bet);
    }

    // THIS LINE WILL NOT RUN PROPERLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    else if (decision == 4) {
      super.call("L");
    }

    // THIS LINE WILL NOT RUN PROPERLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    else if (decision == 5) {
      super.allIn("A");
    }*/

  }

  // This method will randomly bet and check if AI has sufficient money
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
        checkAIBets(playDecision, money);
      }

      else if (betting == money) {
        checkAIBets(playDecision, money);
      }

      else {
        return betting;
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
        checkAIBets(playDecision, money);
      }

      else if (betting == money) {
        checkAIBets(playDecision, money);
      }

      else {
        return betting;
      }
    }
    return 0; // THIS WILL ONLY BE USED TO DETERMINE IF THIS METHOD IS WORKING OR NOT
  }

}
