import java.util.ArrayList;

/**
 * An abstract class that; Manages each players' amount of money, their two card
 * hand or "hole",their total bet per round, and who wins each round. The AI
 * will also utilize these methods.
 * 
 * @author Kyle Wen, Adam Hiles, John Lowie
 * @version 03/21/2019
 *
 */
public class Player {
	protected int stack; // Tracks each player's stack of money
	protected ArrayList<Card> hole = new ArrayList<Card>(); // the player's 2 card hand
	protected Hand hand; // player's 5 card hand as an object
	protected String name = ""; // the name of the human player
	protected int minBet = 0;
	protected int highBet = 0;
	private int totBet = 0; // the player's total bet for the round
	private String action = "";
	private double wins;
	private double lost;
	private double folds;
	private double winPercent;

	// method in main that sets the blinds
	// make the current player bet into a list?

	public Player() {
	}

	public Player(String oName, int oStack) {
		stack = oStack;
		name = oName;
	}

	/**
	 * pre: A new stack value has been entered. post: The stack has been set to the
	 * new value.
	 * 
	 * @param nStack
	 */
	public void setStack(int nStack) {
		stack = nStack;
	}

	/**
	 * pre: none post: calculates the percentage of winning for the player and
	 * returns a double
	 * 
	 * @return winPercent
	 */
	public double getWinPercent() {
		winPercent = (getWins() / (getWins() + getLost())) * 100; // multiply by 100 to get a percentage number
		return winPercent;
	}

	/**
	 * pre: none post: returns the number of times the player has folded during a
	 * game
	 * 
	 * @return folds
	 */
	public double getFolds() {
		return folds;
	}

	/**
	 * pre: none post: returns the number of rounds the player has won
	 * 
	 * @return wins
	 */
	public double getWins() {
		return wins;
	}

	/**
	 * pre: none post: returns the nuumber of rounds lost for the player
	 * 
	 * @return lost
	 */
	public double getLost() {
		return lost;
	}

	/**
	 * pre: none post: adds another round of win when player wins the round
	 */
	public void setWins() {
		wins += 1;
	}

	/**
	 * pre: none post: adds another round of lost when player losses round
	 */
	public void setLost() {
		lost += 1;
	}

	public void setAction(String newAction) {
		action = newAction;
	}

	/**
	 * pre: action variable has no action post: returns the player's action after
	 * player finishes his/her round
	 * 
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * pre: none post: The player's stack of money amount is returned. Gets and
	 * returns the stack of money of player.
	 * 
	 * @return stack
	 */
	public int getStack() {
		return stack;
	}

	/**
	 * pre: A name for the player has been set. post: The player's name has been
	 * set.
	 * 
	 * @param newName
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * pre: none post: The player's name has been returned. Gets and returns the
	 * name of the player.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Given the community and a player's hole cards, this method will find their
	 * highest ranking hand. The rank of each possible hand is determined and
	 * compared to one and another to find the highest. If two hands share the
	 * highest rank they are passed through to the dispute methods to find the
	 * highest.
	 * 
	 * @param comm the community cards
	 * @return none
	 */
	public void setHand(ArrayList<Card> comm) {
		int highestRank = -1, result;

		ArrayList<Card> allCards = new ArrayList<Card>();
		Hand highestHand = new Hand();
		allCards.addAll(comm); // The hole and community cards are combined to a single ArrayList
		allCards.addAll(hole);

		if (allCards.size() == 5) {
			for (Card card : allCards)
				hand.addCard(card);
		} else {
			ArrayList<Hand> combs = new ArrayList<Hand>();
			if (allCards.size() == 6)
				combs = combSix(allCards); // All six combinations of the cards are found for a six card set
			else if (allCards.size() == 7)
				combs = combSeven(allCards); // All 21 combinations of the cards are found for a seven card set

			for (Hand potenHand : combs) {
				if (potenHand.getRank() > highestRank) { // A potential hand becomes the new highest if its rank
															// surpasses the previous highest
					highestRank = potenHand.getRank();
					highestHand = potenHand;
				}

				else if (potenHand.getRank() == highestRank) { // If its rank is equal the two are disputed, the new
																// only succeeding the old if it is greater in value
					result = potenHand.compareHand(highestHand);
					if (result == 1) {
						highestRank = potenHand.getRank();
						highestHand = potenHand;
					}
				}
			}
		}
		hand = highestHand;
	}

	/**
	 * pre: none post: The player's hand has been returned.
	 * 
	 * @return new Hand object
	 */
	public Hand getHand() {
		return new Hand(hand);
	}

	/**
	 * pre: A card has been chosen. post: A card has been added to the player's
	 * hole.
	 * 
	 * @param c
	 */
	public void setHole(Card c) {
		// adds the cards to the hand list
		hole.add(c);
	}

	/**
	 * pre: none post: Returns the player's hole. The player's hole has been
	 * returned.
	 * 
	 * @return hole
	 */
	public ArrayList<Card> getHole() {
		return hole;
	}

	/**
	 * pre: none post: The player's hole has been reset The hole ArrayList has been
	 * cleared to reset for the next round.
	 */
	public void emptyHole() {
		hole.clear();
	}

	/**
	 * pre: none post: The player's hand has been reset The hole ArrayList has been
	 * cleared to reset for the next round.
	 */
	public void emptyHand() {
		hand = null;
		hand = new Hand();
	}

	/**
	 * pre: none post: The player's total bet has been returned.
	 * 
	 * @return totBet
	 */
	public int getBet() {
		return totBet;
	}

