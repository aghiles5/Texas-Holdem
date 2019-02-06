package cardPackage;

import java.util.ArrayList;

/**
 * The Showdown class handles methods involved with the "showdown" phase in
 * Texas Holdem where players reveal their cards and vie for the pot by 
 * making the best ranking hand. Currently, only the basic ranking 
 * determination for one full, five card hand is implemented, returning
 * the ranking name based on the 
 * 
 * @author Adam
 * @version 02/06/19
 */
public class Showdown {

	private static final String[] RANKING_KEY = {"High Card", "One Pair", "Two Pairs", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush", "Royal Flush"};
	
	public static void main(String args[]) {
	}
	
	/**
	 * For a five Card Arraylist hand this method will determine where it falls
	 * on the Texas Holdem hand rankings, listed in the RANKING_KEY array from
	 * lowest to highest. The correct string from the key for the determined 
	 * ranking is returned to the caller.  
	 * 
	 * @param hand the Card Arraylist hand to have its ranking determined
	 * @return the name of the hand's ranking
	 */
	public static String getHandRank(ArrayList<Card> hand) {
		hand = orderByRank(hand); //The Cards are ordered from highest to lowest rank
		
		//Checks for flush/straight type hands
		
		boolean flush = false, straight = false;
		
		//The suites of all the cards are tested if they are identical, or the hand is flush
		
		int suiteMatchCount = 0; 
		for (int i = 1; i < 5; i++) {
			if (hand.get(0).getSuite() == hand.get(i).getSuite())
				suiteMatchCount++;
		}
		if (suiteMatchCount == 4)
			flush = true;
		
		//The rankings of cards are tested if they are in series, or the hand is straight
		
		int decMatchCount = 0;
		for (int i = 1; i < 5; i++) {
			if (hand.get(0).getRank() - i == hand.get(i).getRank())
				decMatchCount++;
		}
		if ((decMatchCount == 4) || ((decMatchCount == 3) && (hand.get(0).getRank() == 12 ) && (hand.get(4).getRank() == 0)))
			straight = true;
		
		//The appropriate flush/straight type ranking is returned based on the above test results, else the method continues
		
		if ((flush == true) && (straight == true) && (hand.get(0).getRank() == 12) && (hand.get(4).getRank() == 0)) 
			return RANKING_KEY[9];
		else if ((flush == true) && (straight == true))
			return RANKING_KEY[8];
		else if (flush == true)
			return RANKING_KEY[5];
		else if (straight == true)
			return RANKING_KEY[4];
		
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
				return RANKING_KEY[7];
			else if (rankMatches == 2)
				threeKind = true;
			else if (rankMatches == 1)
				pairs += 0.5;
			}
		
		//Based on the above determined sets of equal ranks the appropriate rank based rankings are returned
		
		if ((threeKind == true) && (pairs == 1.0))
			return RANKING_KEY[6];
		else if (threeKind == true)
			return RANKING_KEY[3];
		else if (pairs == 2.0)
			return RANKING_KEY[2];
		else if (pairs == 1.0)
			return RANKING_KEY[1];
		
		return RANKING_KEY[0]; // The ranking defaults to "High Card"
	}
	
	/**
	 * To assist in the finding of straights, this method sorts a five Card 
	 * Arraylist hand in ascending order of Card ranks.
	 * 
	 * @param uHand the unsorted Card Arraylist
	 * @return an ArrayList cards ordered from highest to lowest ranks
	 */
	public static ArrayList<Card> orderByRank(ArrayList<Card> uHand){
		ArrayList<Card> oHand = new ArrayList<Card>();
		while (uHand.size() > 0) { //As cards are transferred off of the old hand the next highest card is determined until all are gone		
			int highRank = 0, highRankIndex = 0; 
			for (int i = 0; i < uHand.size(); i++) { //Each card in the hand is tested for being the highest
				Card card = uHand.get(i);
				if (card.getRank() > highRank) { //If the current card has a higher rank it becomes the new high card
					highRank = card.getRank();
					highRankIndex = i;
				}
			}
			oHand.add(uHand.get(highRankIndex)); //The highest card is added to the new hand and removed from the old
			uHand.remove(highRankIndex);
		}
		return oHand;
	}
}
