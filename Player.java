import java.util.ArrayList;

public class Player {
  private int card_1;
  private int card_2;
  private int Money; //not used until BetRaise is done
  private ArrayList<Integer> hand = new ArrayList<Integer>();
  
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
  
  public void check(String choice) {
	  //If the input is c, nothing happens
	  if (choice.equalsIgnoreCase("C")) {
		  System.out.println("Player Checked.");
	  }
  }
  
  public void fold(String choice) {
	  //if the input is f, the list is emptied
	  if (choice.equalsIgnoreCase("F")) {
		  hand.clear(); //list is emptied
		  System.out.println("Player folded.");
	  }
  }
  
  public void BetRaise(String choice, int bet) {
	  if (choice.equalsIgnoreCase("B")) {
		  //checks to see if the bet is less than the
		  //money in the player's balance
		  if (bet <= Money) {
			  Money = Money - bet;
			  //pot update
		  }
	  } else if (choice.equalsIgnoreCase("R")) {
		  //Raise action
		  //if bet >= 2 times pot && bet <= Money
		  Money = Money - bet;
		  //pot update
	  }
  }


}
