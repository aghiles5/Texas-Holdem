import java.util.ArrayList;

// Common methods between AI and Player class will be in this super class

public class Player {
	  private int Money; //not used until BetRaise is done
	  protected ArrayList<Card> hand = new ArrayList<Card>();
	  
	  public ArrayList<Card> setCards() {
		  //adds the cards to the hand list
		  hand.add(//card_1);
		  hand.add(//card_2);
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
				  System.out.println("Player bet $" + bet + ".");
			  }
		  } else if (choice.equalsIgnoreCase("R")) {
			  //Raise action
			  //if bet >= 2 times pot && bet <= Money
			  Money = Money - bet;
			  System.out.println("Player raised $" + bet + ".");
			  //pot update
		  }
	  }
	  
	  public void call(String choice, int toCall, int currentBet) {
		  //need to determine how to compare each player's current Bet to generate a toCall
		  if (choice.equalsIgnoreCase("C")) {
			  int bet = toCall - currentBet;
			  Money -= bet;
			  //pot += bet;
			  System.out.println("Player called.");
		  }
	  }

	  public void allIn(String choice, int currentBet) {
		  if (choice.equalsIgnoreCase("A")) {
			  currentBet += Money;
			  Money = 0;
			  // adds money to pot
			  // then sets money to 0
			  System.out.println("Player went all-in!");
		  }
	  }
}
