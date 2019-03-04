import java.util.ArrayList;

public class GUITest {
	public static Deck testDeck = new Deck();
	
	public static void main(String args[]) {
	}
	
	public static ArrayList<Player> generatePlayers(int numOfPlayers) {
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (int n = 0; n < numOfPlayers; n++) {
			if (n ==0)
				players.add(new Human("You"));
			else
				players.add(new Human("COM " + n));
		}
		
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
