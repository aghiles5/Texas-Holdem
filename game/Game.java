package game;

/**
 * Main class for the game as the game is controlled here
 * Origin of Texas Holdem
 * 
 * @author Brayden Schmaltz - Campbell
 * @version 03/11/19
 */

import java.util.ArrayList;
import java.util.Random;

import cards.Card;
import cards.Deck;
import players.AI;
import players.Human;
import players.Player;

public class Game {

    private ArrayList<Player> players = new ArrayList<Player>();
    protected ArrayList<Player> roundPlayers = new ArrayList<Player>();
    private ArrayList<Card> middleCards = new ArrayList<Card>();
    private Deck cardDeck = new Deck();
    private Player lastPlayer;
    private int roundNum;
    private int playerCount;
    private static final String[] ROUNDKEY = { "Blind Round", "Flop", "Turnover", "Turnover", "Showdown" };
    private int highestBet;
    private double smallBlind;
    private int highBetHolder;
    private int pot;
    private boolean gameOver;
    private boolean userFolded;
    private boolean sBlindDone;
    private boolean bBlindDone;
    private boolean userWon;

    /*
     * Constructor to ensure that roundNum and playerCount are reset to 0 when a
     * game object is created
     */
    public Game() {
        roundNum = 0;
        playerCount = 0;
        pot = 0;
        highestBet = 0;
        highBetHolder = 0;
        smallBlind = 0;
        gameOver = false;
        sBlindDone = false;
        bBlindDone = false;
        userWon = false;
    }

    /**
     * Generates the old players loaded from a file in the SaveIO class
     * 
     * @param name, stack name and stack contain the arrayList passed from SaveIO to
     *              Game
     * @return arraylist of the generated players
     */
    public Game(ArrayList<String> name, ArrayList<Integer> stack, int smlBlind) {
        roundNum = 0;
        playerCount = 0;
        pot = 0;
        highestBet = 0;
        highBetHolder = 0;
        smallBlind = smlBlind;
        gameOver = false;
        sBlindDone = false;
        bBlindDone = false;
        loadPlayers(name, stack);
    }

    /**
     * Generates the old players loaded from a file in the SaveIO class
     * 
     * @param name, stack name and stack contain the arrayList passed from SaveIO to
     *              Game
     * @return arraylist of the generated players
     */
    public void loadPlayers(ArrayList<String> name, ArrayList<Integer> stack) {
        ArrayList<String> names = new ArrayList<String>(name);
        ArrayList<Integer> stacks = new ArrayList<Integer>(stack);
        int position = 0;
        AI.setBetIntervals((int) (smallBlind / 0.025));
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

    }

    /**
     * Generates the players needed to be recorded throughout the game, in gui and
     * TBGame
     * 
     * @param number of players and stackamt decided by GUI or TBGame
     * @return arraylist of the generated players
     */
    public ArrayList<Player> generatePlayers(int numOfPlayers, int stackAmt) {
        Random playerPos = new Random();
        int position = playerPos.nextInt(numOfPlayers);
        AI.clearCPUName();
        AI.addCPUName();
        AI.setBetIntervals(stackAmt);
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

        for (Player player : players) {
            player.stack = stackAmt;
        }

        smallBlind = (int) (stackAmt * 0.025);

        return players;
    }

    /**
     * Sets up the next round for the game, used for GUI and TBGame calling and game
     * setup
     */
    public void setupRound() {
        roundPlayers.clear();
        roundNum = 0;
        playerCount = 0;
        pot = 0;
        highestBet = 0;
        sBlindDone = false;
        bBlindDone = false;
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
            if (player.getStack() > 0) {
                roundPlayers.add(player);
            }
        }

        for (Player player : roundPlayers) {
            player.setMinBet((int) (smallBlind));
            player.setHighBet(highestBet);
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

        if (highestBet <= highBetHolder) {
            highestBet = highBetHolder;
        }

        for (Player player : roundPlayers) {
            player.setHighBet(highestBet);
        }
    }

