import java.util.ArrayList;
import java.util.Random;

/**
 * The Deck class handles methods regarding the creation and handling of 52
 * Card ArrayList objects representing a virtual deck of playing cards.
 * Currently decks can be shuffled and deal a full, five Card hand to an
 * ArrayList.
 * 
 * THIS CLASS IS FOR TESTING/PROOF OF CONCEPT, DECKS MAY NOT BE IMPLEMENTED AS OBJECTS.
 * 
 * @author Adam
 * @version 02/09/18
 */
public class Deck {

	private ArrayList<Card> deck = new ArrayList<Card>();
	
	/**
	 * This constructor creates a new, unshuffled, 52 card deck sorted 
	 * consecutively in each suite, made from an ArrayList of Card objects so 
	 * that cards can be removed to, say, be dealt or burnt.
	 */
	public Deck() {
		for (byte s = 0; s < 4; s++) { //The creation of each rank of card is repeated for each suite
			for (byte r = 0; r < 13; r++) { //Each rank of card is added to the deck
				deck.add(new Card(s, r));
			}
		}	
	}
	
	/**
	 * To shuffle the deck Cards are transferred at random to a temporary deck
	 * then moved back.
	 */
	public void shuffle() {
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		while (deck.size() > 0) { //Cards are transferred from the deck to temp randomly until it is empty
			Random rand = new Random();
			int randIndex = rand.nextInt(deck.size());
			tempDeck.add(deck.get(randIndex)); //A random Card's index is copied to the new sDeck
			deck.remove(randIndex); //The copied Card is removed from the old deck to prevent duplicates
		}
		deck.addAll(tempDeck); //Returns the shuffled cards from the temp deck to the deck
	}
	
	/**
	 * From the deck this method will compose a full, five Card hand, stored in 
	 * another ArrayList. Each Card added to the hand is copied from the front 
	 * Card of the deck which is then removed so that the next Card in the deck 
	 * is moved to the from to be potentially dealt to the hand.
	 * 
	 * @return a five Card ArrayList
	 */
	public ArrayList<Card> dealFullHand() {
		ArrayList<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < 5; i++) { //A Card is added to the hand and subsequently removed from the deck five times
			hand.add(deck.get(0)); 
			deck.remove(0);
		}
		return hand;
	}
	
	/**
	 * Every Card in the deck is listed.
	 */
	public void printDeck() {
		for (Card card : deck)
			System.out.println(card.toString());
		System.out.println("Number of Cards: " + deck.size() + "\n");
	}
}
