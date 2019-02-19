import java.util.ArrayList;

public class Player extends SuperPlayer{
  private int card_1;
  private int card_2;

  Player() {
	 //Use deck class to set hand
  }
  
  public ArrayList<Integer> setCards() {
	  //adds the cards to the hand list
	  hand.add(card_1);
	  hand.add(card_2);
	  return hand;
  }
  
  /// Accessor for player's card
  public ArrayList<Integer> getCards() {
    return hand;
    
  }

}
