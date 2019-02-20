import java.util.ArrayList;

/**
 * The Showdown class handles methods involved with the "showdown" phase in
 * Texas Holdem where players reveal their cards and vie for the pot by 
 * making the best ranking hand. Currently, only the basic ranking 
 * determination for one full, five card hand is implemented, returning
 * the rank number.
 *
 * @author Adam Hiles
 * @version 02/20/19
 */
public class Showdown {

	private static final String[] RANKING_KEY = {"High Card", "One Pair", 
			"Two Pairs", "Three of a Kind", "Straight", "Flush", "Full House", 
			"Four of a Kind", "Straight Flush", "Royal Flush"};
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
	
	private static final int HAND_ONE_GREATER = 1;
	private static final int HAND_TWO_GREATER = 2;
	private static final int HANDS_EQUAL = 0;
	
	public static void main(String args[]) {
	}
	
	/**
	 * Base public class. Messy right now but works, will look into 
	 * stringbuilder for displaying results.
	 * 
	 * @param hole1 the first player's hole cards
	 * @param hole2 the second player's hole cards
	 * @param comm the five community cards
	 * @return the result of the showdown so that the main class can reward the pot accordingly
	 */
	public static int showdown(ArrayList<Card> hole1, ArrayList<Card> hole2, ArrayList<Card> comm) {
		ArrayList<Card> player1Hand, player2Hand;
		int player1Rank, player2Rank, result;
		player1Hand = getHighestHand(hole1, comm);
		player2Hand = getHighestHand(hole2, comm);
		player1Rank = getHandRank(player1Hand);
		player2Rank = getHandRank(player2Hand);
		while (true) {
			if (player1Rank > player2Rank) {
				System.out.println("\nPlayer One Wins the Pot");
				System.out.println("\n1. Player One: " + RANKING_KEY[player1Rank] + "\n");
				for (Card card : player1Hand) {
					System.out.println(card.toString());
				}
				System.out.println("\n2. Player Two: " + RANKING_KEY[player2Rank] + "\n");
				for (Card card : player2Hand) {
					System.out.println(card.toString());
				}
				return HAND_ONE_GREATER;
			}
			else if (player1Rank < player2Rank) {
				System.out.println("\nPlayer Two Wins the Pot");
				System.out.println("\n1. Player Two: " + RANKING_KEY[player2Rank] + "\n");
				for (Card card : player2Hand) {
					System.out.println(card.toString());
				}
				System.out.println("\n2. Player One: " + RANKING_KEY[player1Rank] + "\n");
				for (Card card : player1Hand) {
					System.out.println(card.toString());
				}
				return HAND_TWO_GREATER;
			}
			else {
				result = dispute(player1Hand, player2Hand, player1Rank);
				if (result == HAND_ONE_GREATER) {
					System.out.println("\nPlayer One Wins the Pot");
					System.out.println("\n1. Player One: " + RANKING_KEY[player1Rank] + "\n");
					for (Card card : player1Hand) {
						System.out.println(card.toString());
					}
					System.out.println("\n2. Player Two: " + RANKING_KEY[player2Rank] + "\n");
					for (Card card : player2Hand) {
						System.out.println(card.toString());
					}
					return HAND_ONE_GREATER;
				}
				else if (result == HAND_TWO_GREATER) {
					System.out.println("\nPlayer Two Wins the Pot");
					System.out.println("\n1. Player Two: " + RANKING_KEY[player2Rank] + "\n");
					for (Card card : player2Hand) {
						System.out.println(card.toString());
					}
					System.out.println("\n2. Player One: " + RANKING_KEY[player1Rank] + "\n");
					for (Card card : player1Hand) {
						System.out.println(card.toString());
					}
					return HAND_TWO_GREATER;
				}	
				else {
					System.out.println("\nStalemate");
					System.out.println("\nPlayers One and Two Both Have " + RANKING_KEY[player1Rank] + "\n");
					return HANDS_EQUAL;
				}
			}
		}
	}
	
	//Private Methods
	
