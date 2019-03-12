
// game.raise?
// game.call?
// game.processTurn?
// game.getBet?
/*
 * how to process folds?? *Possibly have a call in gui for emptyhole when fold
 * button clicked? or game.fold maybe?
 * possibly turn button clicks into strings for human.getDecision?
 */
// is game doing the game reset/round reset?
// is game implementing showdown?
/*
 * - gui is now our main? since the main loop is played in there? or is game
 * still? Update: gui main loop removed, even more confused
 * - Possibly add finishUserTurn in GUI.runTurn?
 * - Possibly add updatePlayerInfo in GUI.finishUserTurn?
 * - does game still need newGame/setupRound and is game calling it or is GUI calling it?
 * - in GUI.runGame, possibly have game.newGame call?
 * - in GUI.runPlayRound, possibly have game.setupRound call and possibly have a check in gui to see if all users have gone?
 * i.e game.isRoundrunning()/game.isGameRunning?
 * as well as if(players.size() == playerCount)??
 * - what does game.processturn do?
 * - do we need getDecision now? with buttons we can just have a method in game for each button that calls the method in player
 * ^^ this means human, and the strings for decisions isnt really needed either.
 * - is game setting up money and blinds per round?
 * - how are we implementing more than one player and how are we implementing AI??
 * - does player need to be an abstract class? would make more sense if we remove human.java for it to work.
 * - where am i calling allin, fold, and check? am i calling them?
 * - if we have more than one player, we need a screen before table that asks for their names
 * - fix AI to where its not a for loop in constructor with no AI amt in constructor? 
 * - are we implementing the full game or just the GUI? We can just deal with money and AI later and focus on check, fold and two players like before for now
 * - get rid of or fix player.emptyHand???
 * 
 * Thought:
 * - For demo 2 we should do the same as demo 1 where we just focus on fold and check. add ai this time while having 1 other player, which is us.
 */

/**
 * Main class for the game as the game is controlled here
 * Origin of Texas Holdem
 * 
 * @author Brayden Schmaltz - Campbell
 * @version 03/11/19
 */

import java.util.ArrayList;

public class Game {

    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<Player> roundPlayers = new ArrayList<Player>();
    private static ArrayList<Card> middleCards = new ArrayList<Card>();
    private static Deck cardDeck = new Deck();
    private int playerCount = 0;

    public boolean isGameRunning() {
        checkMoney();
        if (players.size() > 1) {
            return true;
        }

        else {
            return false;
        }
    }

    public boolean isRoundRunning() {
        checkHole();
        if (roundPlayers.size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * public void raise(int playerRaise) { if (playerRaise ==
     * players.get(playerCount).getStack()) {
     * players.get(playerCount).getDecision("A"); } else {
     * players.get(playerCount).getDecision("R", playerRaise); } }
     * 
     * public void call() { players.get(playerCount).getDecision("C"); }
     * 
     * public Player processTurn() { players.get(playerCount).getDecisionAI();
     * return players.get(playerCount); }
     */

    public Player getCurrentPlayer() {
        return players.get(playerCount);
    }

    public void incrementPlayer() {
        playerCount += 1;
    }

    // Change to setupGame and setupRound here and in GUI??
    public static ArrayList<Player> generatePlayers(int numOfPlayers) {
        for (int n = 0; n < numOfPlayers; n++) {
            if (n == 0)
                players.add(new Human("You"));
            else
                players.add(new Human("COM" + n)); // AI??
        }

        return players;
    }

    /*
     * public static ArrayList<Player> generateHole() { for (int i = 0; i < 2; i++)
     * { for (Player player : players) player.setHole(cardDeck.dealSingle()); }
     * 
     * return players; }
     */

    public static void generateMidCards() {
        middleCards = cardDeck.dealCard(middleCards);
    }

    private void checkMoney() {
        for (Player player : players) {
            if (player.getStack() <= 0) {
                players.remove(player);
            }
        }
    }

    private void checkHole() {
        for (Player player : players) {
            if (player.hole.size() == 0) {
                players.remove(player);
            }
        }
    }
}