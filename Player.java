import java.util.ArrayList;

public class Player {
  /// Might change private int to public int
  
  private int card_1;
  private int card_2;
  private ArrayList<Integer> hand = new ArrayList<Integer>();
  
  public ArrayList<Integer> setCards() {
	  hand.add(card_1);
	  hand.add(card_2);
	  return hand;
  }
  
  /// Accessor for player's card
  public ArrayList<Integer> getCards() {
    return hand;
    
  }
  
  public void check(String choice) {
	  if (choice.equalsIgnoreCase("C")) {
		  System.out.println("Player Checked.");
	  }
  }
  
  public void fold(String choice) {
	  if (choice.equalsIgnoreCase("F")) {
		  hand.clear();
		  System.out.println("Player folded.");
	  }
  }
  
  /*
  
  Might possibly implement player decision methods into this class but we can talk about it next meeting
  
  */

}
