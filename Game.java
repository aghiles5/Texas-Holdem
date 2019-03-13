
/**
 * Main class for the game as the game is controlled here
 * Origin of Texas Holdem
 * 
 * @author Brayden Schmaltz - Campbell
 * @version 03/11/19
 */

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> roundPlayers = new ArrayList<Player>();
    private ArrayList<Card> middleCards = new ArrayList<Card>();
    private Deck cardDeck = new Deck();
    private int roundNum;
    private int playerCount;
    private static final String[] ROUNDKEY = { "Blind Round", "Flop", "Turnover", "Showdown" };

    public Game() {
        roundNum = 0;
        playerCount = 0;
    }

    public ArrayList<Player> generatePlayers(int numOfPlayers) {
        Random playerPos = new Random();
        int position = playerPos.nextInt(numOfPlayers);
        for (int n = 0; n < numOfPlayers; n++) {
            if (n == position)
                players.add(new Human("You"));
            else
                players.add(new AI());
        }

        for (int i = 0; i < 2; i++) {
            for (Player player : players)
                player.setHole(cardDeck.dealSingle());
        }

        roundPlayers = players;

        return roundPlayers;
    }

    public void setupRound() {
        roundPlayers.clear();
        for (Player player : players) {
            player.emptyHand();
            player.emptyHole();
        }
        roundPlayers = players;
        middleCards.clear();
        cardDeck = null;
        cardDeck = new Deck();
        for (int i = 0; i < 3; i++) {
            middleCards = cardDeck.dealCard(middleCards);
        }
    }

    public void incrementPlayer() {
        if (playerCount == roundPlayers.size()) {
            playerCount = 0;
        } else {
            playerCount += 1;
        }
    }

    public int getRound() {
        return roundNum;
    }

    public String getRoundString() {
        return ROUNDKEY[roundNum];
    }

    public Player getCurrentPlayer() {
        return roundPlayers.get(playerCount);
    }

    public Player processTurn() {
        players.get(playerCount).getDecision();
        return players.get(playerCount);
    }

    public ArrayList<Player> getPlayers() {
        return roundPlayers;
    }

    public ArrayList<Card> getComm() {
        return middleCards;
    }

    public boolean isBetRoundRunning() {
        for (Player player : roundPlayers) {
            if (player.hole.size() == 0) {
                if (playerCount == roundPlayers.size()) {
                    playerCount -= 1;
                }
                roundPlayers.remove(player);
            }
        }

        if (roundPlayers.size() > 1) {
            return true;
        }

        else if (roundNum <= 3) {
            return true;
        }

        else {
            return false;
        }
    }

    public void fold() {
        players.get(playerCount).fold("F");
    }

    public void tempCheck() {
        players.get(playerCount).check("C");
    }
}