import java.util.ArrayList;

public class Human extends Player{

  Human() {
	 //Use deck class to set hand
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

}
