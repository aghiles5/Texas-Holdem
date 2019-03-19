
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
    private Player lastPlayer;
    private int roundNum;
    private int playerCount;
    private static final String[] ROUNDKEY = { "Blind Round", "Flop", "Turnover", "Turnover", "Showdown" };

    /*
     * Constructor to ensure that roundNum and playerCount are reset to 0 when a
     * game object is created
     */
    public Game() {
        roundNum = 0;
        playerCount = 0;
    }
    
    public ArrayList<Player> getPlayerList() {
    	return players;
    }
    
    public ArrayList<Player> loadPlayers(ArrayList<String> name, ArrayList<Integer> stack) {
    	ArrayList<String> names = new ArrayList<String>(name); 
    	ArrayList<Integer> stacks = new ArrayList<Integer>(stack);
    	int position = 0;
    	for (int i = 0; i < name.size(); i++) {
    		if (names.get(i).equals("You")) {
    			position = i;
    		}
    	}
    	for (int n = 0; n < names.size(); n++) {
            if (n == position)
                players.add(new Human("You", stacks.get(n)));
            else
                players.add(new AI(names.get(n), stacks.get(n)));
        }
    	for (int i = 0; i < 2; i++) {
            for (Player player : players)
                player.setHole(cardDeck.dealSingle());
        }
    	return players;
    }

    /**
     * Generates the players needed to be recorded throughout the game and in gui
     * 
     * @param number of players decided by gui
     * @return arraylist of the generated players
     */
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

    /**
     * Sets up the next round for the game, used for GUI calling and game setup
     */
    public void setupRound() {
        roundPlayers.clear();
        roundNum = 0;
        for (Player player : players) {
            player.emptyHand();
            player.emptyHole();
        }
        cardDeck = null;
        cardDeck = new Deck();
        for (int i = 0; i < 2; i++) {
            for (Player player : players)
                player.setHole(cardDeck.dealSingle());
        }
        for (Player player : players) {
            roundPlayers.add(player);
        }
        middleCards.clear();
        for (int i = 0; i < 3; i++) {
            middleCards = cardDeck.dealCard(middleCards);
        }
    }

    /**
     * Increases the player count by one so that the next player and his decisions
     * can be recorded
     */
    public void incrementPlayer() {
        if (playerCount == roundPlayers.size() - 1) {
            playerCount = 0;
        } else {
            playerCount += 1;
        }
    }

    /**
     * Increases the round by one in order to reset all of the players actions and
     * continue the game
     */
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

    /**
     * Gets the round number for GUI
     * 
     * @return a integer corresponding to the evaluated relation
     */
    public int getRound() {
        return roundNum;
    }

    /**
     * Gets the string for the current round number for GUI
     * 
     * @return a string corresponding to the evaluated relation
     */
    public String getRoundString() {
        return ROUNDKEY[roundNum];
    }

    /**
     * Gets the player that the round is currently on for GUI
     * 
     * @return a Player object corresponding to the evaluated relation
     */
    public Player getCurrentPlayer() {
        return roundPlayers.get(playerCount);
    }

    /**
     * Sets an object of type player to the last player that the game was just
     * getting input from
     */
    public void setLastPlayer(Player theLastPlayer) {
        lastPlayer = theLastPlayer;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public Player processTurn() {
        Player curPlayer = roundPlayers.get(playerCount);
        roundPlayers.get(playerCount).getDecision();
        setLastPlayer(roundPlayers.get(playerCount));
        if (roundPlayers.get(playerCount).getAction() == "Folded") {
            curPlayer = roundPlayers.get(playerCount);
            roundPlayers.remove(playerCount);
            playerCount -= 1;
        }
        return curPlayer;
    }

    /**
     * Gives GUI the players used in the round
     * 
     * @return an arraylist of type player corresponding to the evaluated relation
     */
    public ArrayList<Player> getPlayers() {
        return roundPlayers;
    }

    /**
     * Gives GUI the middle community cards used for the round
     * 
     * @return an arraylist of type card corresponding to the evaluated relation
     */
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

    /**
     * Figures out the winner by determining which player has the highest card rank
     * 
     * @return an arraylist of type player corresponding to the evaluated relation
     */
    public ArrayList<Player> showdown() {
        ArrayList<Player> encapPlayers = new ArrayList<Player>(), winners = new ArrayList<Player>();
        encapPlayers.addAll(roundPlayers); // The player ArrayList is encapsulated by the proxy encapPlayers ArrayList

        Player highestPlayer = encapPlayers.get(0);
        int highPlayerIndex = 0;
        for (int index = 1; index < encapPlayers.size(); index++) {
            if (encapPlayers.get(index).getHand().compareHand(highestPlayer.getHand()) == 1)
                highestPlayer = encapPlayers.get(index);
            highPlayerIndex = index;
        }
        winners.add(highestPlayer);
        encapPlayers.remove(highestPlayer);

        for (Player player : encapPlayers) {
            if (player.getHand().compareHand(highestPlayer.getHand()) == 0)
                winners.add(player);
        }

        return winners;
    }

    /**
     * Calls the player's action when the player chooses to fold and removes him
     * from the round
     */
    public void fold() {
        roundPlayers.get(playerCount).fold("F");
        setLastPlayer(roundPlayers.get(playerCount));
        roundPlayers.remove(playerCount);
        playerCount -= 1;
    }

    /**
     * A temporary method used for DEMO 2, calls the player's action when the player
     * chooses to check
     */
    public void tempCheck() {
        roundPlayers.get(playerCount).check("C");
        setLastPlayer(roundPlayers.get(playerCount));
    }
}