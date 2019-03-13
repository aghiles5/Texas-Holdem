
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
    private Player curPlayer = new Player();
    private Deck cardDeck = new Deck();
    private int roundNum;
    private int playerCount;
    private static final String[] ROUNDKEY = { "Blind Round", "Flop", "Turnover", "Turnover", "Showdown" };

    public Game() {
        roundNum = 0;
        playerCount = 0;
    }

    public ArrayList<Player> generatePlayers(int numOfPlayers) {
        Random playerPos = new Random();
        int position = playerPos.nextInt(numOfPlayers);
        AI.addCPUName();
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

        return players;
    }

    public void setupRound() {
        roundPlayers.clear();
        for (Player player : players) {
            player.emptyHand();
            player.emptyHole();
        }
        for (int i = 0; i < 2; i++) {
            for (Player player : players)
                player.setHole(cardDeck.dealSingle());
        }
        for (Player player : players) {
            roundPlayers.add(player);
        }
        middleCards.clear();
        cardDeck = null;
        cardDeck = new Deck();
        for (int i = 0; i < 3; i++) {
            middleCards = cardDeck.dealCard(middleCards);
        }
    }

    public void incrementPlayer() {
        if (playerCount == roundPlayers.size() - 1) {
            playerCount = 0;
        } else {
            playerCount += 1;
        }
    }

    public void incrementRound() {
        ArrayList<Card> roundComm = new ArrayList<Card>();
        playerCount = 0;
        // checkAction();
        for (Card roundCard : middleCards) {
            roundComm.add(roundCard);
        }
        roundNum++;
        if (roundNum == 0) {
            roundComm.remove(middleCards.size() - 1);
            roundComm.remove(middleCards.size() - 2);
        } else if (roundNum == 1) {
            roundComm.remove(middleCards.size() - 1);
        }

        for (Player player : roundPlayers) {
            player.setHand(roundComm);
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

    public Player getLastPlayer() {
        if (curPlayer.getAction() == "Folded") {
            return curPlayer;
        }
    }

    public Player processTurn() {
        // Player curPlayer = roundPlayers.get(playerCount);
        roundPlayers.get(playerCount).getDecision();
        if (roundPlayers.get(playerCount).getAction() == "Folded") {
            curPlayer = roundPlayers.get(playerCount);
            roundPlayers.remove(playerCount);
            playerCount -= 1;
        }
        return curPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return roundPlayers;
    }

    public ArrayList<Card> getComm() {
        return middleCards;
    }

    public boolean isBetRoundRunning() {
        int actionCounter = 0;
        for (Player player : roundPlayers) {
            if (player.getAction() == "Checked") {
                actionCounter += 1;
            } else if (player.getAction() == "Folded") {
                actionCounter += 1;
            }
        }

        if (actionCounter == roundPlayers.size()) {
            incrementRound();
            return false;
        }

        else if (roundPlayers.size() > 1) {
            return true;
        }

        else {
            incrementRound();
            return false;
        }
    }

    /*
     * public void checkAction() { ArrayList<Player> tempRPlayers = new
     * ArrayList<Player>(); tempRPlayers.addAll(roundPlayers); for (Player player :
     * tempRPlayers) { if (player.getAction() == "Folded") {
     * roundPlayers.remove(player); } } }
     */

    public ArrayList<Player> showdown() {
        ArrayList<Player> encapPlayers = new ArrayList<Player>(), winners = new ArrayList<Player>();
        encapPlayers.addAll(roundPlayers); // The player ArrayList is encapsulated by the proxy encapPlayers ArrayList

        Player highestPlayer = encapPlayers.get(0);
        int highPlayerIndex = -1;
        for (int index = 1; index < encapPlayers.size(); index++) {
            if (encapPlayers.get(index).getHand().compareHand(highestPlayer.getHand()) == 1)
                highestPlayer = encapPlayers.get(index);
            highPlayerIndex = index;
        }
        winners.add(highestPlayer);
        encapPlayers.remove(highPlayerIndex);

        for (Player player : encapPlayers) {
            if (player.getHand().compareHand(highestPlayer.getHand()) == 0)
                winners.add(player);
        }

        return winners;
    }

    public void fold() {
        roundPlayers.get(playerCount).fold("F");
        roundPlayers.remove(playerCount);
        playerCount -= 1;
    }

    public void tempCheck() {
        roundPlayers.get(playerCount).check("C");
    }
}