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
 * @author Brayden, Adam
 * @version 02/20/18
 */
public class Deck {

	private ArrayList<Card> deck = new ArrayList<Card>();
	
	/**
	 * This constructor creates a new, unshuffled, 52 card deck sorted 
	 * consecutively in each suite, made from an ArrayList of Card objects so 
	 * that cards can be removed to, say, be dealt or burnt.
	 */
	public Deck() {
		for (int s = 0; s < 4; s++) { //The creation of each rank of card is repeated for each suite
			for (int r = 0; r < 13; r++) { //Each rank of card is added to the deck
				deck.add(new Card(s, r));
			}
		}	
		shuffle();
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
	
	public Card dealSingle() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}

	private void burnCard(){
		deck.remove(0);
	}

	public ArrayList<Card> dealCard(ArrayList<Card> middleCards){
		burnCard();
		if(middleCards.size() < 3){
			for(int i = 0; i < 3; i++){
				middleCards.add(deck.get(0));
				deck.remove(0);
			}
		}
		else{
			middleCards.add(deck.get(0));
			deck.remove(0);
		}
		
		return middleCards;
	}
}
