/**
 * Retrieves the human player's decision 
 * and calls the appropriate action method.
 * @author Kyle Wen
 */
public class Human extends Player {
	/**
	 * Constructor that gets the name of the player.
	 * @param n
	 */
	public Human(String n) {
		super.name = n;
	}

	/**
	 * pre: The player has entered a decision.
	 * post: The appropriate method has been called.
	 * Gets the player's decision and calls the corresponding method.
	 * @param decision
	 */
  	public void getDecision(String decision) {
		decision = super.invalidChoice(decision);
		//check to see if allIn is implemented properly
		super.allIn(decision);
		super.call(decision);
		super.check(decision);
		super.fold(decision);
	}
  	
  	/**
	 * pre: The player has entered a decision that involves a specified bet.
	 * post: The appropriate method has been called.
	 * Gets the player's decision and calls the corresponding method.
	 * @param decision, newBet
	 */
  	public void getDecision(String decision, int newBet) {
  			//Check to see if implemented properly
  			//overloads the above method to handle BetRaise
  			super.BetRaise(decision, newBet);
  			
  	}

}
