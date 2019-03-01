import java.util.ArrayList;

public class GUITest {
	public static void main(String args[]) {
		Deck testDeck = new Deck();
		testDeck.shuffle();
		ArrayList<Card> comm = new ArrayList<Card>();
		ArrayList<Player> players = new ArrayList<Player>();
		
		players.add(new Human("User"));
		players.add(new Human("COM 1"));
		players.add(new Human("COM 2"));
		players.add(new Human("COM 3"));
		players.add(new Human("COM 4"));
		players.add(new Human("COM 5"));
		
		for (int i = 0; i < 2; i++) {
			for (Player player : players)
				player.setHole(testDeck.dealSingle());
		}
		
		comm = testDeck.dealCard(comm);
		comm = testDeck.dealCard(comm);
		comm = testDeck.dealCard(comm);
		
		TableGUI.passArrayLists(players, comm);
		TableGUI.main(null);
	}
}
