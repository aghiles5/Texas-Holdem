import java.util.ArrayList;

public class Human extends Player{

  Human() {
	 //Use deck class to set hand
  }
  
  /// Accessory for player's card
  public ArrayList<Card> getCards() {
    return hand;
    
  }

}
