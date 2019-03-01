import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jfxtras.scene.layout.CircularPane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TableGUI extends Application {
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Card> comm = new ArrayList<Card>();
	private static int pot;
	private static int highestBet;
	@Override
	public void start(Stage primaryStage) throws Exception{
		BorderPane root = new BorderPane();
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double windowW = primaryScreenBounds.getWidth();
		double windowH = primaryScreenBounds.getHeight();
		
		Scene scene = new Scene(root, windowW, windowH);
		primaryStage.setTitle("Texas Hold\'em");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		
		//Left bar
		
		/*
		VBox comBar = new VBox();
		comBar.setMinSize(windowW * (7.0 / 38.0), windowH);
		comBar.setSpacing(10);
		comBar.setAlignment(Pos.CENTER);
		comBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 5;" + "-fx-border-color: maroon;" 
				+ "-fx-background-color: indianred;");
		for (int index = 1; index < players.size(); index++) {
			VBox comEntry = new VBox();
			
			Label name = new Label(players.get(index).getName());
			Text stack = new Text("Stack: ");
			Text bet = new Text("Bet: ");
			Text action = new Text("Action: ");
			
			HBox cards = new HBox();
			cards.setSpacing(10);
			cards.setAlignment(Pos.CENTER);
			Image card1 = new Image("/Images/TempCard.png");
			Image card2 = new Image("/Images/TempCard.png");
			cards.getChildren().addAll(new ImageView(card1), new ImageView(card2));
			
			comEntry.setAlignment(Pos.CENTER);
			comEntry.setSpacing(3);
			comEntry.getChildren().addAll(name, stack, bet, action, cards);
			comBar.getChildren().add(comEntry);
		}
		*/
		
		//Right bar
		
		VBox playerBar = new VBox();
		playerBar.setMinSize(windowW * (1.0 / 4.0), windowH);
		playerBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 1;" + "-fx-border-insets: 5;"
		        + "-fx-border-color: black;");
		
		//Centre table
		
		CircularPane table = new CircularPane();
		table.setMinSize(windowW * (3.0 / 4.0), windowH);
		for (Player player : players) {
			VBox comEntry = new VBox();
			
			Label name = new Label(player.getName());
			Text stack = new Text("Stack: ");
			Text bet = new Text("Bet: ");
			Text action = new Text("Action: ");
			
			HBox cards = new HBox();
			cards.setSpacing(10);
			cards.setAlignment(Pos.CENTER);
			Image card1 = new Image("/Images/TempCard.png");
			Image card2 = new Image("/Images/TempCard.png");
			cards.getChildren().addAll(new ImageView(card1), new ImageView(card2));
			
			comEntry.setAlignment(Pos.CENTER);
			comEntry.getChildren().addAll(name, stack, bet, action, cards);
			table.add(comEntry);
		}
		
		root.setRight(playerBar);
		root.setCenter(table);
		//root.setLeft(comBar);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void passArrayLists(ArrayList<Player> playersDesig, ArrayList<Card> commDesig) {
		players = playersDesig;
		comm = commDesig;
	}
	
	public static void updatePot(int newPot) {
		pot = newPot;
	}
	
	public static void updateHighestBet(int newHighestBet) {
		highestBet = newHighestBet;
	}
}
