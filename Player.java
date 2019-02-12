
public class Player {
  /// Might change private int to public int
  
  private int card_1;
  private int card_2;
  private int[] hand = new int[2];
  
  public int[] setCards() {
	  hand[0] = card_1;
	  hand[1] = card_2;
	  return hand;
  }
  
  /// Accessor for player's card
  public int[] getCards() {
    return hand;
    
  }
  
  /*
  
  Might possibly implement player decision methods into this class but we can talk about it next meeting
  
  */

}
