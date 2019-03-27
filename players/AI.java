package players;
import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/26/2019
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
		setCPUName();
		this.minBet = (int) (super.getStack() * 0.025);
	}
	
	//Constructor that sets the name of the AI and the stack - Kyle
	public AI(String name, int stack) {
		super.name = name;
		super.stack = stack;
	}

	// SETTERS AND GETTERS------------------------------------------------------------------------------------------------------------------------------------------

	// This method adds the list of names to the empty array of CPU names
	public static void addCPUName() {
		for (int i = 0; i < newNames.length; i++) { // For loop runs as long as the length to add names from newNames to cpuName
			cpuName.add(newNames[i]); // Adds newNames into cpuName
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
		betInterval = (int) (0.01 * stack);
	}

	// THE METHODS BELOW THIS LINE ARE ALL AI ACTIONS AND ACTION QUALIFICATION CHECKS-------------------------------------------------------------------------------

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
		int decision = choice.nextInt(100);

		// If AI has a stack less than minBet then they only have 2 actions to play
		if (super.getStack() <= minBet || super.getStack() <= super.getHighBet()) {
			if (decision < 10) {
				super.allIn("A");
			}
			else {
				super.fold("F");
			}
		}

		// if AI has sufficient stack but has insufficient stack to raise to the next interval, then AI only has 3 actions to play
		else if (super.getStack() > minBet && super.getStack() - betInterval <= super.getHighBet()) {
			if (decision < 10) {
				super.allIn("A");
			}
			else if (decision >= 10 && decision < 55) {
				super.call("L");
			}
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
				int bet = checkAIBets("R");
				super.BetRaise("R", bet);
			}
		}
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
			if (decision < 10) {
				super.allIn("A");
			}
			else {
				super.fold("F");
			}
		}

		// When AI has suffifient stack but can only bet to the bet interval
		else if (super.getStack() > minBet && super.getStack() <= 2 * betInterval) {
			if (decision < 20) {
				super.allIn("A");
			}
			else if (decision >= 20 && decision < 60) {
				super.fold("F");
			}
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
				int bet = checkAIBets("B");
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
	 * @param decision decision of bet or raise
	 * @return returnBet
	 */
	public int checkAIBets(String decision) {
		Random bet = new Random(); // Random betting amount
		Random betMount = new Random(); // Probability of betting/raising alot or little
		int betProb = betMount.nextInt(100);
		int returnBet = 0;
		// int minRaise = super.getStack() + betInterval;
		Boolean canBet = false;

		while (canBet == false) {
			int betting = bet.nextInt(super.getStack() + 1); // Creating a random number for betting

			// Checks bet decision
			if (decision == "B") {
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

			// POSSIBLE RUNTIME ERRORS HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

			// Checks raise decision
			else if (decision == "R") {
				if (betting > super.getHighBet() && betting < super.getStack()) {
					// Probability of raising alot is 10%
					if (betProb < 10) {
						if (super.getStack() - betInterval <= betting) {
							returnBet = super.getHighBet() + betInterval;
							canBet = true;
						}
						else {
							returnBet = checkBetInterval("R", betting);
							canBet = true;
						}
					}
					// Probability of raising low is 90%
					else if (betProb >= 10) {
						if (super.getStack() - betInterval <= betting) {
							returnBet = super.getHighBet() + betInterval;
							canBet = true;
						}
						else {
							returnBet = checkBetInterval("R", betting);
							canBet = true;
						}
					}
				}
			}
		}

		return returnBet;
	}

	// POSSIBEL RUNTIME ERRORS HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * This method will check if betting amount is an interval of betting
	 * 
	 * @param toBet random bet amount from AI
	 * @return returnBet
	 */
	public int checkBetInterval(String decision, int toBet) {
		int returnBet = toBet;
		int checkBet = returnBet % betInterval; // Checks if bet amount from AI is a betting interval
		double halfWayP = betInterval / 2; // Mid point of betting interval

		if (decision == "B") {
			// Rounds the bet amount to the closest bet interval
			if (checkBet != 0) {
				if ((double) checkBet < halfWayP) { // Rounds down the bet amount
					returnBet -= checkBet;
				}
				else { // Rounds up the bet amount
					// Rounds down bet amount if it is too close to AI stack
					if (returnBet + betInterval - checkBet == super.getStack()) {
						returnBet -= checkBet;
					}
					else {
						returnBet += (betInterval - checkBet);
					}
				}
			}
		}

		else if (decision == "R") {
			if (checkBet != 0) {
				if ((double) checkBet < halfWayP) {
					if (returnBet - checkBet == super.getHighBet()) {
						returnBet += checkBet;
					}
					else {
						returnBet -= checkBet;
					}
				}
				else {
					if (returnBet + betInterval - (int) checkBet == super.getStack()) {
						returnBet -= checkBet;
					}
					else {
						returnBet += (betInterval - (int) checkBet);
					}
				}
			}
		}
		return returnBet;
	}

}