	/**
	 * pre: A bet value is entered. post: The player's total bet has been set.
	 */
	public void setBet(int nBet) {
		totBet = nBet;
	}

	/**
	 * pre: A player decision has been made. post: The player has "checked" and
	 * chosen to do nothing.
	 * 
	 * @param choice
	 */
	public void check(String choice) {
		// If the input is c, play moves to the next player
		if (choice.equalsIgnoreCase("C")) {
			action = "Checked";
			System.out.println("Player checked.");
			// Nothing
		}
	}

	/**
	 * pre: A player decision has been made. post: The player's hand has been
	 * cleared as they have given up on this round.
	 * 
	 * @param choice
	 */
	public void fold(String choice) {
		// if the input is f, the list is emptied
		if (choice.equalsIgnoreCase("F")) {
			emptyHand(); // Clears hand
			emptyHole(); // Clears hole
			action = "Folded";
			System.out.println("Player Folded.");
			folds += 1;
			// Nothing

		}
	}

	/**
	 * pre: A player decision and their increased bet have been entered. post: The
	 * player has added money to the pot with their total amount of money decreasing
	 * appropriately.
	 * 
	 * @param choice
	 * @param newBet
	 */
	public void BetRaise(String choice, int newBet) {
		if (choice.equalsIgnoreCase("B")) { // checks to see if the bet is less than the
			// money in the player's balance if (newBet <= stack) { stack -= newBet;
			// decreases the player's money value. totBet += newBet; // adds the bet to the
			// player's total bet.
			if (newBet >= 0) {
				stack -= newBet;
				totBet += newBet;
				action = "Bet";
				System.out.println("Player bet $" + newBet + ".");
			}
		} else if (choice.equalsIgnoreCase("R")) { // Raise action
			// must be 2x the amount to call
			if (newBet >= 0) {
				stack -= newBet;
				System.out.println("Player raised $" + newBet + ".");
				// totBet += newBet; // check logic
			}
			action = "Raised";
		}
	}

	/**
	 * pre: A player decision has been made. post: The player has called. Calculates
	 * the amount to call and adds that amount to the pot.
	 * 
	 * @param choice
	 * @param currentBet
	 */
	public void call(String choice) {
		int toCall = highBet - totBet; // highBet must be tracked
		// need to determine how to compare each player's current Bet to generate a
		// toCall
		if (choice.equalsIgnoreCase("L")) {
			stack -= toCall;
			totBet += toCall;
			action = "Called";
			System.out.println("Player called.");
		}
	}

	/**
	 * pre: A player decision has been made. post: The player has $0 remaining and
	 * has gone "All-In."
	 * 
	 * @param choice
	 */
	public void allIn(String choice) {
		if (choice.equalsIgnoreCase("A")) {
			totBet += stack;
			stack = 0;
			System.out.println("Player went all-in!");
			action = "All In";
		}
	}

	public void getDecision() {
		// Goes to getDecision in AI;
	}

	public void getDecision2() {
		// Goes to getDecision2 in AI;
	}

	/**
	 * All six unique combinations of five card hands from a six card set are found
	 * by the below algorithm. One blank space is cycled through all the indices of
	 * the passed Card ArrayList, writing those Cards not occupied by the blank
	 * space to a new Hand.
	 * 
	 * @param allCards the combined community and a given player's hole cards
	 * @return An ArrayList of all five card hand combinations
	 */
	private ArrayList<Hand> combSix(ArrayList<Card> allCards) {
		ArrayList<Hand> combs = new ArrayList<Hand>(); // A six Hand ArrayList is created to store all possible
														// combinations
		for (int i = 0; i < 6; i++)
			combs.add(new Hand());

		for (int blank = 0; blank < 6; blank++) { // The blank space is moved through each index, also doubles as the
													// index in the Hand ArrayList to write to
			for (int index = 0; index < 6; index++) { // Each Card index in allCards is ran through
				if (index != blank) // If the index of the card doesn't fall on the blank it is written to the
									// current combination index
					combs.get(blank).addCard(allCards.get(index));
			}
		}
		return combs;
	}

	/**
	 * All 21 unique combinations of five card hands from a seven card set are found
	 * by the below algorithm. Two blank spaces in the seven card set are cycled,
	 * their indices being exempt from a hand possibility when it is written to the
	 * master list.
	 * 
	 * @param allCards the combined community and a given player's hole cards
	 * @return An ArrayList of all five card hand combinations
	 */
	private ArrayList<Hand> combSeven(ArrayList<Card> allCards) {
		ArrayList<Hand> combs = new ArrayList<Hand>(); // A 21 Hand ArrayList is created to store all possible
														// combinations
		for (int i = 0; i < 21; i++)
			combs.add(new Hand());

		int writeIndex = 0; // The index of the 21 ArrayList ArrayList is cycled for each blank2 change
		for (int blank1 = 0; blank1 < 6; blank1++) { // The first blank advanced after the second has gone through each
														// space following it
			for (int blank2 = blank1 + 1; blank2 < 7; blank2++) { // The second blank cycles through each index
																	// following the first blank
				for (int index = 0; index < 7; index++) { // Each Card index in allCards is ran through
					if ((index != blank1) && (index != blank2)) // If the index of the card doesn't fall on a blank it
																// is written to the current combination index
						combs.get(writeIndex).addCard(allCards.get(index));
				}
				writeIndex++;
			}
		}
		return combs;
	}

	public void setHighBet(int betAmt) {
		highBet = betAmt;
	}

	public void setMinBet(int betAmt) {
		minBet = betAmt;
	}

}