	/**
	 * Given the community and a player's hole cards, this method will find 
	 * their highest ranking hand. The rank of each possible hand is determined
	 * and compared to one and another to find the highest. If two hands share
	 * the highest rank they are passed through to the dispute methods to find
	 * the highest.
	 * 
	 * @param hole a player's hole cards
	 * @param comm the five community cards
	 * @return the highest possible hand from the cards 
	 */
	private static ArrayList<Card> getHighestHand(ArrayList<Card> hole, ArrayList<Card> comm) {
		int highestRank = -1, rank, result;
		
		ArrayList<Card> allCards = new ArrayList<Card>(), highestHand = new ArrayList<Card>(); //The hole and community cards are combined to a single ArrayList
		allCards.addAll(comm);
		allCards.addAll(hole);
		
		ArrayList<ArrayList<Card>> combs = comb(allCards); //All 21 combinations of the cards are found
		
		for (ArrayList<Card> hand : combs) { //The rank for each combination is found
			rank = getHandRank(hand);
			
			if (rank > highestRank) { //A hand becomes the new highest if its rank surpasses the previous highest
				highestRank = rank;
				highestHand = hand;
			}
			
			else if (rank == highestRank) { //If its rank is equal the two are disputed, the new only succeeding the old if it is greater in value
				result = dispute(hand, highestHand, rank);
				if (result == HAND_ONE_GREATER) {
					highestRank = rank;
					highestHand = hand;
				}
			}
		}
		
		return highestHand;
	}
	
	/**
	 * For a five Card Arraylist hand this method will determine where it falls
	 * on the Texas Holdem hand rankings, listed in the RANKING_KEY array from
	 * lowest to highest. The correct rank number is returned to the caller.
	 * 
	 * @param hand the Card Arraylist hand to have its ranking determined
	 * @return the integer ranking of the hand
	 */
	private static int getHandRank(ArrayList<Card> hand) {
		hand = orderByRank(hand); //The Cards are ordered from highest to lowest rank
		
		//Checks for flush/straight type hands
		
		boolean isFlush = isFlush(hand);
		
		boolean isStraight = isStraight(hand);
		
		//The appropriate flush/straight type ranking is returned based on the above test results, else the method continues
		
		if ((isFlush == true) && (isStraight == true) && (hand.get(0).getRank() == 12) && (hand.get(4).getRank() == 8)) 
			return ROYAL_FLUSH;
		else if ((isFlush == true) && (isStraight == true))
			return STRAIGHT_FLUSH;
		else if (isFlush == true)
			return FLUSH;
		else if (isStraight == true)
			return STRAIGHT;
		
		// Pair checking
		
		boolean threeKind = false;
		double pairs = 0.0;
		
		for (Card card : hand) { //Each card is tested against all other cards in the hand for which ones have matching ranks	
			int rankMatches = -1;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches == 3) //If four cards have matching ranks "Four of a Kind" is immediately returned
				return FOUR_OF_A_KIND;
			else if (rankMatches == 2)
				threeKind = true;
			else if (rankMatches == 1)
				pairs += 0.5;
			}
		
		//Based on the above determined sets of equal ranks the appropriate rank based rankings are returned
		
		if ((threeKind == true) && (pairs == 1.0))
			return FULL_HOUSE;
		else if (threeKind == true)
			return THREE_OF_A_KIND;
		else if (pairs == 2.0)
			return TWO_PAIRS;
		else if (pairs == 1.0)
			return ONE_PAIR;
		
