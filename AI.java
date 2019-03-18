import java.util.ArrayList;
import java.util.Random;

/**
 * The AI class handles the random decisions that the AI commits
 * 
 * @author John Lowie
 * @version 03/18/2019
 */

// FIX THE AI NAMING SYSTEM!!!!!!!!!!!!!!!!!

public class AI extends Player {

	//String tempName = ""; // Empty string of a CPU name
	public static ArrayList<String> cpuName = new ArrayList<String>(); // This is an empty array of CPU player names
	public static final String[] newNames = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton",
			"DickheadDallas", "EasyEarle", "FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio",
			"ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" }; // This is a list of names for the CPU player

	// This method adds the list of names to the empty array of CPU names
	public static void addCPUName() {
		for (int i = 0; i < newNames.length; i++) { // For loop runs as long as the length to add names from newNames to cpuName
			cpuName.add(newNames[i]); // Adds newNames into cpuName
		}
	}

	public AI() {
		addCPUName();
		setCPUName();
	}

	public void setCPUName() {
		Random name = new Random();
		int rName = name.nextInt(cpuName.size()); // rName chooses a random integer based on cpuName ArrayList size
		super.name = cpuName.get(rName); // From rName, this will set the CPU player's name
		cpuName.remove(rName); // Removes name from cpuName so there are no duplicate player names on poker table
		//super.name = tempName;
	}

	// This method pushes the AI decisions to Player class
	public void getDecision() {
		// This implements the randomness of the AI
		Random choice = new Random();
		int decision = choice.nextInt(100); // TMPORARY CHANGE!!!!!!!!!!!!!!!!!!!!!!!
		//Random betting = new Random();

		// AI randomly checks as a decision
		if (decision <= 94) {
			super.check("C");
		}

		// AI randomly folds as a decision
		else if (decision >= 95) {
			super.fold("F");
		}

		// THIS COMMENT IS TEMPORARY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		/*
		 * else if (decision == 2) { checkAIBets(decision, super.getStack()); int bet =
		 * betting.nextInt(super.getStack()); super.BetRaise("B", bet); }
		 * 
		 * else if (decision == 3) { checkAIBets(decision, super.getStack()); int bet =
		 * betting.nextInt(super.getStack()); super.BetRaise("R", bet); }
		 * 
		 * // THIS LINE WILL NOT RUN PROPERLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!! else if
		 * (decision == 4) { super.call("L"); }
		 * 
		 * // THIS LINE WILL NOT RUN PROPERLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!! else if
		 * (decision == 5) { super.allIn("A"); }
		 */

	}

	// This method will randomly bet and check if AI has sufficient money
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
		return 0; // THIS WILL ONLY BE USED TO DETERMINE IF THIS METHOD IS WORKING OR NOT
	}

}
