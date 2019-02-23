import java.util.ArrayList;


/**
 * This class provides an environment for testing methods from the Showdown
 * class.
 *
 * FOR TESTING PURPOSES ONLY.
 *
 * @author Adam Hiles
 * @version 02/22/19
 */
public class ShowdownTest {

	public static void main(String args[]) {
		twoPlayerDemo();
	}
	
	public static void twoPlayerDemo() {
		Deck testDeck = new Deck();
		testDeck.shuffle();
		ArrayList<Card> comm = new ArrayList<Card>();
		ArrayList<Player> players = new ArrayList<Player>();
		
		players.add(new Human("Player1"));
		players.add(new Human("Player2"));
		players.add(new Human("Player3"));
		players.add(new Human("Player4"));
		players.add(new Human("Player5"));
		players.add(new Human("Player6"));
		players.add(new Human("Player7"));
		players.add(new Human("Player8"));
		players.add(new Human("Player9"));
		players.add(new Human("Player10"));
		
		for (int i = 0; i < 2; i++) {
			for (Player player : players)
				player.setHole(testDeck.dealSingle());
		}
		
		comm = testDeck.simComm();
		System.out.println("Community Cards:\n");
		for (Card card : comm)
			System.out.println(card.toString());
		for (Player player : players) {
			System.out.println("\n" + player.getName() + "'s Cards:\n");
			for (Card card : player.getHole())
				System.out.println(card.toString());
		}
		
		Showdown.showdown(players, comm);
	}
}