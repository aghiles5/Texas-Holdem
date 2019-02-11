import java.util.ArrayList;
import java.util.Random;

/**
 * This class provides an environment for testing methods from the Showdown
 * class. Currently, a five card hand is dealt from a shuffled 52 card deck to
 * test basic hand ranking determination, such as straights, flushes, and
 * pairs, for one player.  
 *
 * @author Adam Hiles
 * @version 02/05/19
 */
public class ShowdownTest {

	/**
	 * The main method will execute a test. In this case, the ranking of a
	 * random, five card hand is gotten from the Showdown class.
	 */
	public static void main(String args[]) {
		ArrayList<Card> testDeck = new ArrayList<Card>(); //A test deck is created and shuffled
		testDeck = newDeck();
		testDeck = shuffle(testDeck);

		
		ArrayList<Card> testHand = new ArrayList<Card>(); //A test hand is dealt from the shuffled test deck
		testHand = dealFullHand(testDeck);
		
		for (Card card : testHand) //Each card is the hand is displayed to check if the proper ranking is determined
			System.out.println(card.toString());
		
		System.out.print("\n" + Showdown.getHandRank(testHand)); //The test hand's ranking is determined by Showdown and displayed
	}
	
	/**
	 * This method returns a new, unshuffled, 52 card deck sorted sequentially
	 * in each suite, made from an ArrayList of Card objects so that cards can
	 * be removed to, say, be dealt or burnt.
	 * 
	 * @return a deck-like Card ArrayList
	 */
	public static ArrayList<Card> newDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (int s = 0; s < 4; s++) { //The creation of each rank of card is repeated for each suite
			for (int r = 0; r < 13; r++) { //Each rank of card is added to the deck
				deck.add(new Card(s, r));
			}
		}		
		return deck;
	}
	
	/**
	 * From a given ArrayList deck of Card objects this method will compose a 
	 * full, five Card hand, stored in another ArrayList. Each Card added to 
	 * the hand is copied from the front Card of the deck which is then removed 
	 * so that the next Card in the deck is moved to the from to be potentially 
	 * dealt to the hand.
	 * 
	 * @param deck the ArrayList from which Card objects are taken
	 * @return a five Card ArrayList
	 */
	public static ArrayList<Card> dealFullHand(ArrayList<Card> deck) {
		ArrayList<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < 5; i++) { //A Card is added to the hand and subsequently removed from the deck five times
			hand.add(deck.get(0)); 
			deck.remove(0);
		}
		return hand;
	}
	
	/**
	 * This method is intended to shuffle a 52 Card ArrayList deck, though the
	 * algorithm would work for any Card ArrayList.
	 * 
	 * @param uDeck the unshuffled Card ArrayList
	 * @return a shuffled Card ArrayList
	 */
	public static ArrayList<Card> shuffle(ArrayList<Card> uDeck) {
		ArrayList<Card> sDeck = new ArrayList<Card>();
		while (uDeck.size() > 0) { //Cards are transferred from the old to new deck until all are moved
			Random rand = new Random();
			int randIndex = rand.nextInt(uDeck.size());
			sDeck.add(uDeck.get(randIndex)); //A random Card's index is copied to the new sDeck
			uDeck.remove(randIndex);//The copied Card is removed from the old deck to prevent duplicates
		}
		return sDeck;
	}
}
