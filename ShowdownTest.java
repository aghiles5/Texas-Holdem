import java.util.ArrayList;


/**
 * This class provides an environment for testing methods from the Showdown
 * class.
 *
 * FOR TESTING PURPOSES ONLY.
 *
 * @author Adam Hiles
 * @version 02/18/19
 */
public class ShowdownTest {

	public static void main(String args[]) {
		twoPlayerDemo();
	}
	
	public static void twoPlayerDemo() {
		Deck testDeck = new Deck();
		testDeck.shuffle();
		ArrayList<Card> hole1 = new ArrayList<Card>(), hole2 = new ArrayList<Card>(), comm = new ArrayList<Card>();
		for (int i = 0; i < 2; i++) {
			hole1.add(testDeck.dealSingle());
			hole2.add(testDeck.dealSingle());
		}
		comm = testDeck.simComm();
		System.out.println("Community Cards:\n");
		for (Card card : comm)
			System.out.println(card.toString());
		System.out.println("\nPlayer One's Cards:\n");
		for (Card card : hole1)
			System.out.println(card.toString());
		System.out.println("\nPlayer Two's Cards:\n");
		for (Card card : hole2)
			System.out.println(card.toString());
		System.out.println("");
		Showdown.showdown(hole1, hole2, comm);
	}
}