    /**
     * Increases the round by one in order to reset all of the players actions and
     * continue the game
     */
    public void incrementRound() {
        ArrayList<Card> roundComm = new ArrayList<Card>();
        playerCount = 0;
        highestBet = 0;
        highBetHolder = 0;

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
            pot += player.getBet();
            player.setBet(0);
            player.setAction(" ");
            player.setHand(roundComm);
        }
    }

    /**
     * Processes the AI's turn/decision
     * 
     * @return a Player object corresponding to the evaluated relation
     */
    public Player processTurn() {
        Player curPlayer = roundPlayers.get(playerCount);

        if (roundNum == 0 && playerCount == 0 && sBlindDone == false) {
            if (roundPlayers.get(playerCount).stack < smallBlind) {
                roundPlayers.get(playerCount).allIn("A");
                highBetHolder = (int) smallBlind;
            } else {
                bet((int) (smallBlind));
                highBetHolder = roundPlayers.get(playerCount).getBet();
            }
            
            sBlindDone = true;
        } else if (roundNum == 0 && playerCount == 1 && bBlindDone == false) {
            if (roundPlayers.get(playerCount).stack < (smallBlind * 2)) {
                roundPlayers.get(playerCount).allIn("A");
                highBetHolder = (int)smallBlind*2;
            } else {
                bet((int) (smallBlind));
                highBetHolder = roundPlayers.get(playerCount).getBet();
            }
            bBlindDone = true;
        } else if (playerCount == 0 && roundNum != 0 && highestBet == 0) {
            roundPlayers.get(playerCount).getDecision2();
        } else if (lastPlayer.getAction() == "Raised" || lastPlayer.getAction() == "Bet"
                || lastPlayer.getAction() == "All In" || lastPlayer.getAction() == "Called") {
            roundPlayers.get(playerCount).getDecision();
        } else if (highestBet == 0) {
            if (lastPlayer.getAction() == "Checked" || lastPlayer.getAction() == "Folded") {
                roundPlayers.get(playerCount).getDecision2();
            }
        } else if (lastPlayer.getAction() == "Folded" && highestBet != 0) {
            roundPlayers.get(playerCount).getDecision();
        } else {
            roundPlayers.get(playerCount).getDecision();
        }

        if (roundPlayers.get(playerCount).getBet() > highBetHolder) {
            highBetHolder = roundPlayers.get(playerCount).getBet();
        }

        setLastPlayer(roundPlayers.get(playerCount));
        if (roundPlayers.get(playerCount).getAction() == "Folded") {
            curPlayer = roundPlayers.get(playerCount);
            pot += curPlayer.getBet();
            curPlayer.setBet(0);
            roundPlayers.remove(playerCount);
            playerCount -= 1;
        }

        return curPlayer;
    }

    /**
     * Calls the player's action when the player chooses to fold and removes him
     * from the round
     */
    public void fold() {
        roundPlayers.get(playerCount).fold("F");
        setLastPlayer(roundPlayers.get(playerCount));
        pot += roundPlayers.get(playerCount).getBet();
        roundPlayers.get(playerCount).setBet(0);
        roundPlayers.remove(playerCount);
        playerCount -= 1;
        userFolded = true;
    }

    /**
     * Calls the player's action when the player chooses to Call or Check
     */
    public void call() {
        if (roundPlayers.get(playerCount).getBet() == highestBet) {
            roundPlayers.get(playerCount).check("C");
        }

        else if (roundPlayers.get(playerCount).stack <= (highestBet - roundPlayers.get(playerCount).getBet())) {
            roundPlayers.get(playerCount).allIn("A");
        }

        else {
            roundPlayers.get(playerCount).call("L");
        }

        setLastPlayer(roundPlayers.get(playerCount));
    }

    /**
     * Calls the player's action when the player chooses to Bet some amount of their
     * stack
     */
    public void bet(int betAmt) {
        if (roundPlayers.get(playerCount).stack <= (betAmt + highestBet)) {
            roundPlayers.get(playerCount).allIn("A");
            betAmt = roundPlayers.get(playerCount).getBet();
        }

        else {
            if (highestBet == 0 || (roundNum == 0 && betAmt > highestBet * 2)) {
                roundPlayers.get(playerCount).BetRaise("B", betAmt);
            } else {
                roundPlayers.get(playerCount).BetRaise("R", betAmt);
            }
        }

        highBetHolder += betAmt;
        setLastPlayer(roundPlayers.get(playerCount));
    }

    /**
     * Checks to see if the bet round is still running, used for GUI and TBGame
     * 
     * @return a boolean condition corresponding to the evaluated relation
     */
    public boolean isBetRoundRunning() {
        if (roundNum == 0) {
            if (highestBet == smallBlind * 2) {
                if ((playerCount == 2) || (roundPlayers.size() == 2 && playerCount == 0)) {
                    int BBCounter = 0;
                    for (Player player : roundPlayers) {
                        if (player.getBet() == smallBlind * 2) {
                            BBCounter++;
                        }
                    }

                    if (BBCounter == roundPlayers.size()) {
                        incrementRound();
                        return false;
                    }
                }
            } else {
                int betCounter = 0;
                for (Player player : roundPlayers) {
                    if ((player.getBet() == highestBet) || (player.getStack() == 0)) {
                        betCounter++;
                    }
                }

                if (betCounter == roundPlayers.size()) {
                    incrementRound();
                    return false;
                }
            }
        } else if (highestBet == 0) {
            int checkCount = 0;
            for (Player player : roundPlayers) {
                if ((player.getAction() == "Checked") || (player.getStack() == 0)) {
                    checkCount++;
                }
            }

            if (checkCount == roundPlayers.size()) {
                incrementRound();
                return false;
            }
        } else {
            int betCounter = 0;
            for (Player player : roundPlayers) {
                if ((player.getBet() == highestBet) || (player.getStack() == 0)) {
                    betCounter++;
                }
            }

            if (betCounter == roundPlayers.size()) {
                incrementRound();
                return false;
            }
        }
        return true;
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

        int divisor = winners.size();
        for (Player player : winners) {
            player.stack += (int) (pot / divisor);
            pot -= (int) (pot / divisor);
            if (pot % 2 == 1) {
                if (player == winners.get(0)) {
                    player.stack++;
                }
            }
            divisor--;
        }

        ArrayList<Player> gameOverList = new ArrayList<Player>();
        gameOverList.addAll(players);

        for (Player player : players) {
            if (player instanceof Human && player.stack == 0) {
                gameOver = true;
                break;
            }

            if (player.stack <= 0) {
                gameOverList.remove(player);
            }
        }

        if (gameOverList.size() == 1) {
            gameOver = true;
            userWon = true;
        }

        userFolded = false;
        blindPosition();

        return winners;
    }

    /**
     * Rotates the blinds for the players so that the person doing the blinds is
     * different
     */
    private void blindPosition() {
        ArrayList<Player> tempPList = new ArrayList<Player>();
        for (Player player : players) {
            if (player != players.get(0)) {
                tempPList.add(player);
            }
        }

        tempPList.add(players.get(0));

        players.clear();
        players.addAll(tempPList);
    }

    /**
     * Gets the master list of the created players
     * 
     * @Return ArrayList of type Player
     */
    public ArrayList<Player> getPlayerList() {
        return players;
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
     * Gets the player that made the most recent decision which is currently for GUI
     * and TBGame
     * 
     * @return a Player object corresponding to the evaluated relation
     */
    public Player getLastPlayer() {
        return lastPlayer;
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

    /**
     * Gets the smallblind for GUI and TBGame
     * 
     * @return a integer corresponding to the evaluated relation
     */
    public int getSmallBlind() {
        return (int) (smallBlind);
    }

    /**
     * Gets the most recent highest bet for GUI and TBGame
     * 
     * @return a int corresponding to the evaluated relation
     */
    public int getHighestBet() {
        return highestBet;
    }

    /**
     * Gets the round's pot for GUI and TBGame
     * 
     * @return a int corresponding to the evaluated relation
     */
    public int getPot() {
        return pot;
    }

    /**
     * Gets the current count of player in the player list for GUI and TBGame
     * 
     * @return a int corresponding to the evaluated relation
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Gets the check to see if the game is over or not, used for GUI and TBGame
     * 
     * @return a boolean condition corresponding to the evaluated relation
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the check to see if the user has folded or not, used for GUI and TBGame
     * 
     * @return a boolean condition corresponding to the evaluated relation
     */
    public boolean isUserFolded() {
        return userFolded;
    }

    /**
     * Gets the check to see if the user has won the game or not.
     * 
     * @return a boolean condition corresponding to the evaluated relation
     */
    public boolean hasUserWon() {
        return userWon;
    }

    /**
     * Sets an object of type player to the last player that the game was just
     * getting input from
     */
    public void setLastPlayer(Player theLastPlayer) {
        lastPlayer = theLastPlayer;
    }
}