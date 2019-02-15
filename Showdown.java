import java.util.ArrayList;

/**
 * The Showdown class handles methods involved with the "showdown" phase in
 * Texas Holdem where players reveal their cards and vie for the pot by 
 * making the best ranking hand. Currently, only the basic ranking 
 * determination for one full, five card hand is implemented, returning
 * the rank number.
 *
 * DETERMINE HIGH CARD
 * 
 * @author Adam
 * @version 02/09/19
 */
public class Showdown {

	private static final String[] RANKING_KEY = {"High Card", "One Pair", 
			"Two Pairs", "Three of a Kind", "Straight", "Flush", "Full House", 
			"Four of a Kind", "Straight Flush", "Royal Flush"};
	
	public static void main(String args[]) {
	}
	
	/**
	 * Will determine a player's highest hand based on their hole cards and
	 * the community cards.
	 * 
	 * @param community
	 * @param hole
	 */
	public static void detHighestHand(ArrayList<Card> community, ArrayList<Card> hole) {	
	}
	
	public static String byteToString(byte i) {
		return RANKING_KEY[i];
	}
	
	/**
	 * For a five Card Arraylist hand this method will determine where it falls
	 * on the Texas Holdem hand rankings, listed in the RANKING_KEY array from
	 * lowest to highest. The correct rank number according to the key is 
	 * returned to the caller. 
	 * 
	 * @param hand the Card Arraylist hand to have its ranking determined
	 * @return the integer ranking of the hand
	 */
	public static byte getHandRank(ArrayList<Card> hand) {
		hand = orderByRank(hand); //The Cards are ordered from highest 
		                          //to lowest rank
		
		//Checks for flush/straight type hands
		
		boolean isFlush = isFlush(hand);
		
		boolean isStraight = isStraight(hand);
		
		//The appropriate flush/straight type ranking is returned based on the 
		//above test results, else the method continues
		
		if ((isFlush == true) && (isStraight == true) && (hand.get(0).getRank() == 12) && (hand.get(4).getRank() == 8)) 
			return 9;
		else if ((isFlush == true) && (isStraight == true))
			return 8;
		else if (isFlush == true)
			return 5;
		else if (isStraight == true)
			return 4;
		
		// Pair checking
		
		boolean threeKind = false;
		double pairs = 0.0;
		
		for (Card card : hand) { //Each card is tested against all other cards 
			                     //in the hand for which ones have matching 
			                     //ranks	
			int rankMatches = -1;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches == 3) //If four cards have matching ranks "Four of 
				                  //a Kind" is immediately returned
				return 7;
			else if (rankMatches == 2)
				threeKind = true;
			else if (rankMatches == 1)
				pairs += 0.5;
			}
		
		//Based on the above determined sets of equal ranks the appropriate 
		//rank based rankings are returned
		
		if ((threeKind == true) && (pairs == 1.0))
			return 6;
		else if (threeKind == true)
			return 3;
		else if (pairs == 2.0)
			return 2;
		else if (pairs == 1.0)
			return 1;
		
		return 0; // The ranking defaults to "High Card"
	}
	
	//Dispute settlers (WIP)
	//Will return 0 if the hands are equal, 1 if the first hand is higher, 2 if the second is higher.
	
	public static int disputeStraight(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		hand1 = orderByRank(hand1);
		hand2 = orderByRank(hand2);
		
		if ((hand1.get(0).getRank() == hand1.get(0).getRank()) && (hand1.get(4).getRank() == hand1.get(4).getRank()))
			return 0;
		else if ((hand1.get(0).getRank() > hand1.get(0).getRank()) && (hand1.get(4).getRank() > hand1.get(4).getRank()))
			return 1;
		else
			return 2;
	}
	
	public static int disputeNonConsec(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		hand1 = orderByRank(hand1);
		hand2 = orderByRank(hand2);
		
		for (int i = 0; i < 5; i++) {
			if (hand1.get(i).getRank() > hand2.get(i).getRank())
				return 1;
			else if (hand1.get(i).getRank() < hand2.get(i).getRank())
				return 2;
		}
		return 0;
	}
	
	public static int disputeOneRankSet(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int hand1SetRank = 0, hand2SetRank = 0;
		
		for (Card card : hand1) {
			int rankMatches = 0;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand1.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches > 1) {
				hand1SetRank = card.getRank();
				break;
			}
		}
		
		for (Card card : hand2) {
			int rankMatches = 0;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand1.get(i).getRank())
					rankMatches++;
			}
			if (rankMatches > 1) {
				hand2SetRank = card.getRank();
				break;
			}
		}
		
		if (hand1SetRank > hand2SetRank)
			return 1;
		else if (hand1SetRank < hand2SetRank)
			return 2;
		else
			return 0;
	}
	
	public static int disputeTwoRankSet(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		hand1 = orderByRank(hand1);
		hand2 = orderByRank(hand2);
		
		int hand1SetRankA = -1, hand1SetRankB = -1, hand2SetRankA = -1, hand2SetRankB = -1;
		
		for (Card card : hand1) {
			int rankMatches = 0;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand1.get(i).getRank())
					rankMatches++;
			}
			if ((rankMatches > 1) && (hand1SetRankA == -1))
				hand1SetRankA = card.getRank();
			else if ((rankMatches > 1) && (card.getRank() != hand1SetRankA)) {
				hand1SetRankB = card.getRank();
			}
		}
		
		for (Card card : hand2) {
			int rankMatches = 0;
			for (int i = 0; i < 5; i++) { 
				if (card.getRank() == hand2.get(i).getRank())
					rankMatches++;
			}
			if ((rankMatches > 1) && (hand2SetRankA == -1))
				hand2SetRankA = card.getRank();
			else if ((rankMatches > 1) && (card.getRank() != hand2SetRankA)) {
				hand2SetRankB = card.getRank();
			}	
		}	
		return 0;
	}
	
	//Private Helper Methods
	
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
		for (int i = 1; i < 5; i++) {
			if (hand.get(0).getSuit() == hand.get(i).getSuit())
				suiteMatchCount++;
		}
		if (suiteMatchCount == 4)
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
	
	private static ArrayList<ArrayList<Card>> combComm3(ArrayList<Card> comm) {
		ArrayList<ArrayList<Card>> commCombs3 = new ArrayList<ArrayList<Card>>();
		for (byte i = 0; i < 10; i++)
			commCombs3.add(new ArrayList<Card>());
		byte writeIndex = 0;
		for (byte blank1 = 0; blank1 < 4; blank1++) {
			for (byte blank2 = (byte) (blank1 + 1); blank2 > blank1 && blank2 < 5; blank2++) {
				for (byte index = 0; index < 5; index++) {
					if ((index != blank1) && (index != blank2))
						commCombs3.get(writeIndex).add(comm.get(index));
				}
				writeIndex++;
			}
		}
		
		return commCombs3;
	}
	
	private static ArrayList<ArrayList<Card>> combComm4(ArrayList<Card> comm) {
		ArrayList<ArrayList<Card>> commCombs4 = new ArrayList<ArrayList<Card>>();
		for (byte i = 0; i < 5; i++)
			commCombs4.add(new ArrayList<Card>());
		for (byte writeIndex = 0; writeIndex < 5; writeIndex++) {
			for (byte index = 0; index < 5; index++) {
				if (index != writeIndex)
					commCombs4.get(writeIndex).add(comm.get(index));
			}
		}
		return commCombs4;
	}
}
