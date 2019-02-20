import java.util.ArrayList;
import java.util.Random;

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

  public void decisionAI() {
    // Will implement random choice that SuperPlayer will decide on AI choice
  }

}
