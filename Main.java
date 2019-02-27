/**
 * Main class for the game as the game is played and started here
 * Origin of Texas Holdem
 * 
 * @author Brayden
 * @version 02/20/18
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class Main {
	
	private static boolean roundOccur = true;
	private static boolean gameOccur = true;
	private static Deck cardDeck = new Deck();
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Card> middleCards = new ArrayList<Card>();
	private static int aNewGame = 0;

	private static String findName(){
		String name = "";
		System.out.printf("Please enter your name player: ");
		Scanner nameInput = new Scanner(System.in);
		name = nameInput.next();
		return name;
	}

	private static void newGame(){
		roundOccur = true;
		players.clear();
		players.add(new Human(findName()));
		players.add(new Human(findName()));
		cardDeck = null;
		cardDeck = new Deck();
		//Change this to for loop later on for more players
		for (int i = 0; i < 2; i++) {
			for (Player player : players)
				player.setHole(cardDeck.dealSingle());
		}
	}

	private static void setupRound(){
		roundOccur = true;
		for (Player player : players){
			player.emptyHand();
			player.emptyHole();
		}
		middleCards.clear();
		cardDeck = null;
		cardDeck = new Deck();
		//Change this to for loop later on for more players
		for (int i = 0; i < 2; i++) {
			for (Player player : players)
				player.setHole(cardDeck.dealSingle());
		}
	}

	private static String getPlayerInput(){
		String decision = "";
		System.out.printf("\nWhat would you like to do next (C for Check, F for Fold): ");
		Scanner dcInput = new Scanner(System.in);
		decision = dcInput.next();
		return decision;
	}

	private static void clearScreen() {    
		if(System.getProperty("os.name").startsWith("Window"))
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

	public static void startGame() {
		//Allows for the game to keep going until one of the situations below is met
		while(gameOccur){
			 if(aNewGame == 0){
				newGame();
				aNewGame = 1;
			}

			else if(players.size() == 1){
				String playAgain = "";
				System.out.printf("\nWould you like to play again (Y/N): ");
				Scanner paInput = new Scanner(System.in);
				playAgain = paInput.next();

				while(!playAgain.equalsIgnoreCase("Y") && !playAgain.equalsIgnoreCase("N")){
					System.out.printf("\nEnter Y or N to have a valid input: ");
					playAgain = paInput.next();
				}

				if(playAgain.equalsIgnoreCase("N")){
					break;
				}

				else{
					newGame();
				}

			}

			else{
				String quitGame = "";
				System.out.printf("\nPlease input Q to Quit Game or Anything else to Continue: ");
				Scanner quitInput = new Scanner(System.in);
				quitGame = quitInput.next();

				if(quitGame.equalsIgnoreCase("Q")){
					break;
				}

				else{
					setupRound();
				}
			}

			//Allows for the round to keep going
			String input = "";
			while(roundOccur){
				for(int i = 0; i < 4; i++) {
					if (i != 0)
						middleCards = cardDeck.dealCard(middleCards);
					for (Player player : players) {
						clearScreen();
						if (i != 0) {
							System.out.println("The Community Cards Are:");
							for (Card card : middleCards){
								System.out.println(card.toString());
							}
							System.out.println("");
						}

						System.out.println(player.getName() + "'s Cards:");
						for (Card card : player.getHole())
							System.out.println(card.toString());
						
						input = getPlayerInput();
						player.getDecision(input);
						if(input.equalsIgnoreCase("F")){
							roundOccur = false;
							players.remove(player);
							break;
						}
					}

					if(input.equalsIgnoreCase("F")){
						roundOccur = false;
						break;
					}
				}

				if(input.equalsIgnoreCase("F")){
					roundOccur = false;
					break;
				}

				clearScreen();
				Showdown.showdown(players, middleCards);
				roundOccur = false;
			}
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		//A way to start the game.
		clearScreen();
		String start = "";
		System.out.println("Welcome to Texas-Hold'em Poker!");
		Scanner input = new Scanner(System.in);
		while(!start.equalsIgnoreCase("S")) {
			System.out.printf("Enter \"S\" to start the game: ");
			start = input.next();
		}
		startGame(); //Starts the game.
	}
}
