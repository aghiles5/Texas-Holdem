public class AI {
  /// Might change private int to public int
  
  private int card_1;
  private int card_2;
  private int[] hand = new int[2]; 
  
  public int[] setCards() {
	  hand[0] = card_1;
	  hand[1] = card_2;
	  return hand;
  }
  
  public int[] getCards() {
    
    return hand;

  }
  
  /*
  Might implement random AI decisions here but we will talk about it next meeting
  */

}
