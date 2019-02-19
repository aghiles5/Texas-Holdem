import java.util.ArrayList;

// Common methods between AI and Player class will be in this super class

public class SuperPlayer {
	  private int Money; //not used until BetRaise is done
	  protected ArrayList<Integer> hand = new ArrayList<Integer>();
    /* implements
    check (done)
    fold (done)
    raise (together with bet)
    call
    and bet
    methods
    */
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
