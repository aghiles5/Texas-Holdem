/** 
 * The Card class stores and handles information for a virtual playing card. 
 * The suite of the card is stored as an integer corresponding to the index of 
 * the suite in the class constant SUITE_KEY array and its rank is similarly 
 * an integer corresponding to the rank in the class constant RANK_KEY array.
 * E.g. the Ace of Spades would be classified by suite 1 and rank 0 as per the
 * mentioned keys.
 * 
 * @author Adam Hiles
 * @version 02/05/19
 */
public class Card {

	//Instance Variables
	
	private int suite;
	private int rank;
	
	//Class Constants
	
	private static final String[] SUITE_KEY = {"Hearts", "Spades", "Diamonds", 
			"Clubs"};
	private static final String[] RANK_KEY = {"Ace", "Two", "Three", "Four", 
			"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	//Constructors
	
	
	/**
	 * A new card object requires that caller to provide the instanced suite
	 * and rank integers, which will be directly set to said variables.
	 * Currently there is no check for proper values: 0 to 3 for the suite and
	 * 0 to 12 for the rank. Passing incorrect values will not result any error
	 * in most aspects of usage (besides logical) except for in the toString()
	 * method where the indexes will by out of the range of the keys.
	 * 
	 * @param setSuite the caller's specified suite 
	 * @param setRank the caller's specified suite
	 */
	public Card(int setSuite, int setRank) {
		suite = setSuite;
		rank = setRank;
	}
	
	//Getters
	
	/**
	 * Returns the integer representing the card's suite.
	 * 
	 * @return the suite integer
	 */
	public int getSuite() {
		return suite;
	}
	
	/**
	 * Returns the integer representing the card's rank.
	 * 
	 * @return the rank integer
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * For user interfaces the name of the card is converted from its integer
	 * components to a string in the form "(rank) of (suite)" by retrieving its
	 * corresponding string from the keys. E.g. a card with suite 2 (Diamonds) 
	 * and rank 7 (Eight) will return the string "Eight of Diamonds".
	 * 
	 * @return a string of object information in a user friendly form
	 */
	public String toString() {
		String cardName = RANK_KEY[rank] + " of " + SUITE_KEY[suite];
		return cardName;
	}
	

}
