import java.util.ArrayList;

/**
 * In the cases that two hands have the same rank the ranks of the cards 
 * that make up those hands will have to be compared to determine which
 * hand is higher. The following class settles these disputes based on the
 * type of algorithm required for the conflicting hand rank.
 * 
 * @author Adam Hiles
 * @version 03/02/19
 */
public class Dispute extends Showdown{
	
	/**
	 * The first step in the dispute process is to direct the hands to the 
	 * appropriate dispute method, achieved in the below if-else branches.
	 * 
	 * @param hand1 the first hand for the comparison
	 * @param hand2 the second hand for the comparison
	 * @param commonRank the common rank between the two hands
	 * @return a byte corresponding to the evaluated relation
	 */
	public static int dispute(ArrayList<Card> hand1, ArrayList<Card> hand2, int commonRank) {
		hand1 = orderCardsByRank(hand1); //Hands are ordered at this stage to mitigate repeated code
		hand2 = orderCardsByRank(hand2);
		
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
				return HAND_TWO_GREATER;
			else if ((hand1.get(0).getRank() != 12) && (hand1.get(4).getRank() != 0))
				return HAND_ONE_GREATER;
			else
				return HANDS_EQUAL;
		}
		
		if (hand1.get(4).getRank() == hand2.get(4).getRank()) //General Case
			return HANDS_EQUAL;
		else if (hand1.get(4).getRank() > hand2.get(4).getRank())
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
