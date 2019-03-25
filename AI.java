import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/25/2019
 */

public class AI extends Player {

	private int betInterval; // FIX THIS!!!!!!!!!!!!!!!!!!
	private int minBet;

	public static ArrayList<String> cpuName = new ArrayList<String>(); // This is an empty array of CPU player names
	public static final String[] newNames = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton",
			"DickheadDallas", "EasyEarle", "FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio",
			"ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" }; // This is a list of names for the CPU player

	// CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------------------------
	
	// Constructor that initiates the name of AI
	public AI() {
		setCPUName();
		setBetIntervals();
	}
	
	//Constructor that sets the name of the AI and the stack - Kyle
	public AI(String name, int stack) {
		super.name = name;
		super.stack = stack;
		this.minBet = (int) (super.getStack() * 0.025); // CHANGE THIS!!!!!!!!!!!!!!!!!!
	}

	// SET UP METHODS-----------------------------------------------------------------------------------------------------------------------------------------------

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

	public void setBetIntervals() {
		this.betInterval = (int) (0.01 * super.getStack());
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
		// Next 2 lines of code sets up probability of AI choices
		Random choice = new Random();
		int decision = choice.nextInt(100);

		// AI fold action
		if (decision < 10) {
			super.fold("F");
		}

		// AI all in action
		else if (decision >= 10 && decision < 15) {
			super.allIn("A");
		}
		
		// AI call action
		else if (decision >= 15 && decision < 75) {
			super.call("L");
		}
		
		// AI raise action
		else if (decision >= 75) {
			int bet = checkAIBets("R");
			super.BetRaise("R", bet);
		}
	}

	/**
	 * THIS METHOD IS IMPLEMENTED IF AND ONLY IF THE HIGHEST BET IS 0!!
	 * 
	 * Implement this method if previous player/ players checks
	 */
	public void getDecision2() {
		// Next 2 lines of code sets up probability of AI choices
		Random choice = new Random();
		int decision = choice.nextInt(100);

		// AI check action
		if (decision < 60) {
			super.check("C");
		}
		// AI bet action
		else if (decision >= 60 && decision < 85) {
			int bet = checkAIBets("B");
			super.BetRaise("B", bet);
		}
		// AI fold action
		else if (decision >= 85 && decision < 95) {
			super.fold("F");
		}
		// AI all in action
		else if (decision >= 95) {
			super.allIn("A");
		}
	}

	/**
	 * This method will check if AI has sufficient money in it's stack for the desired game actions bet,
	 * raise, and call
	 * 
	 * @param playDecision AI's decision during round
	 */
	public int checkAIBets(String playDecision) {
		Random bet = new Random();
		int betting = bet.nextInt(super.getStack());

		// BOTH BET AND RAISE HAVE THE SAME CHECK FUNCTION

		if (playDecision == "B") {
			if (betting == super.getStack() || betting == 0) {
				checkAIBets("B");
			}
			else {
				return 100; // FIX THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			}
		}
		/**
		 * Since "B" and "R" will be the only strings passed into checkAIBets method
		 * it will be redundant to have an else if condition for "R"
		 */
		else {
			if (betting == super.getStack() || betting == 0) {
				checkAIBets("R");
			}
			else {
				return 100; // FIX THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			}
		}
	}

}
