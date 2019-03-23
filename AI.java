import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/23/2019
 */

public class AI extends Player {

	public static ArrayList<String> cpuName = new ArrayList<String>(); // This is an empty array of CPU player names
	public static final String[] newNames = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton",
			"DickheadDallas", "EasyEarle", "FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio",
			"ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" }; // This is a list of names for the CPU player

	// CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------------------------
	
	// Constructor that initiates the name of AI
	public AI() {
		setCPUName();
	}
	
	//Constructor that sets the name of the AI and the stack - Kyle
	public AI(String name, int stack) {
		super.name = name;
		super.stack = stack;
	}

	// NAME SET UP METHODS------------------------------------------------------------------------------------------------------------------------------------------

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
		// This implements the randomness of the AI
		Random choice = new Random();
		int decision = choice.nextInt(100);
		Random betting = new Random();

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
			checkAIBets(decision, super.getStack());
			int bet = betting.nextInt(super.getStack());
			super.BetRaise("R", bet);
		}
	}

	/**
	 * THIS METHOD IS IMPLEMENTED IF AND ONLY IF THE HIGHEST BET IS 0!!
	 * 
	 * Implement this method if previous player/ players checks
	 */
	public void getDecision2() {
		// This implements the randomness of the AI
		Random choice = new Random();
		int decision = choice.nextInt(100);
		Random betting = new Random();

		// AI check action
		if (decision < 60) {
			super.check("C");
		}
		// AI bet action
		else if (decision >= 60 && decision < 85) {
			checkAIBets(decision, super.getStack());
			int bet = betting.nextInt(super.getStack());
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
	 * @param money AI's current amount of stacks
	 */
	public int checkAIBets(int playDecision, int money) {
		Random bet = new Random();
		// int betting = bet.nextInt(money + 1); TEMPORARY!!!!!!!!!!!!!!

		/**
		 * This handle the AI's betting decisions If the bets are 0 or it is the same as
		 * its stack, the method will run again until the bet is not 0 and lower than
		 * it's stack
		 */
		if (playDecision == 2) {
			int betting = bet.nextInt(money + 1);
			if (betting == 0) {
				checkAIBets(playDecision, money);
			} else if (betting == money) {
				checkAIBets(playDecision, money);
			} else {
				return betting;
			}
		}

		/**
		 * This handles the AI's raising decisions If the bets are 0 or it is the same
		 * as its stack, the method will run again until the bet is not 0 and lower than
		 * it's stack
		 */
		else if (playDecision == 3) {
			int betting = bet.nextInt(money + 1);
			if (betting == 0) {
				checkAIBets(playDecision, money);
			} else if (betting == money) {
				checkAIBets(playDecision, money);
			} else {
				return betting;
			}
		}
		return 0; // THIS WILL ONLY BE USED TO DETERMINE IF THIS METHOD IS WORKING OR NOT!!!!!!!!!!!!!!!!!!!!!!!
	}

}
