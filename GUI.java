import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Ellipse;

/**
 * This method is the entry point for a new, GUI-based game. The menu is 
 * displayed, new Games are created, and the main game loop is run from here.
 * 
 * @author Adam Hiles
 * @version 03/13/18
 */
public class GUI extends Application {
	private final double WIN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	private final double WIN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	
	private boolean userFolded = false;
	
	/**
	 * On the start of the GUI the main menu will be displayed and an
	 * EventHandler for its start button will be created that starts
	 * the game if pressed.
	 * 
	 * @param the stage of the GUI
	 */
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

		primaryStage.setScene(scene);
		primaryStage.show();
		
		((Button) scene.lookup("#startButton")).setOnAction(new EventHandler<ActionEvent>() { //Temporarily, the EventHandler for the main menu's start button is set here.
			@Override
			public void handle(ActionEvent event) {
				generatePlayArea(scene);
			}
		});
	}
	
	/**
	 * Before starting the game the player are will be generated by creating a
	 * new Game object, ActionBar and Table objects based on Game information,
	 * and creating EventHandlers for the user's action buttons. These 
	 * EventHandlers will likely be moved to their own class for future
	 * iterations. The start of a round is called once all this is set up.
	 * 
	 * @param scene the GUI scene
	 */
	private void generatePlayArea(Scene scene) {
		Game game = new Game();
		int playerNum = (int) ((Slider) scene.lookup("#comSlider")).getValue() + 1;
		ArrayList<Player> players = game.generatePlayers(playerNum);
		game.setupRound();
		ArrayList<Card> comm = game.getComm();
		
		
		BorderPane playArea = new BorderPane();
		ActionBar actionBar = new ActionBar(WIN_WIDTH, WIN_HEIGHT);
		Table table = new Table(players, comm);
		playArea.setBottom(actionBar.getBarPane());
		playArea.setCenter(table.getTablePane());
		scene.setRoot(playArea);
		
		((Button) scene.lookup("#notifCont")).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				((HBox) scene.lookup("#notif")).setVisible(false);
				for (Player player : game.getPlayers()) {
					player.setAction(" ");
					((Label) scene.lookup("#" + player.getName() + "Action")).setText(" ");
				}
				 if (game.getRound() < 4)
				 	runTurn(scene, game);
				 else if (game.getRound() == 4) {
				 	if (((Label) scene.lookup("#notifLabel")).getText() == "Showdown")
				 		showdown(scene, game);
				 	else
				 		cleanup(scene, game);
				 	}
			}
		});
		
		((Button) scene.lookup("#fold")).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.fold();
				finishUserTurn(scene, game);
			}
		});
		
		((Button) scene.lookup("#raiseConfirm")).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
		});
		
		((Button) scene.lookup("#call")).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.tempCheck();
				finishUserTurn(scene, game);
			}
		});
		
		runPlayRound(scene, game);
	}
	
	/**
	 * Before starting a round of play the small blind, big blind, and dealer
	 * chips are set in their appropriate spot, players' hole cards are
	 * updated, and the community cards are updated. After these actions are
	 * carried out the first round notification is called.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void runPlayRound(Scene scene, Game game) {
		ArrayList<Player> players = game.getPlayers();
		((Ellipse) scene.lookup("#" + players.get(0).getName() + "Chip")).setFill(Color.BLUE);
		((Ellipse) scene.lookup("#" + players.get(0).getName() + "Chip")).setVisible(true);
		((Ellipse) scene.lookup("#" + players.get(1).getName() + "Chip")).setFill(Color.YELLOW);
		((Ellipse) scene.lookup("#" + players.get(1).getName() + "Chip")).setVisible(true);
		if (players.size() != 2) {
			((Ellipse) scene.lookup("#" + players.get(players.size() - 1).getName() + "Chip")).setFill(Color.WHITE);
			((Ellipse) scene.lookup("#" + players.get(players.size() - 1).getName() + "Chip")).setVisible(true);
		}
		
		for (Player player : players) {
			((ImageView) scene.lookup("#" + player.getName() + "Card1")).setImage(new Image("/Images/" + player.getHole().get(0).getSuit() + "/" + player.getHole().get(0).getRank() + ".png")); 
			((ImageView) scene.lookup("#" + player.getName() + "Card2")).setImage(new Image("/Images/" + player.getHole().get(1).getSuit() + "/" + player.getHole().get(1).getRank() + ".png"));
			((Label) scene.lookup("#" + player.getName() + "Action")).setText(" ");
			((Label) scene.lookup("#" + player.getName() + "Bet")).setText("Current Bet: $0");
		}
		
		for(int index = 0; index < 5; index++) {
			ArrayList<Card> comm = game.getComm();
			((ImageView) scene.lookup("#commFront" + index)).setImage(new Image("/Images/" + comm.get(index).getSuit() + "/" + comm.get(index).getRank() + ".png"));
		}
		
		ImageView drawCard = (ImageView) scene.lookup("#drawCard");
		double nodeMinX = drawCard.getLayoutBounds().getMinX();
	    double nodeMinY = drawCard.getLayoutBounds().getMinY();
		Point2D nodeInScene = drawCard.localToScene(nodeMinX, nodeMinY);
		System.out.println(nodeInScene.getX() + ", " + nodeInScene.getY());
		
		notifyRound(scene, game);
		//dealCard(scene, (ImageView) scene.lookup("#YouCard1Back"), (ImageView) scene.lookup("#YouCard1"));
	}
	
	/**
	 * In between rounds a notification window will appear, prompting the user
	 * to continue. The community cards will also be revealed as appropriate.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void notifyRound(Scene scene, Game game) {
		if (game.getRound() == 1)
			revealFlop(scene);
		else if (game.getRound() == 2)
			revealCard((ImageView) scene.lookup("#commBack3"), (ImageView) scene.lookup("#commFront3"));
		else if (game.getRound() == 3)
			revealCard((ImageView) scene.lookup("#commBack4"), (ImageView) scene.lookup("#commFront4"));
		
		HBox notif = (HBox) scene.lookup("#notif");
		Label notifLabel = (Label) scene.lookup("#notifLabel");
		notifLabel.setText(game.getRoundString());
		
		if (userFolded) { //If the user folded, fast track to showdown
			if (game.getRound() == 4)
				showdown(scene, game);
			else
				runTurn(scene, game);
		}
		
		if (game.getPlayers().size() == 1) { //If one player remains, they get the pot
			while (game.getRound() != 4) {
				game.incrementRound();
				notifyRound(scene, game);
			}
			showdown(scene, game);
		}
		
		notif.setVisible(true);
	}
	
	/**
	 * To run a player turn this method retrieves the current player from the
	 * game. If the player is the user the method for setting up a user turn is
	 * called. Else, if they are an AI, the game will process their turn, and,
	 * after a short pause for effect, a method will be called to finish their
	 * turn.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void runTurn(Scene scene, Game game) {
		Player player = game.getCurrentPlayer();
		if (player instanceof Human) {
			setupUserTurn(player, scene, game);
		}
		else {
			((Label) scene.lookup("#" + player.getName() + "Name")).setStyle("-fx-text-fill: red;");
			player = game.processTurn();
			
			PauseTransition pause = new PauseTransition(new Duration(1000));
			
			if (userFolded)
				pause.setDuration(new Duration(1));
			
			pause.setOnFinished(new EventHandler<ActionEvent>() { 
				@Override
				public void handle(ActionEvent event) {
					finishAITurn(scene, game);
				}
			});
			
			pause.play();
		}
	}
	
	/**
	 * After an AI's turn is processed, their on screen information will be
	 * updated, the counter for the next player will be incremented, and either
	 * the program will continue to that next player's turn or a new round
	 * notification will appear, granted the betting round is over.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void finishAITurn(Scene scene, Game game) {
		Player player = game.getLastPlayer();
		updatePlayerInfo(player, scene);
		game.incrementPlayer();
		((Label) scene.lookup("#" + player.getName() + "Name")).setStyle("-fx-text-fill: black;");
		if (game.isBetRoundRunning())
			runTurn(scene, game);
		else
			notifyRound(scene, game);
	}
	
	/**
	 * To setup the user's turn their control buttons are simply enabled and 
	 * their name is colored to mark them as the current player. Additional
	 * scene lookups are included for future iterations where this method
	 * will have to dynamically change call/raise button names and change the
	 * bounds of the raise slide to reflect the user's stack.
	 * 
	 * @param user the human player
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void setupUserTurn(Player user, Scene scene, Game game) {
		Button call = (Button) scene.lookup("#call");
		Button raise = (Button) scene.lookup("#raise");
		HBox controls = (HBox) scene.lookup("#controls");
		HBox raiseInput = (HBox) scene.lookup("#raiseInput");
		Slider raiseSlider = (Slider) scene.lookup("#raiseSlider");
		
		((Label) scene.lookup("#" + user.getName() + "Name")).setStyle("-fx-text-fill: red;");
		
		controls.setDisable(false);
	}
	
	/**
	 * At the end of the user's turn their controls are disabled, the raise
	 * menu is closed, button names are defaulted, and the current player is
	 * incremented and the call to run their turn is made. If the betting round
	 * is over the call to setup the next round is made.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void finishUserTurn(Scene scene, Game game) {
		HBox controls = (HBox) scene.lookup("#controls");
		HBox raiseInput = (HBox) scene.lookup("#raiseInput");
		Slider raiseSlider = (Slider) scene.lookup("#raiseSlider");
		Button raise = (Button) scene.lookup("#raise");
		
		Player user = game.getLastPlayer();
		controls.setDisable(true);
		raiseInput.setVisible(false);
		raise.setText("Bet");
		updatePlayerInfo(user, scene);
		((Label) scene.lookup("#" + user.getName() + "Name")).setStyle("-fx-text-fill: black;");
		game.incrementPlayer();
		
		if (user.getAction() == "Folded")
			userFolded = true;
		
		if (game.isBetRoundRunning())
			runTurn(scene, game);
		else
			notifyRound(scene, game);
	}
	
	/**
	 * After a player has carried out their turn their information as displayed
	 * on the GUI needs to be updated. Currently, only their action is updated
	 * but their stack and bet will also be updated once money systems are
	 * 
	 * 
	 * @param player the player whose information needs to be updated
	 * @param scene the GUI scene
	 */
	private void updatePlayerInfo(Player player, Scene scene) {
		//((Label) scene.lookup("#" + player.getName() + "Stack")).setText("Stack: " + player.getStack());
		((Label) scene.lookup("#" + player.getName() + "Action")).setText("Action: " + player.getAction());
		//((Label) scene.lookup("#" + player.getName() + "Bet")).setText("Current Bet: " + player.getBet());
		if (player.getAction() == "Folded")
			((Label) scene.lookup("#" + player.getName() + "Bet")).setText(" ");
	}
	
	/**
	 * For the showdown phase of play each remaining player's bet label is
	 * set to display their highest hand rank. After game is called to 
	 * process the showdown the notification window is shown with a dynamic
	 * string listing the round's victors.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void showdown(Scene scene, Game game) {
		revealAllCards(game.getPlayers(), scene);
		for (Player player : game.getPlayers())
			((Label) scene.lookup("#" + player.getName() + "Bet")).setText("Hand: " + player.getHand().toString());
		
		ArrayList<Player> winners = game.showdown();
		HBox notif = (HBox) scene.lookup("#notif");
		Label notifLabel = (Label) scene.lookup("#notifLabel");
		
		StringBuilder winnerString = new StringBuilder();
		if ((winners.size() == game.getPlayers().size()) &&(game.getPlayers().size() != 1))
			winnerString.append("The Pot Will Be Divided Evenly");
		else {
			if (winners.size() == 1)
				winnerString.append(winners.get(0).getName());
			else {
				int index = 0;
				for (Player player : winners) {
					if (index == 0)
						winnerString.append(player.getName());
					else if (index == winners.size() - 1)
						winnerString.append(" and " + player.getName());
					else
						winnerString.append(", " + player.getName());
					index++;
				}
			}
			winnerString.append(" Won the Pot");
		}
		
		notifLabel.setText(winnerString.toString());
		notif.setVisible(true);
	}
	
	/**
	 * To cleanup for the next round of play the backs of all AI and community
	 * cards are returned and game is called to setup a new round. The GUI
	 * event loop is returned to runPlayRound and the game continues.
	 * 
	 * @param scene the GUI scene
	 * @param game the Game object
	 */
	private void cleanup(Scene scene, Game game) {
		for (Player player : game.getPlayers()) {
			if (player instanceof AI) {
				hideCard((ImageView) scene.lookup("#" + player.getName() + "Card1Back"));
				hideCard((ImageView) scene.lookup("#" + player.getName() + "Card2Back"));
			}
		}
		
		userFolded = false;
		
		for (int index = 0; index < 5; index++) {
			hideCard((ImageView) scene.lookup("#commBack" + index));
		}
		
		game.setupRound();
		runPlayRound(scene, game);
	}
	
	/**
	 * For each AI player in the passed list, i.e. those whose cards are
	 * covered, their hole cards are revealed through the card reveal 
	 * animation.
	 * 
	 * @param players the players still in the game
	 * @param scene the game node tree
	 */
	private void revealAllCards(ArrayList<Player> players, Scene scene) {
		for (Player player : players) {
			if (player instanceof AI) {
				ScaleTransition showFrontA = revealCard((ImageView) scene.lookup("#" + player.getName() + "Card1Back"), (ImageView) scene.lookup("#" + player.getName() + "Card1"));
				showFrontA.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						revealCard((ImageView) scene.lookup("#" + player.getName() + "Card2Back"), (ImageView) scene.lookup("#" + player.getName() + "Card2"));
					}
				});	
			}
		}
	}
	
	/**
	 * For the second betting round the first three community cards are
	 * revealed sequentially via the below method and the card reveal
	 * animation.
	 * 
	 * @param scene the game node tree
	 */
	private void revealFlop(Scene scene) {
		ScaleTransition showFrontA = revealCard((ImageView) scene.lookup("#commBack0"), (ImageView) scene.lookup("#commFront0"));
		showFrontA.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ScaleTransition showFrontB = revealCard((ImageView) scene.lookup("#commBack1"), (ImageView) scene.lookup("#commFront1"));
				showFrontB.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						revealCard((ImageView) scene.lookup("#commBack2"), (ImageView) scene.lookup("#commFront2"));
					}
				});	
			}
		});	
	}
	
	private void dealPlayerHole(Scene scene, Game game, int index) {
		Player subPlayer = game.getPlayers().get(index);
		ImageView cardAFront = (ImageView) scene.lookup("#" + subPlayer.getName() + "Card1");
		ImageView cardBFront = (ImageView) scene.lookup("#" + subPlayer.getName() + "Card2");
		ImageView cardABack = (ImageView) scene.lookup("#" + subPlayer.getName() + "Card1Back");
		ImageView cardBBack = (ImageView) scene.lookup("#" + subPlayer.getName() + "Card2Back");
		
		TranslateTransition moveDrawCardA = dealCard(scene, cardABack, cardAFront);
		
		moveDrawCardA.setOnFinished(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				TranslateTransition moveDrawCardB = dealCard(scene, cardBBack, cardBFront);
				moveDrawCardB.setOnFinished(new EventHandler<ActionEvent>() { 
					@Override
					public void handle(ActionEvent event) {
						if (index < game.getPlayers().size() - 1)
							dealPlayerHole(scene, game, index + 1);
						else
							notifyRound(scene, game);
					}
				});
			}
		});
	}
	
	private TranslateTransition dealCard(Scene scene, ImageView cardBack, ImageView cardFront) {
		ImageView drawCard = (ImageView) scene.lookup("#drawCard");
		double originX = drawCard.localToScene(drawCard.getBoundsInLocal()).getMinX(); 
		double originY = drawCard.localToScene(drawCard.getBoundsInLocal()).getMinY(); 
		double targetX = cardBack.localToScene(cardBack.getBoundsInLocal()).getMinX(); 
		double targetY = cardBack.localToScene(cardBack.getBoundsInLocal()).getMinY(); 
		
		System.out.println(originX + ", " + originY);
		System.out.println(targetX + ", " + targetY);
		
		TranslateTransition moveDrawCard = new TranslateTransition(new Duration(2000), drawCard);
		moveDrawCard.setFromX(originX);
		moveDrawCard.setFromY(originY);
		moveDrawCard.setToX(targetX - originX);
		moveDrawCard.setToY(targetY - originY);
		
		moveDrawCard.setOnFinished(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				cardBack.setVisible(true);
				cardFront.setVisible(true);
			}
		});
		
		moveDrawCard.play();
		
		return moveDrawCard;
	}
	
	/**
	 * The set of ImageViews passed into this method will be swapped by an
	 * appropriate flipping animation.
	 * 
	 * @param cardBack the ImageView of the card's back
	 * @param cardFront the ImageView of the card's face
	 */
	private ScaleTransition revealCard(ImageView cardBack, ImageView cardFront) {
		ScaleTransition hideFront = new ScaleTransition(); //The face is initially hidden from view
		hideFront.setByX(-1);
		hideFront.setDuration(Duration.seconds(0.001));
		hideFront.setNode(cardFront);
		
		ScaleTransition hideBack = new ScaleTransition(); //The back is scaled to a line to be invisible
		hideBack.setByX(-1);
		hideBack.setDuration(Duration.seconds(0.25));
		hideBack.setNode(cardBack);
		
		ScaleTransition showFront = new ScaleTransition(); //The face is returned from a line to full size
		showFront.setByX(1);
		showFront.setDuration(Duration.seconds(0.25));
		showFront.setNode(cardFront);
		
		hideFront.setOnFinished(new EventHandler<ActionEvent>() { //Once the face is hidden the back is flipped
			@Override
			public void handle(ActionEvent event) {
				hideBack.play();
			}
		});
		
		hideBack.setOnFinished(new EventHandler<ActionEvent>() { //Once the back is flipped the front is flipped
			@Override
			public void handle(ActionEvent event) {
				showFront.play();
			}
		});
		
		hideFront.play();
		
		return showFront;
	}
	
	/**
	 * To hide cards for cleanup the back will simply be near instantly
	 * stretch back to its original width.
	 * 
	 * @param cardBack the ImageView of the card's back
	 */
	private void hideCard(ImageView cardBack) {
		ScaleTransition showBack = new ScaleTransition();
		showBack.setByX(1);
		showBack.setDuration(Duration.seconds(0.001));
		showBack.setNode(cardBack);
		showBack.play();
	}
	
	/**
	 * On launch the extended Application handles the launch of the GUI,
	 * eventually leading to the start method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
