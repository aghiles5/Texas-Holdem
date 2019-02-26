/**
 * Main class for the game as the game is played and started here
 * Origin of Texas Holdem
 * 
 * @author Brayden
 * @version 02/20/18
 */

 //Must change all the player1, player2 things to loops later on when using AI and more players
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private boolean roundOccur;
	private boolean gameOccur;
	private Deck cardDeck = new Deck();
	private Player player1 = new Human("Player1");
	private Player player2 = new Human("Player2");
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> middleCards = new ArrayList<Card>();
	private ArrayList<Card> playerHole = new ArrayList<Card>();
	private int playerCount;
	private int aNewGame;

	public Main(){
		roundOccur = true;
		gameOccur = true;
		players.add(player1);
		players.add(player2);
		playerCount = 0;
		aNewGame = 0;
	}

	private String findName(){
		String name = "";
		System.out.printf("Please enter your name player: ");
		Scanner nameInput = new Scanner(System.in);
		name = nameInput.next();
		return name;
	}

	private void newGame(){
		players.clear();
		player1 = null;
		player2 = null;
		player1 = new Human(findName());
		player2 = new Human(findName());
		players.add(player1);
		players.add(player2);
		playerCount = 0;
		cardDeck = null;
		cardDeck = new Deck();
		//Change this to for loop later on for more players
		player1.setHole(cardDeck.dealSingle());
		player2.setHole(cardDeck.dealSingle());
		player1.setHole(cardDeck.dealSingle());
		player2.setHole(cardDeck.dealSingle());
	}

	private void setupRound(){
		player1.emptyHole();
		player1.emptyHand();
		player2.emptyHole();
		player2.emptyHand();
		cardDeck = null;
		cardDeck = new Deck();
		//Change this to for loop later on for more players
		player1.setHole(cardDeck.dealSingle());
		player2.setHole(cardDeck.dealSingle());
		player1.setHole(cardDeck.dealSingle());
		player2.setHole(cardDeck.dealSingle());
	}

	private String getPlayerInput(){
		String decision = "";
		System.out.printf("What would you like to do next (C for Check, F for Fold): ");
		Scanner dcInput = new Scanner(System.in);
		decision = dcInput.next();
		return decision;
	}

	public void startGame() {
		//Allows for the game to keep going until one of the situations below is met
		while(gameOccur){
			if(players.size() == 1){
				String playAgain = "";
				System.out.printf("Would you like to play again (Y/N): ");
				Scanner paInput = new Scanner(System.in);
				playAgain = paInput.next();

				while(!playAgain.equalsIgnoreCase("Y") || !playAgain.equalsIgnoreCase("N")){
					System.out.printf("Enter Y or N to have a valid input: ");
					playAgain = paInput.next();
				}

				if(playAgain.equalsIgnoreCase("N")){
					break;
				}

				else{
					newGame();
				}

			}

			else if(aNewGame == 0){
				newGame();
				aNewGame = 1;
			}

			else{
				String quitGame = "";
				System.out.printf("Please input Q to Quit Game or Anything else to Continue: ");
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
			while(roundOccur){
				for(int i = 0; i)
			}
		}
	}
	
	public void main(String[] args) {
		//A way to start the game.
		String start = "";
		System.out.println("Welcome to Texas-Hold'em Poker!");
		Scanner input = new Scanner(System.in);
		while(!start.equals(" ")) {
			System.out.println("Enter SPACE to start the game.");
			start = input.next();
		}
		startGame(); //Starts the game.
	}
}
