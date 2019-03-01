import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		
		HBox comBar = new HBox();
		comBar.setMinSize(windowW, windowH / 4);
		comBar.setSpacing(100);
		comBar.setAlignment(Pos.CENTER);
		comBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-color: black;");
		for (int index = 1; index < players.size(); index++) {
			VBox comEntry = new VBox();
			Label name = new Label(players.get(index).getName());
			Text stack = new Text("Stack: ");
			Text bet = new Text("Bet: ");
			Text action = new Text("Action: ");
			comEntry.setAlignment(Pos.CENTER);
			comEntry.getChildren().addAll(name, stack, bet, action);
			comBar.getChildren().add(comEntry);
		}
		
		//Left Sidebar
		
		VBox dealerBar = new VBox();
		dealerBar.setMinSize(windowW / 4, windowH / 2);
		dealerBar.setAlignment(Pos.CENTER);
		dealerBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-color: black;");
		
		Text potText = new Text("Pot: ");
		Text highestBetText = new Text("Highest Bet: ");
		Label commLabel = new Label("Community Cards");
		Label flopLabel = new Label("The Flop");
		
		HBox flopBox = new HBox();
		
		HBox singlesBox = new HBox();
		Label turnLabel = new Label("The Turn");
		Label riverLabel = new Label("The River");
		singlesBox.getChildren().addAll(turnLabel, riverLabel);
		
		dealerBar.getChildren().addAll(potText, highestBetText, commLabel, 
				flopLabel, flopBox, singlesBox);
		
		//Lower bar
		
		HBox playerBar = new HBox();
		playerBar.setMinSize(windowW, windowH / 4);
		playerBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-color: black;");
		
		GridPane table = new GridPane();
		table.setMinSize(windowW * (3 / 4), windowH / 2);
		table.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-color: black;");
		
		root.setLeft(dealerBar);
		root.setBottom(playerBar);
		root.setCenter(table);
		root.setTop(comBar);
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
