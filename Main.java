/* This line is required as it is used to get user input
 * for player action during game
 */
import java.util.Scanner;

public class Main {
	
	public static void startGame() {
		//Plays the game!	
	}
	
	public static void main(String[] args) {
		//A way to start the game.
		String start;
		System.out.println("Welcome to Texas-Hold'em Poker!");
		Scanner input = new Scanner(System.in);
		while (!start.equals(" ")) {
			System.out.println("Enter SPACE to start the game.");
			start = input.next();
		}
			startGame();
		

	}

}
