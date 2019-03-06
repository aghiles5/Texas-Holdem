import java.util.ArrayList;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

/**
 * 
 * @author Adam Hiles
 * @version 03/03/18
 */
public class GUI extends Application {
	private final double WIN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	private final double WIN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Texas Hold\'em");
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitHint("");
		
		MainMenu menu = new MainMenu();
		
		Scene scene = new Scene(menu.getMenu(), WIN_WIDTH, WIN_HEIGHT);
		scene.getStylesheets().add("tableStyle.css");
		scene.setFill(Color.BLACK);
		
		((Button) scene.lookup("#startButton")).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUITest.testDeck.shuffle();
				ArrayList<Card> comm = GUITest.generateComm();
				int playerNum = (int) ((Slider) scene.lookup("#comSlider")).getValue() + 1;
				ArrayList<Player> players = GUITest.generatePlayers(playerNum);
				
				BorderPane playArea = new BorderPane();
				ActionBar actionBar = new ActionBar();
				Table table = new Table(players, comm);
				playArea.setBottom(actionBar.getBarPane());
				playArea.setCenter(table.getTablePane());
				scene.setRoot(playArea);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/*
	private void blind(Scene scene, Game game) {
		boolean roundRunning = true;
		while (roundRunning) {
			if (game.getCurrentPlayer() instanceof Human) {
				boolean playerDeciding = true;
				while (playerDeciding) {}
				Player player = game.getCurrentPlayer();
			}
			else
				Player player = game.processTurn();
			if (player.getAction() == "BUSTED OUT") {
				((Label) scene.lookup("#" + player.getName() + "Stack")).setText("");
				((Label) scene.lookup("#" + player.getName() + "Action")).setText("");
				((Label) scene.lookup("#" + player.getName() + "Bet")).setText("");
			}
			else {
				((Label) scene.lookup("#" + player.getName() + "Stack")).setText("Stack: " + player.getStack());
				((Label) scene.lookup("#" + player.getName() + "Action")).setText("Action: " + player.getAction());
				((Label) scene.lookup("#" + player.getName() + "Bet")).setText("Current Bet: " + player.getBet());	
			}
		}
		game.updatePot();
	}
	*/
	
	private void revealCard(ImageView cardBack, ImageView cardFront) {
		ScaleTransition hideFront = new ScaleTransition();
		hideFront.setByX(-1);
		hideFront.setDuration(Duration.seconds(0.001));
		hideFront.setNode(cardFront);
		
		ScaleTransition hideBack = new ScaleTransition();
		hideBack.setByX(-1);
		hideBack.setDuration(Duration.seconds(0.25));
		hideBack.setNode(cardBack);
		
		ScaleTransition showFront = new ScaleTransition();
		showFront.setByX(1);
		showFront.setDuration(Duration.seconds(0.25));
		showFront.setNode(cardFront);
		
		hideFront.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				hideBack.play();
			}
		});
		
		hideBack.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showFront.play();
			}
		});
		
		hideFront.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
