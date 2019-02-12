import java.util.ArrayList;


/**
 * This class provides an environment for testing methods from the Showdown
 * class.
 *
 * FOR TESTING PURPOSES ONLY.
 *
 * @author Adam Hiles
 * @version 02/11/19
 */
public class ShowdownTest {

	public static void main(String args[]) {
		Deck testDeck = new Deck();
		testDeck.printDeck();
		testDeck.shuffle();
		testDeck.printDeck();
	}
	
	public static void randomHandTest() {
		Deck testDeck = new Deck();
		testDeck.shuffle();
		
		ArrayList<Card> testHand = testDeck.dealFullHand();
		for (Card card : testHand) 
			System.out.println(card.toString());
		
		byte rankByte = Showdown.getHandRank(testHand);
		
		System.out.println("\nRank: " + Showdown.byteToString(rankByte));
	}
	
	public static void findDefinedRank() {
		byte targetRank = 5;
		System.out.println("First \"" + Showdown.byteToString(targetRank) + "\" Hand\n");
		int counter = 0;
		boolean run = true;
		while (run) {
			Deck testDeck = new Deck();
			testDeck.shuffle();
			for (int i = 0; i < 10; i++) {
				ArrayList<Card> testHand = testDeck.dealFullHand();
				byte rankByte = Showdown.getHandRank(testHand);
				counter++;
				if (rankByte == targetRank) { //Change to find hands of a certain rank
					for (Card card : testHand) 
						System.out.println(card.toString());
					System.out.println("\nNumber of Hands to Find: " + counter);
					run = false;
					break;
				}
			}
		}
	}
	
	public static void timeTest() {
		System.out.println("Time Test\n");
		long startTime = System.nanoTime();
		
		for (int o = 0; o < 100000; o++) {
			Deck testDeck = new Deck();
			testDeck.shuffle();
			for (int i = 0; i < 10; i++) {
				ArrayList<Card> testHand = testDeck.dealFullHand();
				Showdown.getHandRank(testHand);
				}
			}
		
		long endTime = System.nanoTime();
		double time = (endTime - startTime)/1000000.0;
		
		System.out.println("Time: " + time + " ms");
		System.out.println("Approx. Time per Rank Determination: " + time/1000 + " us");
	}
}
