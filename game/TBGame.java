package game;

/**
 * The main class for the text based version of the game
 * Origin of Texas Holdem - Text Based
 * 
 * @author Brayden Schmaltz - Campbell
 * @version 03/27/19
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import cards.Card;
import players.AI;
import players.Human;
import players.Player;

public class TBGame {

    private static boolean gameOccur = true;
    private static int aNewGame = 0;
    private static ArrayList<Card> comm = new ArrayList<Card>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Player player = new Player();
    private static Game game = new Game();

    /**
     * Clears the terminal
     */
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

    /**
     * Sets up the game to be played and processed through the terminal
     */
    private static void setupGame() {
        game = null;
        game = new Game();
        int playerNum = playerAmt();
        int stackNum = stackAmt();
        players = game.generatePlayers(playerNum, stackNum);
        game.setupRound();
        comm = game.getComm();
        clearScreen();
    }

    /**
     * Plays the whole game through the terminal
     */
    public static void playGame() {
        while (gameOccur) {
            if (aNewGame == 0) {
                setupGame();
                aNewGame = 1;
            }

            else if (game.isGameOver()) {
                clearScreen();
                for (Player player : players) {
                    if (player instanceof Human) {
                        if (player.stack == 0) {
                            System.out.println("You have lost the match.");
                        }
                    } else {
                        if (player.stack == 0) {
                            System.out.println("You have won the match!");
                        }
                    }
                }
                String playAgain = "";
                System.out.printf("Would you like to play again (Y/N): ");
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
                clearScreen();
                game.setupRound();
                System.out.println("A New Round Has Started");
                continueCheck();
                players = game.getPlayers();
            }

            while (game.getRound() < 5) {
                clearScreen();
                System.out.println("Betting Round: " + game.getRoundString());
                continueCheck();
                Boolean roundCheck = true;
                while (roundCheck) {
                    player = game.getCurrentPlayer();
                    printInfo();
                    if (players.size() == 1) {
                        break;
                    }

                    if (player.stack == 0) {
                        break;
                    }

                    if (player instanceof Human) {
                        if (game.getRound() == 0 && (game.getPlayerCount() == 0 || game.getPlayerCount() == 1)) {
                            if (game.getPlayerCount() == 0 && game.getHighestBet() == 0) {
                                game.bet(game.getSmallBlind());
                            } else if (game.getPlayerCount() == 1 && game.getHighestBet() == game.getSmallBlind()) {
                                game.bet(game.getSmallBlind());
                            } else {
                                String choice = getPlayerInput();
                                if (choice.equalsIgnoreCase("F")) {
                                    game.fold();
                                    break;
                                } else if (choice.equalsIgnoreCase("C")) {
                                    game.call();
                                } else if (choice.equalsIgnoreCase("B")) {
                                    game.bet(getBetAmt());
                                } else if (choice.equalsIgnoreCase("A")) {
                                    game.bet(player.stack);
                                    break;
                                } else if (choice.equalsIgnoreCase("Q")) {
                                    System.exit(0);
                                }
                            }
                        } else {
                            String choice = getPlayerInput();
                            if (choice.equalsIgnoreCase("F")) {
                                game.fold();
                                break;
                            } else if (choice.equalsIgnoreCase("C")) {
                                game.call();
                            } else if (choice.equalsIgnoreCase("B")) {
                                game.bet(getBetAmt());
                            } else if (choice.equalsIgnoreCase("A")) {
                                game.bet(player.stack);
                                break;
                            } else if (choice.equalsIgnoreCase("Q")) {
                                System.exit(0);
                            }
                        }
                    }

                    else {
                        player = game.processTurn();
                        if (player.stack == 0) {
                            break;
                        }
                    }

                    players = game.getPlayers();
                    game.incrementPlayer();
                    roundCheck = game.isBetRoundRunning();
                    continueCheck();
                }

                if (players.size() == 1) {
                    break;
                }

                if (player instanceof Human) {
                    if (player.stack == 0) {
                        players = game.getPlayers();
                        game.incrementPlayer();
                        printInfo();
                        player = game.processTurn();
                        continueCheck();
                        break;
                    }
                }

                else if (player instanceof Human) {
                    if (player.stack == 0) {
                        players = game.getPlayers();
                        game.incrementPlayer();
                        printInfo();
                        String choice = getPlayerInput();
                        if (choice.equalsIgnoreCase("F")) {
                            game.fold();
                            break;
                        } else if (choice.equalsIgnoreCase("C")) {
                            game.call();
                        } else if (choice.equalsIgnoreCase("B")) {
                            game.bet(getBetAmt());
                        } else if (choice.equalsIgnoreCase("A")) {
                            game.bet(player.stack);
                            break;
                        } else if (choice.equalsIgnoreCase("Q")) {
                            System.exit(0);
                        }
                        continueCheck();
                        break;
                    }
                }

                if (game.isUserFolded()) {
                    break;
                }
            }

            players = game.getPlayers();
            if (game.getRound() != 4) {
                game.incrementRound();
            }
            showdown();
            continueCheck();
        }
    }

    /**
     * Asks the user to enter the amount of players he wants in his game
     * 
     * @Return an integer for the player count
     */
    private static int playerAmt() {
        int decision = 0;
        System.out.printf("\nHow many players are there? (2 = Min, 10 = Max): ");
        Scanner dcInput = new Scanner(System.in);
        decision = dcInput.nextInt();
        while (decision > 10 || decision < 2) {
            System.out.printf("Enter a valid player amount: ");
            decision = dcInput.nextInt();
        }
        return decision;
    }

    /**
     * Asks the user to choose one of the stack amounts he wants to use in his game
     * 
     * @Return an integer for the stack amount
     */
    private static int stackAmt() {
        int decision = 0;
        System.out.printf(
                "\nWhat is the default Stack Amount?:\n1 = 1000\n2 = 10000\n3 = 100000\n4 = 1000000\nYour Choice: ");
        Scanner dcInput = new Scanner(System.in);
        decision = dcInput.nextInt();
        while (decision > 4 || decision < 1) {
            System.out.printf("Enter a valid Stack Choice: ");
            decision = dcInput.nextInt();
        }
        if (decision == 1) {
            return 1000;
        } else if (decision == 2) {
            return 10000;
        } else if (decision == 3) {
            return 100000;
        } else {
            return 1000000;
        }
    }

    /**
     * Asks the user to enter one of the input choices
     * 
     * @Return a string that is used for the players betting decision
     */
    private static String getPlayerInput() {
        String decision = "";
        System.out.printf(
                "\nWhat would you like to do next\nC = Call/Check\nF = Fold\nA = All In\nB = Bet/Raise\nQ = Quit Game\nYour Choice: ");
        Scanner dcInput = new Scanner(System.in);
        decision = dcInput.next();
        while (!decision.equalsIgnoreCase("C") && !decision.equalsIgnoreCase("F") && !decision.equalsIgnoreCase("A")
                && !decision.equalsIgnoreCase("B") && !decision.equalsIgnoreCase("Q")) {
            System.out.printf("Enter a valid choice: ");
            decision = dcInput.next();
        }
        System.out.println("");
        return decision;
    }

    /**
     * Asks the user to enter the amount of money he wants to bet if he chooses to bet
     * 
     * @Return an integer for the players bet amount
     */
    private static int getBetAmt() {
        int decision = 0;
        System.out.printf("\nHow much do you want to bet?: ");
        Scanner dcInput = new Scanner(System.in);
        decision = dcInput.nextInt();
        while (decision > game.getCurrentPlayer().stack || decision < 0) {
            System.out.printf("Enter a valid bet amount: ");
            decision = dcInput.nextInt();
        }
        return decision;
    }

    /**
     * Asks the user to enter in any random key to continue the game
     */
    private static void continueCheck() {
        String decision = "";
        System.out.printf("\nType Anything to Continue: ");
        Scanner dcInput = new Scanner(System.in);
        decision = dcInput.next();
    }

    /**
     * Prints the info of the player that is currently making their decision
     */
    private static void printInfo() {
        clearScreen();
        System.out.println("Round: " + game.getRoundString());
        System.out.println("\nThe Community Cards Are:");
        int commCardCount = 0;
        for (Card card : comm) {
            if (game.getRound() == 0) {
                // No Cards
            } else if (game.getRound() == 1) {
                if (commCardCount < 3) {
                    System.out.println(card.toString());
                }
            } else if (game.getRound() == 2) {
                if (commCardCount < 4) {
                    System.out.println(card.toString());
                }
            } else if (game.getRound() == 3 || game.getRound() == 4) {
                System.out.println(card.toString());
            }
            commCardCount++;
        }

        System.out.println("\nHighest Wager: " + game.getHighestBet());
        System.out.println("\nPot: " + game.getPot() + "\n");

        Player lastPlayer = new Player();
        if (game.getPlayerCount() != 0 && game.getHighestBet() != 0) {
            lastPlayer = game.getLastPlayer();
            System.out.printf("Last Player's Action: " + lastPlayer.getAction());
            if (lastPlayer.getAction() == "Bet" || lastPlayer.getAction() == "All In"
                    || lastPlayer.getAction() == "Raised") {
                System.out.printf(" - " + lastPlayer.getBet());
            }
            System.out.println("\n");
        }

        Player curPlayer = game.getCurrentPlayer();
        System.out.printf(curPlayer.getName() + ": ");
        if (game.getPlayerCount() == 0 && game.getRound() == 0) {
            System.out.printf("Small Blind");
        } else if (game.getPlayerCount() == 1 && game.getRound() == 0) {
            System.out.printf("Big Blind");
        } else if (game.getPlayerCount() == players.size()) {
            System.out.printf("Dealer");
        } else if (curPlayer instanceof AI) {
            System.out.printf("AI");
        } else {
            System.out.printf("Human Player");
        }

        System.out.println("\nStack: " + curPlayer.stack);
        System.out.println("\nHole:\n");
        if (curPlayer instanceof Human) {
            for (Card card : curPlayer.getHole()) {
                System.out.println(card.toString());
            }
        } else {
            for (int i = 0; i < 2; i++) {
                System.out.println("Hidden Card");
            }
        }

        System.out.println("");
    }

    /**
     * Prints the winner of the round and how much money they get
     */
    private static void showdown() {
        String winnerStringDivide = "";
        String winnerString = "";
        String winnerString2 = "";

        clearScreen();
        System.out.println("The Community Cards Are:");
        for (Card card : comm) {
            System.out.println(card.toString());
        }

        System.out.println("\nPot: " + game.getPot() + "\n");

        ArrayList<Player> winners = game.showdown();

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