/**
 * This line is required as it is used to get user input
 * for player action during game
 */
import java.util.Scanner;

public class Main {
	
	private boolean roundOccur;
	private boolean gameOccur;
	private Deck cardDeck = new Deck();
	private Player player1 = new Human();
	private Player player2 = new Human();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> middleCards = new ArrayList<Card>();

	public Main(){
		roundOccur = true;
		gameOccur = true;
		players.add(player1);
		players.add(player2);
	}

	public void startGame() {
		//Allows for the game to keep going until one of the situations below is met
		while(gameOccur){
			if(players.size() == 1){
				String playAgain = "";
				System.out.printf("Would you like to play again (Y/N): ");
				Scanner paInput = new Scanner(System.in);
				playAgain = paInput.next();

				while(!playAgain.equals("Y") || !playAgain.equals("N")){
					System.out.printf("Enter Y or N to have a valid input: ");
					playAgain = paInput.next();
				}

				if(playAgain == "N"){
					break;
				}
			}

			else{
				String quitGame = "";
				System.out.printf("If you would like to quit the game please input Q: ");
				Scanner quitInput = new Scanner(System.in);
				quitGame = quitInput.next();

				if(quitGame.equalsIgnoreCase("Q")){
					break;
				}
			}

			//Allows for the round to keep going
			while(roundOccur){

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
