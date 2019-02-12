/** 
 * The Card class stores and handles information for a virtual playing card. 
 * The suit of the card is stored as an byte corresponding to the index of 
 * the suit in the class constant SUITE_KEY array and its rank is similarly 
 * an byte corresponding to the rank in the class constant RANK_KEY array.
 * E.g. the Ace of Spades would be classified by suite 1 and rank 12 as per the
 * mentioned keys.
 * 
 * @author Adam Hiles
 * @version 02/11/19
 */
public class Card {

	//Instance Variables
	
	private byte suit;
	private byte rank;
	
	//Class Constants
	
	private static final String[] SUIT_KEY = {"Hearts", "Spades", "Diamonds", 
			"Clubs"};
	private static final String[] RANK_KEY = {"Two", "Three", "Four", "Five", 
			"Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", 
			"Ace"};
	
	//Constructors
	
	/**
	 * A new card object requires that caller to provide the instanced suit
	 * and rank integers, which will be directly set to said variables.
	 * Currently there is no check for proper values: 0 to 3 for the suit and
	 * 0 to 12 for the rank. Passing incorrect values will not create errors 
	 * in most aspects of usage (besides logical) except for in the toString()
	 * method where the indexes will by out of the range of the keys.
	 * 
	 * @param suit the caller's specified suit
	 * @param rank the caller's specified rank
	 */
	public Card(byte suit, byte rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	//Getters
	
	/**
	 * Returns the byte representing the card's suit.
	 * 
	 * @return the suit byte
	 */
	public byte getSuit() {
		return suit;
	}
	
	/**
	 * Returns the byte representing the card's rank.
	 * 
	 * @return the rank byte
	 */
	public byte getRank() {
		return rank;
	}
	
	/**
	 * For user interfaces the name of the card is converted from its byte
	 * components to a string in the form "(rank) of (suit)" by retrieving its
	 * corresponding string from the keys. E.g. a card with suit 2 (Diamonds) 
	 * and rank 7 (Nine) will return the string "Nine of Diamonds".
	 * 
	 * @return a string of object information in a user friendly form
	 */
	public String toString() {
		String cardName = RANK_KEY[rank] + " of " + SUIT_KEY[suit];
		return cardName;
	}
}
