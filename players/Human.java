package players;
/**
 * Retrieves the human player's decision and calls the appropriate action
 * method.
 * 
 * @author Kyle Wen, Brayden Schmaltz
 * @version 03/13/2019
 */

public class Human extends Player {
	/**
	 * Constructor that gets the name of the player.
	 * 
	 * @param n
	 */
	public Human(String n) {
		super.name = n;
	}
	
	public Human(String n, int Money) {
		super.name = n;
		super.stack = Money;
	}

	/**
	 * pre: The player has entered a decision. post: The appropriate method has been
	 * called. Gets the player's decision and calls the corresponding method.
	 * 
	 * @param decision
	 */
	public void getDecision(String decision) {
		// check to see if allIn is implemented properly
		// super.allIn(decision);
		// super.call(decision);
		super.check(decision);
		super.fold(decision);
	}

	/**
	 * pre: The player has entered a decision that involves a specified bet. post:
	 * The appropriate method has been called. Gets the player's decision and calls
	 * the corresponding method.
	 * 
	 * @param decision, newBet
	 */
	public void getDecision(String decision, int newBet) {
		// Check to see if implemented properly
		// overloads the above method to handle BetRaise
		// super.BetRaise(decision, newBet);

	}
}
