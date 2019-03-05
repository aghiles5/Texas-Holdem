import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int rank;
	
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
	
	private static final int THIS_HAND_GREATER = 1;
	private static final int OTHER_HAND_GREATER = 2;
	private static final int HANDS_EQUAL = 0;
	
	public Hand() {}
	
	public int getRank() {
		return rank;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void addCard(Card card) {
		if (cards.size() < 5)
			cards.add(card);
			if (cards.size() == 5)
				detRank();
	}
	
	public int compareHand(Hand hand) {
		if (rank > hand.getRank())
			return THIS_HAND_GREATER;
		else if (rank < hand.getRank())
			return OTHER_HAND_GREATER;
		else
			return dispute(hand);
	}
	
	/**
	 * To assist in the finding of straights and evaluating ranking disputes, 
	 * this method sorts the Hand's cards in ascending order of Card 
	 * ranks.
	 * 
	 * @param none
	 * @return an ArrayList of Cards ordered from highest to lowest ranks
	 */
	public ArrayList<Card> orderCards(){
		ArrayList<Card> oCards = new ArrayList<Card>(), uCards = new ArrayList<Card>();
		for (Card card : cards) //The passed hand is copied to the unorganized hand uHand to prevent privacy leaks.
			uCards.add(card);
		
		while (uCards.size() > 0) { //As cards are transferred off of the old hand the next highest card is determined until all are gone		
			int highRank = 0, highRankIndex = 0;
			
			for (int i = 0; i < uCards.size(); i++) { //Each card in the hand is tested for being the highest
				if (uCards.get(i).getRank() > highRank) { //If the current card has a higher rank it becomes the new high card
					highRank = uCards.get(i).getRank();
					highRankIndex = i;
				}
			}
			
			oCards.add(uCards.get(highRankIndex)); //The highest card is added to the new hand and removed from the old
			uCards.remove(highRankIndex);
		}
		
		return oCards;
	}
	
	/**
	 * For a five Card Arraylist hand this method will determine where it falls
	 * on the Texas Holdem hand rankings, listed in the RANKING_KEY array from
	 * lowest to highest. The correct rank number is returned to the caller.
	 * 
	 * @param hand the Card Arraylist hand to have its ranking determined
	 * @return the integer ranking of the hand
	 */
	private void detRank() {
		ArrayList<Card> oCards = orderCards(); //The Cards are ordered from highest to lowest rank
		
		//Checks for flush/straight type hands
		
		boolean isFlush = isFlush();
		
		boolean isStraight = isStraight();
		
		//The appropriate flush/straight type ranking is returned based on the above test results, else the method continues
		
		if ((isFlush == true) && (isStraight == true) && (oCards.get(0).getRank() == 12) && (oCards.get(4).getRank() == 8)) 
			rank = ROYAL_FLUSH;
		else if ((isFlush == true) && (isStraight == true))
			rank = STRAIGHT_FLUSH;
		else if (isFlush == true)
			rank = FLUSH;
		else if (isStraight == true)
			rank = STRAIGHT;
		
		// Pair checking
		
		boolean threeKind = false;
		double pairs = 0.0;
		
		for (Card card : oCards) { //Each card is tested against all other cards in the hand for which ones have matching ranks	
			int rankMatches = -1;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == oCards.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches == 3) //If four cards have matching ranks "Four of a Kind" is immediately returned
				rank = FOUR_OF_A_KIND;
			else if (rankMatches == 2)
				threeKind = true;
			else if (rankMatches == 1)
				pairs += 0.5;
			}
		
		//Based on the above determined sets of equal ranks the appropriate rank based rankings are returned
		
		if ((threeKind == true) && (pairs == 1.0))
			rank = FULL_HOUSE;
		else if (threeKind == true)
			rank = THREE_OF_A_KIND;
		else if (pairs == 2.0)
			rank = TWO_PAIRS;
		else if (pairs == 1.0)
			rank = ONE_PAIR;
		
		rank = HIGH_CARD; // The ranking defaults to "High Card"
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
	private boolean isFlush() {
		boolean isFlush = false;
		int suiteMatchCount = 0; 
		
		for (int i = 1; i < 5; i++) { //The number of cards matching the first card's suit are found
			if (cards.get(0).getSuit() == cards.get(i).getSuit())
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
	private boolean isStraight() {
		boolean isStraight = false;
		int decMatchCount = 0;
		
		if (cards.get(0).getRank() == 12) { //Ace special case
			for (int i = 2; i < 5; i++) {
				if (cards.get(1).getRank() - i + 1 == cards.get(i).getRank())
					decMatchCount++;
			}
			if ((decMatchCount == 3) && ((cards.get(1).getRank() == 11) || (cards.get(1).getRank() == 3)))
				isStraight = true;
		}
		
		else { //General straight check
			decMatchCount = 0;
			for (int i = 1; i < 5; i++) {
				if (cards.get(0).getRank() - i == cards.get(i).getRank())
					decMatchCount++;
			}
			if (decMatchCount == 4)
				isStraight = true;
		}
		
		return isStraight;
	}
	
	/**
	 * The first step in the dispute process is to direct the hands to the 
	 * appropriate dispute method, achieved in the below if-else branches.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @param commonRank the common rank between the two hands
	 * @return a byte corresponding to the evaluated relation
	 */
	public int dispute(Hand hand) {
		ArrayList<Card> thisHand = orderCards(); //Hands are ordered at this stage to mitigate repeated code
		ArrayList<Card> otherHand = hand.orderCards();
		
		if ((rank == STRAIGHT_FLUSH) || (rank == STRAIGHT) || (rank == ROYAL_FLUSH))
			return disputeStraight(thisHand, otherHand);
		else if ((rank == FLUSH) || (rank == HIGH_CARD))
			return disputeNonConsec(thisHand, otherHand);
		else if ((rank == FULL_HOUSE) ||(rank == TWO_PAIRS))
			return disputeTwoRankSet(thisHand, otherHand);
		else //All other hands must be a one rank set type, sent to the appropriate dispute method
			return disputeOneRankSet(thisHand, otherHand);
	}
	
	/**
	 * For straight type hands, specifically straight flushes and straights,
	 * the ranks of the last card will be compared for each hand to determine 
	 * which has the higher value. If either hand is the lowest ace containing
	 * straight it will always be the lowest ranking hand as long as the other
	 * straight is not equivalent.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @return a byte corresponding to the evaluated relation
	 */
	private static int disputeStraight(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		if (((hand1.get(0).getRank() == 12) && (hand1.get(4).getRank() == 0)) || ((hand2.get(0).getRank() == 12) && (hand2.get(4).getRank() == 0))) { //Ace special case
			if ((hand2.get(0).getRank() != 12) && (hand2.get(4).getRank() != 0))
				return OTHER_HAND_GREATER;
			else if ((hand1.get(0).getRank() != 12) && (hand1.get(4).getRank() != 0))
				return THIS_HAND_GREATER;
			else
				return HANDS_EQUAL;
		}
		
		if (hand1.get(4).getRank() == hand2.get(4).getRank()) //General Case
			return HANDS_EQUAL;
		else if (hand1.get(4).getRank() > hand2.get(4).getRank())
			return THIS_HAND_GREATER;
		else
			return OTHER_HAND_GREATER;
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
				return THIS_HAND_GREATER;
			else if (hand1.get(i).getRank() < hand2.get(i).getRank())
				return OTHER_HAND_GREATER;
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
			return THIS_HAND_GREATER;
		else if (hand1SetRank < hand2SetRank)
			return OTHER_HAND_GREATER;
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
			return THIS_HAND_GREATER;
		else if (hand2SetRankA > hand1SetRankA)
			return OTHER_HAND_GREATER;
		else if (hand1SetRankB > hand2SetRankB) //Set B is compared if set A is equal
			return THIS_HAND_GREATER;
		else if (hand2SetRankB > hand1SetRankB)
			return OTHER_HAND_GREATER;
		else //If the hands are two pairs and both ranks sets are equal the kicker card is evaluated
			return disputeNonConsec(hand1Kicker, hand2Kicker);
	}
}
