
/**
 * The main class for the text based version of the game
 * Origin of Texas Holdem - Text Based
 * 
 * @author Brayden Schmaltz - Campbell
 * @version 03/27/19
 */

import java.util.ArrayList;
import java.util.Scanner;
import javax.smartcardio.Card;
import java.io.IOException;

import cards.Card;
import cards.Deck;
import cards.Hand;
import players.AI;
import players.Human;
import players.Player;

public static class TBGame {

    private static boolean gameOccur = true;
    private static int aNewGame = 0;
    private static ArrayList<Card> comm = new ArrayList<Card>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Game game = new Game();

    private static void clearScreen() {
        if (System.getProperty("os.name").startsWith("Window"))
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    private static void setupGame() {
        game = null;
        game = new Game();
        int playerNum = playerAmt();
        players = game.generatePlayers(playerNum);
        game.setupRound();
        comm = game.getComm();
        clearScreen();
    }

    public static void playGame() {
        while (gameOccur) {
            if (aNewGame == 0) {
                setupGame();
                aNewGame = 1;
            }

            else if (game.isGameOver()) {
                clearScreen();
                String playAgain = "";
                System.out.printf("\nWould you like to play again (Y/N): ");
                Scanner paInput = new Scanner(System.in);
                playAgain = paInput.next();

                while (!playAgain.equalsIgnoreCase("Y") && !playAgain.equalsIgnoreCase("N")) {
                    System.out.printf("\nEnter Y or N to have a valid input: ");
                    playAgain = paInput.next();
                }

                if (playAgain.equalsIgnoreCase("N")) {
                    System.exit(0);
                }

                else {
                    setupGame();
                }
            }

            else {
                game.setupRound();
                players = game.getPlayers();
            }

            while (game.getRound() != 4) {
                while (game.isBetRoundRunning()) {
                    printInfo();
                    if (players.get(game.getPlayerCount()) instanceof Human) {
                        String choice = getPlayerInput();
                        if (choice.equalsIgnoreCase("F")) {
                            game.fold();
                        } else if (choice.equalsIgnoreCase("C")) {
                            game.call();
                        } else if (choice.equalsIgnoreCase("B")) {
                            game.Bet(getBetAmt());
                        } else if (choice.equalsIgnoreCase("A")) {
                            game.allIn();
                        } else if (choice.equalsIgnoreCase("Q")) {
                            System.exit(0);
                        }

                        if (isUserFolded()) {
                            showdown();
                            break;
                        }
                    }

                    else {
                        game.processTurn();
                    }

                    game.incrementPlayer();
                    players = game.getPlayers();
                }
            }
            showdown();
        }
    }

    private static int playerAmt() {
        int decision = 0;
        System.out.printf("\nHow many players are there?: ");
        Scanner dcInput = new Scanner(System.in);
        while (decision > 10 || decision < 0) {
            System.out.printf("Enter a valid bet amount: ");
            decision = dcInput.nextInt();
        }
        decision = dcInput.nextInt();
        return decision;
    }

    private static String getPlayerInput() {
        String decision = "";
        System.out.printf(
                "\nWhat would you like to do next\nC = Call/Check\nF = Fold\nA = All In\nB = Bet/Raise\nQ = Quit Game\nYour Choice: ");
        Scanner dcInput = new Scanner(System.in);
        while (decision != "C" || decision != "F" || decision != "A" || decision != "B" || decision != "Q") {
            System.out.printf("Enter a valid choice: ");
            decision = dcInput.next();
        }
        decision = dcInput.next();
        return decision;
    }

    private static int getBetAmt() {
        int decision = 0;
        System.out.printf("\nHow much do you want to bet?: ");
        Scanner dcInput = new Scanner(System.in);
        while (decision > getCurrentPlayer().stack || decision < 0) {
            System.out.printf("Enter a valid bet amount: ");
            decision = dcInput.nextInt();
        }
        return decision;
    }

    private static void printInfo() {
        clearScreen();
        System.out.println("The Community Cards Are:");
        for (Card card : comm) {
            System.out.println(card.toString());
        }

        System.out.println("\nHighest Wager: " + game.getHighestBet());
        System.out.println("\nPot: " + game.getPot());

        Player lastPlayer = new Player();
        lastPlayer = game.getLastPlayer();
        System.out.println("\n Last Player's Action: " + lastPlayer.getAction());

        if (lastPlayer.getAction() == "Bet" || lastPlayer.getAction() == "All In"
                || lastPlayer.getAction() == "Raised") {
            System.out.print(" " + lastPlayer.getBet());
        }

        Player curPlayer = new Player();
        curPlayer = game.getCurrentPlayer();
        System.out.println(curPlayer.getName() + ": ");
        if (curPlayer == players.get(0)) {
            System.out.print("Small Blind");
        } else if (curPlayer == players.get(1)) {
            System.out.print("Big Blind");
        } else if (curPlayer == players.get(-1)) {
            System.out.print("Dealer");
        } else if (curPlayer instanceof AI) {
            System.out.print("AI");
        } else {
            System.out.print("Human Player");
        }

    }

    private static void showdown() {
        String winnerStringDivide = "";
        String winnerString = "";
        String winnerString2 = "";

        ArrayList<Player> winners = game.showdown();

        clearScreen();
        System.out.println("The Community Cards Are:");
        for (Card card : comm) {
            System.out.println(card.toString());
        }

        System.out.println("\nPot: " + game.getPot());

        if ((winners.size() == game.getPlayers().size()) && (game.getPlayers().size() != 1)) {
            winnerStringDivide = "The Pot Will Be Divided Evenly";
            System.out.println(winnerStringDivide);
        } else {
            if (winners.size() == 1)
                winnerString = winners.get(0).getName();
            else {
                int index = 0;
                for (Player player : winners) {
                    if (index == 0)
                        winnerString = player.getName();
                    else if (index == winners.size() - 1) {
                        winnerString2 = " and " + player.getName();
                        winnerString += winnerString2;
                    } else
                        winnerString += ", " + player.getName();
                    index++;
                }
            }
            winnerString += " Won the Pot";
        }

        System.out.println(winnerString);
        System.out.println("\nFinal Hands:");
        for (Player player : winners) {
            System.out.println(player.getName() + ": " + player.getHand().toString() + "\n");
        }
    }

    public static void main(String[] args) {
        // A way to start the game.
        clearScreen();
        String start = "";
        System.out.println("Welcome to Texas-Hold'em Poker!");
        Scanner input = new Scanner(System.in);
        while (!start.equalsIgnoreCase("S")) {
            System.out.printf("Enter \"S\" to start the game: ");
            start = input.next();
        }
        playGame(); // Starts the game.
    }
}