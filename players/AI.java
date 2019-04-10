package players;
import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 04/06/2019
 */


public class AI extends Player {

	private static int betInterval; // Interval for bets
	private int minBet; // Minimum bet amount
	private ArrayList<Integer> smartAIDec = new ArrayList<Integer>(); // Empty list of percentages that will be passed
	
	// Next few lines are variable from Hand class
	private static final int HIGH_CARD = 0;
	private static final int ONE_PAIR = 1;
	private static final int TWO_PAIRS = 2;
	private static final int THREE_OF_A_KIND = 3;
	private static final int STRAIGHT = 4;
	private static final int FLUSH = 5;
	private static final int FULL_HOUSE = 6;
	private static final int FOUR_OF_A_KIND = 7;
	private static final int STRAIGHT_FLUSH = 8;
	private static final int ROYAL_FLUSH = 9;

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
	
	// This method clears the list of CPU names
	public static void clearCPUName() {
		cpuName.clear();
	}

	// This is a getter for PlayerTest class
	public static ArrayList<String> getCPUName() {
		return cpuName;
	}

	public static int getBetInterval() {
		return betInterval;
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

		ArrayList<Integer> percent; // Empty list of percentages that will be updated

		// If AI has a stack less than minBet or less than the highest bet, then they only have 2 actions to play 
		if (super.getStack() <= minBet || super.getStack() <= super.getHighBet()) {
			percent = smartAIDecision(2);

			// All in action
			if (decision < percent.get(0)) {
				super.allIn("A");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// if AI has sufficient stack but has insufficient stack to raise to the next interval, then AI only has 3 actions to play
		else if (super.getStack() > minBet && super.getStack() - betInterval <= super.getHighBet()) {
			percent = smartAIDecision(3);

			// All in action
			if (decision < percent.get(0)) {
				super.allIn("A");
			}
			// Call action
			else if (decision >= percent.get(0) && decision < percent.get(1)) {
				super.call("L");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// AI has sufficient stack to raise more than one bet interval
		else {
			percent = smartAIDecision(4);

			// AI all in action
			if (decision < percent.get(0)) {
				super.allIn("A");
			}
			// AI raise action
			else if (decision >= percent.get(0) && decision > percent.get(1)) {
				int bet = checkAIRaise(); // Generates a random number within the bounds of stack for raising
				super.BetRaise("R", bet - super.getHighBet());
			}
			// AI call action
			else if (decision >= percent.get(1) && decision < percent.get(2)) {
				super.call("L");
			}
			// AI fold action
			else {
				super.fold("F");
			}
		
			
		}

		percent.clear();
		smartAIDec.clear(); // Clears the list of percentage for next round
	}
	/**
	 * This method generates a random raise amount that is greater than the highest bet for AI
	 * 
	 * @return newRaise
	 */
	public int checkAIRaise() {
		Random bet = new Random();
		
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

		ArrayList<Integer> percent; // Empty list of percentages that will be updated

		// If AI has a stack less than minBet then they only have 2 actions to play
		if (super.getStack() <= minBet) {
			percent = smartAIDecision2(3);

			// All in action
			if (decision < percent.get(0)) {
				super.allIn("A");
			}
			// Check action
			else if (decision >= percent.get(1) && decision < percent.get(2)) {
				super.check("C");
			}
			// Fold action
			else {
				super.fold("F");
			}
		}

		// AI has sufficient stack to bet more than the minimum bet amount
		else { 
			percent = smartAIDecision2(4);

			// AI all in action
			if (decision < percent.get(0)) {
				super.allIn("A");
			}
			// AI bet action
			else if (decision >= percent.get(0) && decision < percent.get(1)) {
				if (super.getStack() > minBet && super.getStack() <= 2 * betInterval) {
					super.BetRaise("B", betInterval);
				}
				else {
					int bet = checkAIBets(); // Generates a random number within the bounds of minimum bet and stack for betting
					super.BetRaise("B", bet);
				}
			}
			// AI check action
			else if (decision >= percent.get(1) && decision < percent.get(2)) {
				super.check("C");
			}
			// AI fold action
			else {
				super.fold("F");
			}
			
		}

		percent.clear();
		smartAIDec.clear(); // Clears the list of percentage for next round
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
					if (returnBet + betInterval - checkBet >= super.getStack()) { // Tests if round up raise exceeds stack
						returnBet -= checkBet; // Rounds down raise amount
					}
					else { // Rounds up raise amount
						returnBet += (betInterval - checkBet);
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

	// AI DECIDES ON PROBABILITY OF EACH ACTION BASED UPON THE RANKING OF IT'S OWN CARDS ---------------------------------------------------------------------------

	/**
	 * This method will decide the probability of percentage of which action the AI will implement
	 * based on the card ranking it has
	 * 
	 * This method only decides for AI when it cannot check
	 * 
	 * @param numChoice
	 * @return 
	 */
	protected ArrayList<Integer> smartAIDecision(int numChoice) {
		smartAIDec.clear();

		hand = super.getHand();

		// Default probabilities when AI cards have no ranking yet
		if (hand == null) {
			if (numChoice == 2) {
				smartAIDec.add(5);
			}
			else if (numChoice == 3) {
				smartAIDec.add(5);
				smartAIDec.add(20);
			}
			else if (numChoice == 4) {
				smartAIDec.add(5);
				smartAIDec.add(15);
				smartAIDec.add(40);
			}
		}
		else {
			if (hand.getRank() == HIGH_CARD) {
				if (numChoice == 2) {
					smartAIDec.add(1);
				}
				else if (numChoice == 3) {
					smartAIDec.add(2);
					smartAIDec.add(12);
				}
				else if (numChoice == 4) {
					smartAIDec.add(2);
					smartAIDec.add(12);
					smartAIDec.add(17);
				}
			}
			else if (hand.getRank() == ONE_PAIR) {
				if (numChoice == 2) {
					smartAIDec.add(2);
				}
				else if (numChoice == 3) {
					smartAIDec.add(2);
					smartAIDec.add(12);
				}
				else if (numChoice == 4) {
					smartAIDec.add(2);
					smartAIDec.add(12);
					smartAIDec.add(17);
				}
			}
			else if (hand.getRank() == TWO_PAIRS) {
				if (numChoice == 2) {
					smartAIDec.add(2);
				}
				else if (numChoice == 3) {
					smartAIDec.add(5);
					smartAIDec.add(30);
				}
				else if (numChoice == 4) {
					smartAIDec.add(2);
					smartAIDec.add(12);
					smartAIDec.add(30);
				}
			}
			else if (hand.getRank() == THREE_OF_A_KIND) {
				if (numChoice == 2) {
					smartAIDec.add(2);
				}
				else if (numChoice == 3) {
					smartAIDec.add(3);
					smartAIDec.add(13);
				}
				else if (numChoice == 4) {
					smartAIDec.add(4);
					smartAIDec.add(19);
					smartAIDec.add(70);
				}
			}
			else if (hand.getRank() == STRAIGHT) {
				if (numChoice == 2) {
					smartAIDec.add(10);
				}
				else if (numChoice == 3) {
					smartAIDec.add(10);
					smartAIDec.add(85);
				}
				else if (numChoice == 4) {
					smartAIDec.add(12);
					smartAIDec.add(50);
					smartAIDec.add(85);
				}
			}
			else if (hand.getRank() == FLUSH) {
				if (numChoice == 2) {
					smartAIDec.add(30);
				}
				else if (numChoice == 3) {
					smartAIDec.add(25);
					smartAIDec.add(60);
				}
				else if (numChoice == 4) {
					smartAIDec.add(22);
					smartAIDec.add(56);
					smartAIDec.add(90);
				}
			}
			else if (hand.getRank() == FULL_HOUSE) {
				if (numChoice == 2) {
					smartAIDec.add(60);
				}
				else if (numChoice == 3) {
					smartAIDec.add(40);
					smartAIDec.add(80);
				}
				else if (numChoice == 4) {
					smartAIDec.add(20);
					smartAIDec.add(75);
					smartAIDec.add(95);
				}
			}
			else if (hand.getRank() == FOUR_OF_A_KIND) {
				if (numChoice == 2) {
					smartAIDec.add(98);
				}
				else if (numChoice == 3) {
					smartAIDec.add(80);
					smartAIDec.add(95);
				}
				else if (numChoice == 4) {
					smartAIDec.add(75);
					smartAIDec.add(95);
					smartAIDec.add(99);
				}
			}
			else if (hand.getRank() == STRAIGHT_FLUSH) {
				if (numChoice == 2) {
					smartAIDec.add(99);
				}
				else if (numChoice == 3) {
					smartAIDec.add(60);
					smartAIDec.add(99);
				}
				else if (numChoice == 4) {
					smartAIDec.add(60);
					smartAIDec.add(95);
					smartAIDec.add(99);
				}
			}
			else if (hand.getRank() == ROYAL_FLUSH) {
				if (numChoice == 2) {
					smartAIDec.add(100);
				}
				else if (numChoice == 3) {
					smartAIDec.add(70);
					smartAIDec.add(99);
				}
				else if (numChoice == 4) {
					smartAIDec.add(60);
					smartAIDec.add(95);
					smartAIDec.add(99);
				}
			}
 		}
		return smartAIDec;
	}

	/**
	 * This method will decide the probability of percentage of which action the AI will implement
	 * based on the card ranking it has
	 * 
	 * This method only decides for AI when it cannot call !!
	 * 
	 * @param numChoice
	 * @return 
	 */
	protected ArrayList<Integer> smartAIDecision2(int numChoice) { 
		smartAIDec.clear();

		hand = super.getHand();

		// Default probabilities when AI cards have no ranking yet
		if (hand == null) {
			if (numChoice == 3) {
				smartAIDec.add(3);
				smartAIDec.add(80);
			}
			else if (numChoice == 4) {
				smartAIDec.add(5);
				smartAIDec.add(15);
				smartAIDec.add(80);
			}
		}
		else {
			if (hand.getRank() == HIGH_CARD) {
				if (numChoice == 3) {
					smartAIDec.add(1);
					smartAIDec.add(70);
				}
				else if (numChoice == 4) {
					smartAIDec.add(1);
					smartAIDec.add(13);
					smartAIDec.add(70);
				}
			}
			else if (hand.getRank() == ONE_PAIR) {
				if (numChoice == 3) {
					smartAIDec.add(2);
					smartAIDec.add(65);
				}
				else if (numChoice == 4) {
					smartAIDec.add(2);
					smartAIDec.add(13);
					smartAIDec.add(70);
				}
			}
			else if (hand.getRank() == TWO_PAIRS) {
				if (numChoice == 3) {
					smartAIDec.add(2);
					smartAIDec.add(80);
				}
				else if (numChoice == 4) {
					smartAIDec.add(2);
					smartAIDec.add(12);
					smartAIDec.add(70);
				}
			}
			else if (hand.getRank() == THREE_OF_A_KIND) {
				if (numChoice == 3) {
					smartAIDec.add(3);
					smartAIDec.add(80);
				}
				else if (numChoice == 4) {
					smartAIDec.add(4);
					smartAIDec.add(19);
					smartAIDec.add(75);
				}
			}
			else if (hand.getRank() == STRAIGHT) {
				if (numChoice == 3) {
					smartAIDec.add(10);
					smartAIDec.add(85);
				}
				else if (numChoice == 4) {
					smartAIDec.add(12);
					smartAIDec.add(45);
					smartAIDec.add(85);
				}
			}
			else if (hand.getRank() == FLUSH) {
				if (numChoice == 3) {
					smartAIDec.add(20);
					smartAIDec.add(90);
				}
				else if (numChoice == 4) {
					smartAIDec.add(22);
					smartAIDec.add(56);
					smartAIDec.add(90);
				}
			}
			else if (hand.getRank() == FULL_HOUSE) {
				if (numChoice == 3) {
					smartAIDec.add(20);
					smartAIDec.add(90);
				}
				else if (numChoice == 4) {
					smartAIDec.add(25);
					smartAIDec.add(75);
					smartAIDec.add(95);
				}
			}
			else if (hand.getRank() == FOUR_OF_A_KIND) {
				if (numChoice == 3) {
					smartAIDec.add(30);
					smartAIDec.add(95);
				}
				else if (numChoice == 4) {
					smartAIDec.add(30);
					smartAIDec.add(80);
					smartAIDec.add(99);
				}
			}
			else if (hand.getRank() == STRAIGHT_FLUSH) {
				if (numChoice == 3) {
					smartAIDec.add(40);
					smartAIDec.add(99);
				}
				else if (numChoice == 4) {
					smartAIDec.add(50);
					smartAIDec.add(85);
					smartAIDec.add(99);
				}
			}
			else if (hand.getRank() == ROYAL_FLUSH) {
				if (numChoice == 3) {
					smartAIDec.add(50);
					smartAIDec.add(99);
				}
				else if (numChoice == 4) {
					smartAIDec.add(60);
					smartAIDec.add(90);
					smartAIDec.add(99);
				}
			}
 		}
		return smartAIDec;
	}

}
