import java.util.ArrayList;
import java.util.Scanner;

// Common methods between AI and Player class will be in this super class

abstract class Player {
	  private int Money; //Tracks money
	  protected ArrayList<Card> hole = new ArrayList<Card>(); //player's 2 card hand
	  //switch to hand object
	  protected Hand hand = new Hand(); //player's 5 card hand
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
	  
	  //sets the player's hand
	  public void setHand(ArrayList<Card> newHand) {
		  //passes an arrayList newHand and adds it to hand.
		  for (Card card : newHand)
			  hand.add(card);
	  }
	  
	  //gets the player's hand
	  public ArrayList<Card> getHand() {
	    return hand;
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
}
