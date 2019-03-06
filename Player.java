import java.util.ArrayList;
import java.util.Scanner;

// Common methods between AI and Player class will be in this super class

abstract class Player {
	  private int Money; //Tracks money
	  protected ArrayList<Card> hole = new ArrayList<Card>(); //player's 2 card hand
	  //switch to hand object
	  protected Hand hand; //player's 5 card hand
	  protected String name = "";
	  private int totBet = 0;
	  //method in main that sets the blinds
	  //make the current player bet into a list
	  
	  public void setName(String newName){
		  name = newName;
	  }

	  public String getName() {
		  return name;
	  }
	  
	/**
	 * Given the community and a player's hole cards, this method will find 
	 * their highest ranking hand. The rank of each possible hand is determined
	 * and compared to one and another to find the highest. If two hands share
	 * the highest rank they are passed through to the dispute methods to find
	 * the highest.
	 * 
	 * @param comm the community cards
	 * @return none
	 */
	public void setHand(ArrayList<Card> comm) {
		int highestRank = -1, result;
		
		ArrayList<Card> allCards = new ArrayList<Card>(); 
		Hand highestHand = new Hand(); 
		allCards.addAll(comm); //The hole and community cards are combined to a single ArrayList
		allCards.addAll(hole);
		
		if (allCards.size() == 5) {
			for (Card card : allCards)
				hand.addCard(card);
		}
		else {
			ArrayList<Hand> combs = new ArrayList<Hand>();
			if (allCards.size() == 6)
				combs = combSix(allCards); //All six combinations of the cards are found for a six card set
			else if (allCards.size() == 7)
				combs = combSeven(allCards); //All 21 combinations of the cards are found for a seven card set
			
			for (Hand potenHand : combs) {
				if (potenHand.getRank() > highestRank) { //A potential hand becomes the new highest if its rank surpasses the previous highest
					highestRank = potenHand.getRank();
					highestHand = potenHand;
				}
				
				else if (potenHand.getRank() == highestRank) { //If its rank is equal the two are disputed, the new only succeeding the old if it is greater in value
					result = potenHand.compareHand(highestHand);
					if (result == 1) {
						highestRank = potenHand.getRank();
						highestHand = potenHand;
					}
				}
			}
		}
		hand = highestHand;
	}
	  
	  //gets the player's hand
	  public Hand getHand() {
	    return new Hand(hand);
	  }
	  
	  public void setHole(Card c) {
		  //adds the cards to the hand list
		  hole.add(c);
	  }
	  
	  public ArrayList<Card> getHole() {
		  return hole;
	  }

	  public void emptyHole(){
		  hole.clear();
	  }

	  public void emptyHand(){
		  hand.clear();
	  }

	  public int getBet() {
		  return totBet;
	  }
	  
	  public void check(String choice) {
		  //If the input is c, nothing happens
		  if (choice.equalsIgnoreCase("C")) {
			  System.out.println("Player Checked.");
		  }
	  }
	  
	  public void fold(String choice) {
		  //if the input is f, the list is emptied
		  if (choice.equalsIgnoreCase("F")) {
			  emptyHand(); //Clears hand
			  emptyHole(); //Clears hole
			  System.out.println("Player folded.");
		  }
	  }
	  
	  public void BetRaise(String choice, int newBet) {
		  if (choice.equalsIgnoreCase("B")) {
			  //checks to see if the bet is less than the
			  //money in the player's balance
			  if (newBet <= Money) {
				  Money = Money - newBet;
				  totBet += newBet;
				  PotControl.POT += newBet;
				  System.out.println("Player bet $" + newBet + ".");
			  }
		  } else if (choice.equalsIgnoreCase("R")) {
			  //Raise action
			  if (newBet >= 2*totBet && newBet <= Money) {
				Money = Money - newBet;
			  	System.out.println("Player raised $" + newBet + ".");
			  	PotControl.POT += newBet;
			  }
		  }
	  }
	  
	  public void call(String choice, int currentBet) {
		  int toCall = highBet - totBet; //highBet must be tracked
		  //need to determine how to compare each player's current Bet to generate a toCall
		  if (choice.equalsIgnoreCase("L")) {
			  int bet = toCall - currentBet;
			  Money -= bet;
			  PotControl.POT += bet;
			  System.out.println("Player called.");
		  }
	  }

	  public void allIn(String choice) {
		  if (choice.equalsIgnoreCase("A")) {
			  totBet += Money;
			  Money = 0;
			  PotControl.POT += totBet;
			  System.out.println("Player went all-in!");
		  }
	  }

	  public String invalidChoice(String choice){
		String decision = choice;
		while(!decision.equalsIgnoreCase("A") && !decision.equalsIgnoreCase("L") && !decision.equalsIgnoreCase("C") && !decision.equalsIgnoreCase("F") && !decision.equalsIgnoreCase("B") && !decision.equalsIgnoreCase("R")){
			System.out.printf("What would you like to do next (C for Check, F for Fold): ");
			Scanner dcInput = new Scanner(System.in);
			decision = dcInput.next();
		}

		return decision;
	}
	  
	  public void getDecision(String input) {
		 //HUH??? 
	  }
	
	/**
	 * All six unique combinations of five card hands from a six card set are
	 * found by the below algorithm. One blank space is cycled through all the
	 * indices of the passed Card ArrayList, writing those Cards not occupied
	 * by the blank space to a new Hand.
	 * 
	 * @param allCards the combined community and a given player's hole cards
	 * @return An ArrayList of all five card hand combinations
	 */
	private ArrayList<Hand> combSix(ArrayList<Card> allCards) {
		ArrayList<Hand> combs = new ArrayList<Hand>(); //A six Hand ArrayList is created to store all possible combinations
		for (int i = 0; i < 6; i++)
			combs.add(new Hand());
		
		for (int blank = 0; blank < 6; blank++) { //The blank space is moved through each index, also doubles as the index in the Hand ArrayList to write to
			for (int index = 0; index < 6; index++) { //Each Card index in allCards is ran through
				if (index != blank) //If the index of the card doesn't fall on the blank it is written to the current combination index
					combs.get(blank).addCard(allCards.get(index));
			}
		}
		return combs;
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
	private ArrayList<Hand> combSeven(ArrayList<Card> allCards) {
		ArrayList<Hand> combs = new ArrayList<Hand>(); //A 21 Hand ArrayList is created to store all possible combinations
		for (int i = 0; i < 21; i++)
			combs.add(new Hand());
		
		int writeIndex = 0; //The index of the 21 ArrayList ArrayList is cycled for each blank2 change
		for (int blank1 = 0; blank1 < 6; blank1++) { //The first blank advanced after the second has gone through each space following it
			for (int blank2 = blank1 + 1; blank2 < 7; blank2++) { //The second blank cycles through each index following the first blank
				for (int index = 0; index < 7; index++) { //Each Card index in allCards is ran through
					if ((index != blank1) && (index != blank2)) //If the index of the card doesn't fall on a blank it is written to the current combination index
						combs.get(writeIndex).addCard(allCards.get(index));
				}
				writeIndex++;
			}
		}	
		return combs;
	}
	  
}
