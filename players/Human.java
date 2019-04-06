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
}
