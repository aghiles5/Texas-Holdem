import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Ellipse;

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
				ActionBar actionBar = new ActionBar(WIN_WIDTH, WIN_HEIGHT);
				Table table = new Table(players, comm);
				playArea.setBottom(actionBar.getBarPane());
				playArea.setCenter(table.getTablePane());
				scene.setRoot(playArea);
				
				((Ellipse) scene.lookup("#" + players.get(0).getName() + "Chip")).setFill(Color.BLUE);
				((Ellipse) scene.lookup("#" + players.get(0).getName() + "Chip")).setVisible(true);
				((Ellipse) scene.lookup("#" + players.get(1).getName() + "Chip")).setFill(Color.YELLOW);
				((Ellipse) scene.lookup("#" + players.get(1).getName() + "Chip")).setVisible(true);
				if (players.size() != 2) {
					((Ellipse) scene.lookup("#" + players.get(players.size() - 1).getName() + "Chip")).setFill(Color.WHITE);
					((Ellipse) scene.lookup("#" + players.get(players.size() - 1).getName() + "Chip")).setVisible(true);
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/*
	private void runGame(Scene scene, Game game) {
		while (game.isGameRunning()) {
			while (game.isPlayRoundRunning()) {
				Label roundNotif = ((Label) scene.lookup("#roundNotif"));
				roundNotif.setText(game.getRoundString());
				roundNotif.setVisible(true);
				TimeUnit.SECONDS.sleep(3);
				roundNotif.setVisible(false);
				
				while (game.isBetRoundRunning()) {
					Player player = game.getCurrentPlayer();
					if (player instanceof Human) {
						player = userTurn(player, scene, game);
						game.incrementPlayer();
					}
					else
						player = game.processTurn();
					
					updatePlayerInfo(player, scene);
					}
				game.updatePot();
			}
			//Showdown code/cleanup goes here
		}
	}
	
	private Player userTurn(Player user, Scene scene, Game game) {
		Button call = ((Button) scene.lookup("#call"));
		Button raise = ((Button) scene.lookup("#raise"));
		HBox controls = ((HBox) scene.lookup("#controls"));
		HBox raiseInput = ((HBox) scene.lookup("#raiseInput"));
		
		if (user.getBet() == game.getBet())
			call.setText("Check");
		else
			call.setText("Call");
		
		controls.setDisable(false);
		
		boolean userDeciding = game.isUserDeciding();
		while (userDeciding) {userDeciding = game.isUserDeciding();}
		
		controls.setDisable(true);
		raiseInput.setVisible(false);
		raise.setText("Raise");
		
		return game.getCurrentPlayer();
	}
	
	private void updatePlayerInfo(Player player, Scene scene) {
		((Label) scene.lookup("#" + player.getName() + "Stack")).setText("Stack: " + player.getStack());
		((Label) scene.lookup("#" + player.getName() + "Action")).setText("Action: " + player.getAction());
		((Label) scene.lookup("#" + player.getName() + "Bet")).setText("Current Bet: " + player.getBet());
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
