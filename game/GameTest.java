package game;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

import cards.Card;
import cards.Deck;
import players.AI;
import players.Human;
import players.Player;

public class GameTest {
	// testing game constructor
	@Test
	public void test_GameConstructor() {
		Game game = new Game();

		assertEquals(new Integer(0), game.getRound());
		assertEquals(new Integer(0), game.getPlayerCount());
		assertEquals(new Integer(0), game.getPot());
		assertEquals(new Integer(0), game.getHighestBet());
		assertEquals(new Integer(0), game.getSmallBlind());
		assertEquals(new Boolean(false), game.isGameOver());
	}

	// testing generatePlayers
	@Test
	public void test_generatePlayers() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);

		assertEquals(new Integer(3), game.getPlayerList());
		for (Player player : players) {
			assertEquals(new Integer(2), player.getHole().size());
			assertEquals(new Integer(1000), player.getStack());
		}
		assertEquals(new Integer(25), game.getSmallBlind());
	}

	// testing setupRound
	@Test
	public void test_setupRound() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);
		game.setupRound();

		assertEquals(new Integer(3), game.getPlayerList());
		for (Player player : players) {
			assertEquals(new Integer(2), player.getHole().size());
			assertEquals(new Integer(1000), player.getStack());
		}
		assertEquals(new Integer(25), game.getSmallBlind());
		assertEquals(new Integer(0), game.getRound());
		assertEquals(new Integer(0), game.getPlayerCount());
		assertEquals(new Integer(0), game.getPot());
		assertEquals(new Integer(0), game.getHighestBet());
		assertEquals(new Integer(5), game.getComm().size());
	}

	// testing incrementPlayer
	@Test
	public void test_incrementPlayer() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);
		game.setupRound();
		game.incrementPlayer();

		assertEquals(new Integer(0), game.getHighestBet());
		assertEquals(new Integer(1), game.getPlayerCount());
	}
}
