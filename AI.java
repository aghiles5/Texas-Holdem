import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the decisions that the AI commits
 * 
 * @author John Lowie
 * @version 02/19/2019
 */

public class AI extends SuperPlayer{
  private int card_1;
  private int card_2;

  public ArrayList<Integer> setCards() {
    // adds cards to AI's list
    hand.add(card_1);
    hand.add(card_2);
    return hand;
  }

  // Accessor to retrieve AI's cards
  public ArrayList<Interger> getCards() {
    return hand;
  }

  // Not sure if accessor requires to return anything
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
      // super.allIn("A");
    }

  }

}
