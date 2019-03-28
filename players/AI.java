package players;
import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/27/2019
 */


public class AI extends Player {

	private static int betInterval; // Interval for bets
	private int minBet; // Minimum bet amount

	public static ArrayList<String> cpuName = new ArrayList<String>(); // This is an empty array of CPU player names
	public static final String[] newNames = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton",
			"DickheadDallas", "EasyEarle", "FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio",
			"ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" }; // This is a list of names for the CPU player

	// CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------------------------
	
	// Constructor that initiates the name of AI, betting interval, and minimum betting amount
	public AI() {
		setCPUName(); // Calls method to make random AI name
		this.minBet = (int) (super.getStack() * 0.025); // Sets up the minimum amount AI can make a bet
	}
	
	//Constructor that sets the name of the AI and the stack - Kyle
	public AI(String name, int stack) {
		super.name = name; // Sets AI name
		super.stack = stack; // Sets AI's initial stack amount
	}

	// SETTERS AND GETTERS------------------------------------------------------------------------------------------------------------------------------------------

	// This method adds the list of names to the empty array of CPU names
	public static void addCPUName() {
		for (int i = 0; i < newNames.length; i++) { // For loop to add newNames to cpuName
			cpuName.add(newNames[i]); // Adds each individual strings in newNames into cpuName
		}
	}

	// This method sets the name of AI and removes the name from the list to avoid duplicates
	public void setCPUName() {
		Random name = new Random();
		int rName = name.nextInt(cpuName.size()); // rName chooses a random integer based on cpuName ArrayList size
		super.name = cpuName.get(rName); // From rName, this will set the CPU player's name
		cpuName.remove(rName); // Removes name from cpuName so there are no duplicate player names on poker table
	}

	// This method sets up the intervals for betting
	public static void setBetIntervals(int stack) {
		betInterval = (int) (0.01 * stack); // Sets the bet interval to 1% of the initial stack
	}

	// THE METHODS BELOW THIS LINE ARE ALL AI ACTIONS AND ACTION QUALIFICATION CHECKS-------------------------------------------------------------------------------
	
	// YOU WILL HAVE TO MAKE AI MUCH SMARTER THAN RANDOMLY MAKING IT'S OWN CHOICES OUT OF 100!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * THIS METHOD IS IMPLEMENTED IF AND ONLY IF THE PREVIOUS PLAYER/ PLAYERS HAVE BET, RAISED, OR CALLED TO PREVIOUS RAISES
	 * 
	 * Implement this method if previous player raise
	 * Implement this method if previous player calls
	 * Implement this method if previous player calls all-in
	 * Implement this method if highest bet is NOT 0
	 */
	public void getDecision() {
		// Next 2 lines of code sets up probability of AI decisions
		Random choice = new Random();
		int decision = choice.nextInt(100); // Generates a random choice out of 100

		// If AI has a stack less than minBet or less than the highest bet, then they only have 2 actions to play 
		if (super.getStack() <= minBet || super.getStack() <= super.getHighBet()) {
			// All in action
			if (decision < 10) {
				super.allIn("A");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// if AI has sufficient stack but has insufficient stack to raise to the next interval, then AI only has 3 actions to play
		else if (super.getStack() > minBet && super.getStack() - betInterval <= super.getHighBet()) {
			// All in action
			if (decision < 10) {
				super.allIn("A");
			}
			// Call action
			else if (decision >= 10 && decision < 40) {
				super.call("L");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// AI has sufficient stack to raise more than one bet interval
		else {
			// AI fold action
			if (decision < 10) {
				super.fold("F");
			}

			// AI all in action
			else if (decision >= 10 && decision < 12) {
				super.allIn("A");
			}
		
			// AI call action
			else if (decision >= 12 && decision < 75) {
				super.call("L");
			}
		
			// AI raise action
			else if (decision >= 75) {
				int bet = checkAIRaise(); // Generates a random number within the bounds of stack for raising
				super.BetRaise("R", bet - super.getHighBet());
			}
		}
	}
	/**
	 * This method generates a random raise amount that is greater than the highest bet for AI
	 * 
	 * @return newRaise
	 */
	public int checkAIRaise() {
		Random bet = new Random();
		// Random typeBet = new Random(); THIS IS TEMPORARY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// int prbTBet = typeBet.nextInt(100); THIS IS TEMPORARY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		int newRaise = 0;

		Boolean canBet = false;

		while (canBet == false) { // Loop will run until raising amount is satisfied
			int raising = bet.nextInt(super.getStack() + 1);

			// Raise amount must be greater than highest bet but lower than stack to avoid all in action
			if (raising > super.getHighBet() && raising < super.getStack()) { 
				newRaise = checkBetInterval("R", raising);
				canBet = true;
			}
		}
		return newRaise;
	}

	/**
	 * THIS METHOD IS IMPLEMENTED IF AND ONLY IF THE HIGHEST BET IS 0!!
	 * 
	 * Implement this method if previous player/ players checks
	 * Implement this method if current player is AI and is first player after flop
	 */
	public void getDecision2() {
		// Next 2 lines of code sets up probability of AI decisions
		Random choice = new Random();
		int decision = choice.nextInt(100);

		// If AI has a stack less than minBet then they only have 2 actions to play
		if (super.getStack() <= minBet) {
			// All in action
			if (decision < 10) {
				super.allIn("A");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// When AI has suffifient stack but can only bet to the bet interval
		else if (super.getStack() > minBet && super.getStack() <= 2 * betInterval) {
			// All in action
			if (decision < 10) {
				super.allIn("A");
			}
			// Fold action
			else if (decision >= 10 && decision < 60) {
				super.fold("F");
			}
			// Bet action
			else {
				super.BetRaise("B", betInterval);
			}
		}

		// AI has sufficient stack to bet more than the minimum bet amount
		else {
			// AI check action
			if (decision < 63) {
				super.check("C");
			}
			// AI bet action
			else if (decision >= 63 && decision < 88) {
				int bet = checkAIBets(); // Generates a random number within the bounds of minimum bet and stack for betting
				super.BetRaise("B", bet);
			}
			// AI fold action
			else if (decision >= 88 && decision < 98) {
				super.fold("F");
			}
			// AI all in action
			else if (decision >= 98) {
				super.allIn("A");
			}
		}
	}

	/**
	 * This method will check if AI has sufficient money in it's stack for the desired game actions bet,
	 * raise, and call
	 * 
	 * @return returnBet
	 */
	public int checkAIBets() {
		Random bet = new Random(); // Random betting amount
		Random betMount = new Random(); // Probability of betting/raising alot or little
		int betProb = betMount.nextInt(100);
		int returnBet = 0;
		// int minRaise = super.getStack() + betInterval;
		Boolean canBet = false;

		while (canBet == false) {
			int betting = bet.nextInt(super.getStack() + 1); // Creating a random number for betting

			// Probability of betting alot is 10%
			if (betProb < 10) {
				// Must bet more than half of AI's stack as rule
				if (betting < super.getStack() && betting >= (super.getStack() / 2)) {
					if (betting <= 2 * betInterval) {
						returnBet = 2 * betInterval;
						canBet = true;
					}
					else {
						returnBet = checkBetInterval("B", betting); // Checks if the betting amount is an interval of betting
						canBet = true; // Condition satisfied and betting amount modified to break loop
					}
				}
			}

			// Probability of betting low is 90%
			else if (betProb >= 10) {
				// Must bet less than half of AI's stack
				if (betting < (super.getStack() / 2) && betting >= minBet) {
					returnBet = checkBetInterval("B", betting); // Checks if the betting amount is an interval of betting
					canBet = true; // Condition satisfied and betting amount modified to break loop
				}
			}
		}
		return returnBet;
	}

	/**
	 * This method will check if betting amount is an interval for betting
	 * 
	 * @param decision AI action
	 * @param toBet random generated number for updating
	 * @return returnBet
	 */
	public int checkBetInterval(String decision, int toBet) {
		int returnBet = toBet;
		int checkBet = returnBet % betInterval; // Checks if bet amount from AI is a betting interval
		double halfWayP = betInterval / 2; // Mid point of betting interval

		if (decision == "B") { // Handles bet dection
			// Rounds the bet amount to the closest bet interval
			if (checkBet != 0) { // Betting amount is not an interval for betting
				if ((double) checkBet < halfWayP) { // Rounds down the bet amount
					returnBet -= checkBet;
				}
				else { // Rounds up the bet amount
					// Rounds down bet amount if it is too close to stack
					if (returnBet + betInterval - checkBet >= super.getStack()) { //!!!!!!!!!! == -> >= !!!!!!!!!!!!!
						returnBet -= checkBet;
					}
					// Rounds up bet amount if rounded bet does not exceed stack
					else {
						returnBet += (betInterval - checkBet);
					}
				}
			}
		}

		else if (decision == "R") { // Handles raise decision
			if (checkBet != 0) { // This will round the raise amount if it is not an interval for raising
				if ((double) checkBet < halfWayP) { // This will round the raise amount down
					if (returnBet - checkBet <= super.getHighBet()) { // Tests if chosen raise amount is lower than highest bet
						if (returnBet + checkBet >= super.getStack()) { // Rounds down the raise amount if rounded up raise exceeds stack
							returnBet -= checkBet;
						}
						else { // Rounds up raise amount
							returnBet += checkBet;
						}
					}
					else { // Rounds down raise amount
						returnBet -= checkBet;
					}
				}
				else { // Rounds raise amount up
					if (returnBet + betInterval - (int) checkBet >= super.getStack()) { // Tests if round up raise exceeds stack
						returnBet -= checkBet; // Rounds down raise amount
					}
					else { // Rounds up raise amount
						returnBet += (betInterval - (int) checkBet);
					}
				}

				// This is a last resort if the raise amount winds up exceeding stack
				if (returnBet >= super.getStack()) {
					returnBet -= betInterval;
				}
			}
		}
		return returnBet;
	}

}
