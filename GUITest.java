import java.util.ArrayList;

public class GUITest {
	public static Deck testDeck = new Deck();
	
	public static void main(String args[]) {
	}
	
	public static ArrayList<Player> generatePlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		
		players.add(new Human("User"));
		players.add(new Human("COM 1"));
		players.add(new Human("COM 2"));
		players.add(new Human("COM 3"));
		players.add(new Human("COM 4"));
		players.add(new Human("COM 5"));
		players.add(new Human("COM 6"));
		players.add(new Human("COM 7"));
		players.add(new Human("COM 8"));
		players.add(new Human("COM 9"));
		
		for (int i = 0; i < 2; i++) {
			for (Player player : players)
				player.setHole(testDeck.dealSingle());
		}
		
		return players;
	}
	
	public static ArrayList<Card> generateComm() {
		ArrayList<Card> comm = new ArrayList<Card>();
		
		comm = testDeck.dealCard(comm);
		comm = testDeck.dealCard(comm);
		comm = testDeck.dealCard(comm);
		
		return comm;
	}
}
