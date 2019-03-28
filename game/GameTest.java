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

		assertEquals(0, game.getRound());
		assertEquals(0, game.getPlayerCount());
		assertEquals(0, game.getPot());
		assertEquals(0, game.getHighestBet());
		assertEquals(0, game.getSmallBlind());
		assertEquals(new Boolean(false), game.isGameOver());
	}

	// testing generatePlayers
	@Test
	public void test_generatePlayers() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);

		assertEquals(3, game.getPlayerList().size());
		for (Player player : players) {
			assertEquals(2, player.getHole().size());
			assertEquals(1000, player.getStack());
		}
		assertEquals(25, game.getSmallBlind());
	}

	// testing setupRound
	@Test
	public void test_setupRound() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);
		game.setupRound();

		assertEquals(3, game.getPlayerList().size());
		for (Player player : players) {
			assertEquals(2, player.getHole().size());
			assertEquals(1000, player.getStack());
		}
		assertEquals(25, game.getSmallBlind());
		assertEquals(0, game.getRound());
		assertEquals(0, game.getPlayerCount());
		assertEquals(0, game.getPot());
		assertEquals(0, game.getHighestBet());
		assertEquals(5, game.getComm().size());
	}

	// testing incrementPlayer
	@Test
	public void test_incrementPlayer() {
		Game game = new Game();
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.generatePlayers(3, 1000);
		game.setupRound();
		game.incrementPlayer();

		assertEquals(0, game.getHighestBet());
		assertEquals(1, game.getPlayerCount());
	}
}