		return HIGH_CARD; // The ranking defaults to "High Card"
	}
	
	/**
	 * This method checks if a five Card hand is flush, or the suits of all
	 * five cards match. For each Card after the first, a counter is 
	 * incremented when its suit matches the first Card's suit. If four matches
	 * are registered then the hand is determined to be flush.
	 * 
	 * 
	 * @param hand the five Card ArrayList to be evaluated
	 * @return the appropriate boolean condition for the hand's flush state
	 */
	private static boolean isFlush(ArrayList<Card> hand) {
		boolean isFlush = false;
		int suiteMatchCount = 0; 
		
		for (int i = 1; i < 5; i++) { //The number of cards matching the first card's suit are found
			if (hand.get(0).getSuit() == hand.get(i).getSuit())
				suiteMatchCount++;
		}
		
		if (suiteMatchCount == 4) //If all suits match the hand is flush
			isFlush = true;
		
		return isFlush;
	}
	
	/**
	 * This method checks if a five Card hand is straight, or all of its Card's
	 * ranks are consecutive. Once a hand is ordered the number of decrement
	 * matches are counted, where the highest ranking Card's rank minus one
	 * should equal the rank of the Card one index from it and so on. If four
	 * of these matches are registered the hand is determined to be straight. 
	 * If an ace is the highest ranking card it can be straight with either a 
	 * Ten to King or Two to Five rank sequence, so in this case the decrement
	 * matches are tested for the last four Cards in the hand, then confirmed 
	 * straight if these four begin with either a King or Five rank card.  
	 * 
	 * 
	 * @param hand the five Card ArrayList to be evaluated
	 * @return the appropriate boolean condition for the hand's straight state
	 */
	private static boolean isStraight(ArrayList<Card> hand) {
		boolean isStraight = false;
		int decMatchCount = 0;
		
		if (hand.get(0).getRank() == 12) { //Ace special case
			for (int i = 2; i < 5; i++) {
				if (hand.get(1).getRank() - i + 1 == hand.get(i).getRank())
					decMatchCount++;
			}
			if ((decMatchCount == 3) && ((hand.get(1).getRank() == 11) || (hand.get(1).getRank() == 3)))
				isStraight = true;
		}
		
		else { //General straight check
			decMatchCount = 0;
			for (int i = 1; i < 5; i++) {
				if (hand.get(0).getRank() - i == hand.get(i).getRank())
					decMatchCount++;
			}
			if (decMatchCount == 4)
				isStraight = true;
		}
		
		return isStraight;
	}
	
	/**
	 * To assist in the finding of straights and evaluating ranking disputes, 
	 * this method sorts a five Card Arraylist hand in ascending order of Card 
	 * ranks.
	 * 
	 * @param hand the unsorted Card Arraylist
	 * @return an ArrayList cards ordered from highest to lowest ranks
	 */
	private static ArrayList<Card> orderByRank(ArrayList<Card> hand){
		ArrayList<Card> oHand = new ArrayList<Card>(), uHand = new ArrayList<Card>();
		for (Card card : hand) //The passed hand is copied to the unorganized hand uHand to prevent privacy leaks.
			uHand.add(card);
		
		while (uHand.size() > 0) { //As cards are transferred off of the old hand the next highest card is determined until all are gone		
			int highRank = 0, highRankIndex = 0;
			
			for (int i = 0; i < uHand.size(); i++) { //Each card in the hand is tested for being the highest
				if (uHand.get(i).getRank() > highRank) { //If the current card has a higher rank it becomes the new high card
					highRank = uHand.get(i).getRank();
					highRankIndex = i;
				}
			}
			
			oHand.add(uHand.get(highRankIndex)); //The highest card is added to the new hand and removed from the old
			uHand.remove(highRankIndex);
		}
		
		return oHand;
	}
	
	/**
	 * All 21 unique combinations of five card hands from a seven card set are
	 * found by the below algorithm. Two blank spaces in the seven card set are
	 * cycled, their indices being exempt from a hand possibility when it is 
	 * written to the master list.
	 * 
	 * @param allCards the combined community and a given player's hole cards
	 * @return An ArrayList of all five card hand combinations
	 */
	public static ArrayList<ArrayList<Card>> comb(ArrayList<Card> allCards) {
		ArrayList<ArrayList<Card>> combs = new ArrayList<ArrayList<Card>>(); //A 21 ArrayList ArrayList is created to store all possible combinations
		for (int i = 0; i < 21; i++)
			combs.add(new ArrayList<Card>());
		
		int writeIndex = 0; //The index of the 21 ArrayList ArrayList is cycled for each blank2 change
		for (int blank1 = 0; blank1 < 6; blank1++) { //The first blank advanced after the second has gone through each space following it
			for (int blank2 = blank1 + 1; blank2 < 7; blank2++) { //The second blank cycles through each index following the first blank
				for (int index = 0; index < 7; index++) { //Each Card index in allCards is ran through
					if ((index != blank1) && (index != blank2)) //If the index of the card doesn't fall on a blank it is written to the current combination index
						combs.get(writeIndex).add(allCards.get(index));
				}
				writeIndex++;
			}
		}	
		return combs;
	}
	
	// Dispute Settlers
	//
	// In the cases that two hands have the same rank the ranks of the cards 
	// that make up those hands will have to be compared to determine which
	// hand is higher. The following methods settle these disputes based on the
	// type of algorithm required for the conflicting hand rank.
	
	/**
	 * The first step in the dispute process is to direct the hands to the 
	 * appropriate dispute method, achieved in the below if-else branches.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @param commonRank the common rank between the two hands
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int dispute(ArrayList<Card> hand1, ArrayList<Card> hand2, int commonRank) {
		hand1 = orderByRank(hand1); //Hands are ordered at this stage to mitigate repeated code
		hand2 = orderByRank(hand2);
		
		if ((commonRank == STRAIGHT_FLUSH) || (commonRank == STRAIGHT) || (commonRank == ROYAL_FLUSH))
			return disputeStraight(hand1, hand2);
		else if ((commonRank == FLUSH) || (commonRank == HIGH_CARD))
			return disputeNonConsec(hand1, hand2);
		else if ((commonRank == FULL_HOUSE) ||(commonRank == TWO_PAIRS))
			return disputeTwoRankSet(hand1, hand2);
		else //All other hands must be a one rank set type, sent to the appropriate dispute method
			return disputeOneRankSet(hand1, hand2);
	}
	
	/**
	 * For straight type hands, specifically straight flushes and straights,
	 * the ranks of the first and last cards will be compared for each hand
	 * to determine which has the higher value.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int disputeStraight(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		if ((hand1.get(0).getRank() == hand2.get(0).getRank()) && (hand1.get(4).getRank() == hand2.get(4).getRank()))
			return HANDS_EQUAL;
		else if ((hand1.get(0).getRank() > hand2.get(0).getRank()) && (hand1.get(4).getRank() > hand2.get(4).getRank()))
			return HAND_ONE_GREATER;
		else
			return HAND_TWO_GREATER;
	}
	
	/**
	 * For hand ranks with non-consecutive card ranks, such as flush and high
	 * card, the cards for both hands are scanned through from highest to 
	 * lowest ranking, returning a result when one has a card rank higher
	 * than the other. This method can also be used to evaluate the "kicker"
	 * cards from rank set type hands if their initial tests don't settle the
	 * dispute.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int disputeNonConsec(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		for (int i = 0; i < hand1.size(); i++) {
			if (hand1.get(i).getRank() > hand2.get(i).getRank())
				return HAND_ONE_GREATER;
			else if (hand1.get(i).getRank() < hand2.get(i).getRank())
				return HAND_TWO_GREATER;
		}
		return HANDS_EQUAL;
	}
	
	/**
	 * For hand ranks where there is one set of cards with the same rank, such 
	 * as two pairs and three of a kind, the rank of this set is found in each
	 * hand. The rank of the sets in the hands are then compared to determine
	 * which is greater and return the result/ While the set scan is being 
	 * processed the cards not part of the set are copied to the hand's 
	 * respective "kicker" list in case both hands have the same set rank, in
	 * which case said kicker cards are evaluated by the non consecutive 
	 * dispute function to determine the victor. 
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int disputeOneRankSet(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int hand1SetRank = -1, hand2SetRank = -1;
		ArrayList<Card> hand1Kicker = new ArrayList<Card>(), hand2Kicker = new ArrayList<Card>();
		
		for (Card card : hand1) { //Each card in hand1 one is checked for if it is in the set
			int rankMatches = 0;
			
			for (int i = 0; i < 5; i++) { //Each card matching the rank of the current card increments a counter
				if (card.getRank() == hand1.get(i).getRank())
					rankMatches++;
			}
			
			if (rankMatches > 1) //If the current card is part of the set its rank is set to the set rank
				hand1SetRank = card.getRank();
			else //Lone cards are added to the kicker for potential dispute settling
				hand1Kicker.add(card);
		}
		
		for (Card card : hand2) { //The above process is repeated for hand2
			int rankMatches = 0;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand2.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches > 1) 
				hand2SetRank = card.getRank();
			else
				hand2Kicker.add(card);
		}
		
		if (hand1SetRank > hand2SetRank) //The highest ranking set determines the highest hand
			return HAND_ONE_GREATER;
		else if (hand1SetRank < hand2SetRank)
			return HAND_TWO_GREATER;
		else
			return disputeNonConsec(hand1Kicker, hand2Kicker); //The two are disputed if they have the same rank set
	}
	
	/**
	 * For hands with two sets of cards with the same rank, specifically two
	 * pairs and full house, the ranks of both sets will be found. For a full
	 * house the three card sets of each hand will be the first to be 
	 * compared, followed by the two card set. For two pairs the highest ranked
	 * set will be compared first, then the second, then the two kicker cards
	 * will be compared.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int disputeTwoRankSet(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int hand1SetRankA = -1, hand1SetRankB = -1, hand2SetRankA = -1, hand2SetRankB = -1; //The higher value set in the hands will be set A, the other set B
		ArrayList<Card> hand1Kicker = new ArrayList<Card>(), hand2Kicker = new ArrayList<Card>();
		
		for (int o = 0; o < 5; o++) { //Each card in hand1 is checked for being in a rank set
			int rankMatches = 0;
			
			for (int i = 0; i < 5; i++) { //The number of cards with the same rank as the current card increments a counter 
				if (hand1.get(o).getRank() == hand1.get(i).getRank())
					rankMatches++;
			}
			
			if ((rankMatches == 2) && (hand1SetRankB == -1)) { //If a pair is found it is initially set to set B
				hand1SetRankB = hand1.get(o).getRank();
				o++;
			}
			else if (rankMatches == 3) { //A set of three is always set to set A
				hand1SetRankA = hand1.get(o).getRank();
				o += 2;
			}
			else if (rankMatches == 2) { //If a second pair is found then the hand is two pairs, the highest ranking pair is designated set A and the current is set B
				hand1SetRankA = hand1SetRankB;
				hand1SetRankB = hand1.get(o).getRank();
				o++;
			}
			else //The lone card for two pair hands is added to the kicker
				hand1Kicker.add(hand1.get(o));
			}
		
		for (int o = 0; o < 5; o++) { //The above process is repeated for hand2
			int rankMatches = 0;
			
			for (int i = 0; i < 5; i++) { 
				if (hand2.get(o).getRank() == hand2.get(i).getRank())
					rankMatches++;
			}
			
			if ((rankMatches == 2) && (hand2SetRankB == -1)) { 
				hand2SetRankB = hand2.get(o).getRank();
				o++;
			}
			else if (rankMatches == 3) { 
				hand2SetRankA = hand2.get(o).getRank();
				o += 2;
			}
			else if (rankMatches == 2) { 
				hand2SetRankA = hand2SetRankB;
				hand2SetRankB = hand2.get(o).getRank();
				o++;
			}
			else 
				hand2Kicker.add(hand2.get(o));
			}
		
		if (hand1SetRankA > hand2SetRankA) //Set A is first compared
			return HAND_ONE_GREATER;
		else if (hand2SetRankA > hand1SetRankA)
			return HAND_TWO_GREATER;
		else if (hand1SetRankB > hand2SetRankB) //Set B is compared if set A is equal
			return HAND_ONE_GREATER;
		else if (hand2SetRankB > hand1SetRankB)
			return HAND_TWO_GREATER;
		else //If the hands are two pairs and both ranks sets are equal the kicker card is evaluated
			return disputeNonConsec(hand1Kicker, hand2Kicker);
	}
}