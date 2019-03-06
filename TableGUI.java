import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Adam Hiles
 * @version 03/03/18
 */
public class TableGUI extends Application {
	private final double WIN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	private final double WIN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	
	private final double TABLE_TO_SCREEN_RATIO = 8.0 / 3.0;
	private final double TABLE_HEIGHT_RATIO = 1.25;
	private final double TABLE_RIM_RATIO = 1.0 / 36.0;
	private final double TABLE_OUTSET_RATIO = 1.0 / 10.0;
	private final double TABLE_INSET_RATIO = 1.0 / 8.0;
	
	private final double TABLE_WIDTH = WIN_WIDTH / TABLE_TO_SCREEN_RATIO; //Referring to the length of the straightaway
	private final double TABLE_RIM = TABLE_WIDTH * TABLE_RIM_RATIO;
	
	private final double PLAYER_OUTSET = TABLE_WIDTH * TABLE_OUTSET_RATIO; //The distance of player information from the table
	private final double PLAYER_INSET = TABLE_WIDTH * TABLE_INSET_RATIO; //The distance of player bets and cards? inside the table
	
	private final double SEAT_WIDTH = 50.0;
	private final double SEAT_HEIGHT = 60.0;
	
	private final double PLACE_WIDTH = 150.0;
	private final double PLACE_HEIGHT = 80.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		GUITest.testDeck.shuffle();
		ArrayList<Card> comm = GUITest.generateComm();
		ArrayList<Player> players = GUITest.generatePlayers(10);
		
		BorderPane root = new BorderPane();
		
		Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
		scene.getStylesheets().add("tableStyle.css");
		
		primaryStage.setTitle("Texas Hold\'em");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(true);
		primaryStage.setFullScreenExitHint("");
		
		//Action Bar
		
		BorderPane actionBar = setActionBar(players.get(0), WIN_WIDTH, WIN_HEIGHT);
		
		//Main table
		
		StackPane table = new StackPane();
		table.getChildren().addAll(setTable(), setPlayers(players, WIN_WIDTH, WIN_HEIGHT), setCommunity(comm));
		
		root.setBottom(actionBar);
		root.setCenter(table);
		primaryStage.show();
	}
	
	/*
	private void blind(Scene scene, Game game) {
		boolean roundRunning = true;
		while (roundRunning) {
			Player player = game.processTurn();
			VBox seat = (VBox) scene.lookup("#" + game.getCurrentPlayer() + "Seat");
		}
		game.updatePot();
	}
	*/
	
	/**
	 * The geometry of the image of the poker table is formed from the
	 * defined table size constants, returning such to the caller as a
	 * StackPane.
	 * 
	 * @param none.
	 * @return the nodes making up the table
	 */
	private StackPane setTable() {
		Rectangle tableRectRim = new Rectangle(TABLE_WIDTH, (TABLE_WIDTH / TABLE_HEIGHT_RATIO) + (TABLE_RIM * 2));
		tableRectRim.setFill(Color.BLACK);
		
		Ellipse tableLeftRim = new Ellipse(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM, ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM);
		tableLeftRim.setFill(Color.BLACK);
		
		Ellipse tableRightRim = new Ellipse(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM, ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM);
		tableRightRim.setFill(Color.BLACK);
		
		Rectangle tableRect = new Rectangle(TABLE_WIDTH, TABLE_WIDTH / TABLE_HEIGHT_RATIO);
		tableRect.setFill(Color.FORESTGREEN);
		
		Ellipse tableLeft = new Ellipse((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2, (TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2);
		tableLeft.setFill(Color.FORESTGREEN);
		
		Ellipse tableRight = new Ellipse((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2, (TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2);
		tableRight.setFill(Color.FORESTGREEN);
		
		HBox lobesRims = new HBox();
		lobesRims.setAlignment(Pos.CENTER);
		lobesRims.setSpacing(TABLE_WIDTH - (TABLE_WIDTH / TABLE_HEIGHT_RATIO) - (TABLE_RIM * 2));
		lobesRims.getChildren().addAll(tableLeftRim, tableRightRim);
		
		HBox lobes = new HBox();
		lobes.setAlignment(Pos.CENTER);
		lobes.setSpacing(TABLE_WIDTH - (TABLE_WIDTH / TABLE_HEIGHT_RATIO));
		lobes.getChildren().addAll(tableLeft, tableRight);
		
		StackPane table = new StackPane();
		table.setAlignment(Pos.CENTER);
		table.setStyle("-fx-background-color: lightblue;");
		table.getChildren().addAll(tableRectRim, lobesRims, tableRect, lobes);
		
		return table;
	}
	
	/**
	 * The information regarding every player is arrayed around the oval table
	 * by utilizing two coordinate systems. First, the position of each player
	 * is calculated as a scalar around the perimeter of the table with the
	 * user always being at zero at the centre of the bottom of the table. For
	 * players along the top or bottom straightaways they are easily set in 
	 * HBoxes, but those around the hemisphere lobes of the table are put into
	 * a polar coordinate system with each lobe's centre being the origin. 
	 * 
	 * 
	 * @param players the list of players in the game
	 * @param windowW the width of the primary screen
	 * @param windowH the height of the primary screen
	 * @return the stack of all player information nodes
	 */
	private StackPane setPlayers(ArrayList<Player> players, double winWidth, double winHeight) {
		//=====================================================================
		//Coordinate Calculations
		
		//Table Perimeter Coordinates
		double tableBorder = (TABLE_WIDTH * 2.0) + (Math.PI * (TABLE_WIDTH / TABLE_HEIGHT_RATIO));
		double playerSpacing = tableBorder / (double) players.size();
		double bottomEnd = TABLE_WIDTH / 2.0;
		double leftEnd = bottomEnd + Math.PI * ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2.0);
		double topEnd = leftEnd + TABLE_WIDTH;
		double rightEnd = topEnd + Math.PI * ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2.0);
		
		//Lobe Polar Coordinates
		double tableOriginX = winWidth / 2.0;
		double tableOriginY = (winHeight * (7.0 / 8.0)) / 2.0;
		double leftPolarOriginX = tableOriginX - (TABLE_WIDTH / 2.0);
		double leftStartRad = (3.0 * Math.PI) / 2.0;
		double rightPolarOriginX = tableOriginX + (TABLE_WIDTH / 2.0);
		double rightStartRad = Math.PI / 2.0;
		double outsetCircleRadius = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) + PLAYER_OUTSET + TABLE_RIM;
		double insetCircleRadius = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) - PLAYER_INSET;
		
		//=====================================================================
		//Parent Nodes
		
		HBox bottomSeats = new HBox();
		bottomSeats.setAlignment(Pos.CENTER);
		bottomSeats.setSpacing(playerSpacing - SEAT_WIDTH);
		HBox topSeats = new HBox();
		topSeats.setAlignment(Pos.CENTER);
		topSeats.setSpacing(playerSpacing - SEAT_WIDTH);
		
		VBox sWaySeats = new VBox();
		sWaySeats.setAlignment(Pos.CENTER);
		sWaySeats.setSpacing((TABLE_WIDTH / TABLE_HEIGHT_RATIO) + PLAYER_OUTSET + TABLE_RIM);
		sWaySeats.getChildren().addAll(topSeats, bottomSeats);

		Pane lobeSeats = new Pane();
		
		HBox bottomPlaces = new HBox();
		bottomPlaces.setAlignment(Pos.CENTER);
		bottomPlaces.setSpacing(playerSpacing - PLACE_WIDTH);
		HBox topPlaces = new HBox();
		topPlaces.setAlignment(Pos.CENTER);
		topPlaces.setSpacing(playerSpacing - PLACE_WIDTH);
		
		VBox sWayPlaces = new VBox();
		sWayPlaces.setAlignment(Pos.CENTER);
		sWayPlaces.setSpacing((TABLE_WIDTH / TABLE_HEIGHT_RATIO) - (2 * PLAYER_INSET));
		sWayPlaces.getChildren().addAll(topPlaces, bottomPlaces);

		Pane lobePlaces = new Pane();
		
		//=====================================================================
		//Seat and Placement Child Nodes 
		
		int currentPlayer = 0;
		for (Player player : players) {
			double distanceFromUser = currentPlayer * playerSpacing;
			
			//Seat
			Label name = new Label(player.getName());
			Label stack = new Label("Stack: ");
			Label action = new Label("Action: ");
			
			stack.setId(player.getName() + "Stack");
			action.setId(player.getName() + "Action");
			
			VBox seat = new VBox();
			seat.setAlignment(Pos.CENTER);
			seat.setMinWidth(SEAT_WIDTH);
			seat.setMinHeight(SEAT_HEIGHT);
			seat.getChildren().addAll(name, stack, action);
			
			//Placement
			Label bet = new Label("Current Bet: 0.00");
			bet.setId(player.getName() + "Bet");
			
			HBox cardPane = new HBox();
			cardPane.setAlignment(Pos.CENTER);
			cardPane.setSpacing(10);
			
			Image card1;
			Image card2;
			if (player instanceof Human) {
				card1 = new Image("/Images/" + player.getHole().get(0).getSuit() + "/" + player.getHole().get(0).getRank() + ".png", 40, 56, false, false);
				card2 = new Image("/Images/" + player.getHole().get(1).getSuit() + "/" + player.getHole().get(1).getRank() + ".png", 40, 56, false, false);
			}
			else {
				card1 = new Image("/Images/Back.png", 40, 56, false, false);
				card2 = new Image("/Images/Back.png", 40, 56, false, false);
			}
			
			cardPane.getChildren().addAll(new ImageView(card1), new ImageView(card2));
	
			VBox place = new VBox();
			place.setAlignment(Pos.CENTER);
			place.setSpacing(5);
			place.setMinWidth(PLACE_WIDTH);
			place.setMinHeight(PLACE_HEIGHT);
			place.getChildren().addAll(bet, cardPane);
			
			if (distanceFromUser < bottomEnd) {
				bottomSeats.getChildren().add(seat);
				bottomPlaces.getChildren().add(place);
				if ((bottomSeats.getChildren().size() == 2)) { //If there are multiple players on the bottom they need to be reversed
					ObservableList<Node> seatCollec = FXCollections.observableArrayList(bottomSeats.getChildren());
					Collections.swap(seatCollec, 0, 1);
					bottomSeats.getChildren().setAll(seatCollec);
					
					ObservableList<Node> placeCollec = FXCollections.observableArrayList(bottomPlaces.getChildren());
					Collections.swap(placeCollec, 0, 1);
					bottomPlaces.getChildren().setAll(placeCollec);
				}
			}
			else if (distanceFromUser < leftEnd) {
				double phi = leftStartRad - ((distanceFromUser - bottomEnd) / ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2));
				seat.setLayoutX(leftPolarOriginX + (outsetCircleRadius * Math.cos(phi)) - (SEAT_WIDTH / 2));
				seat.setLayoutY(tableOriginY - (outsetCircleRadius * Math.sin(phi)) - (SEAT_HEIGHT / 2));
				place.setLayoutX(leftPolarOriginX + (insetCircleRadius * Math.cos(phi)) - (PLACE_WIDTH / 2));
				place.setLayoutY(tableOriginY - (insetCircleRadius * Math.sin(phi)) - (PLACE_HEIGHT / 2));
				lobeSeats.getChildren().add(seat);
				lobePlaces.getChildren().add(place);
			}
			else if (distanceFromUser < topEnd) {
				topSeats.getChildren().add(seat);
				topPlaces.getChildren().add(place);
			}
			else if (distanceFromUser < rightEnd){
				double phi = rightStartRad - ((distanceFromUser - topEnd) / ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2));
				seat.setLayoutX(rightPolarOriginX + (outsetCircleRadius * Math.cos(phi)) - (SEAT_WIDTH / 2));
				seat.setLayoutY(tableOriginY - (outsetCircleRadius * Math.sin(phi)) - (SEAT_HEIGHT / 2));
				place.setLayoutX(rightPolarOriginX + (insetCircleRadius * Math.cos(phi)) - (PLACE_WIDTH / 2));
				place.setLayoutY(tableOriginY - (insetCircleRadius * Math.sin(phi)) - (PLACE_HEIGHT / 2));
				lobeSeats.getChildren().add(seat);
				lobePlaces.getChildren().add(place);
			}
			else {
				bottomSeats.getChildren().add(seat);
				bottomPlaces.getChildren().add(place);
			}
			currentPlayer++;
		}

		if (topSeats.getChildren().size() == 0) //A dummy seat is added to the top seats to make sure the bottom is offset
			topSeats.getChildren().add(new Label("\n\n\n"));
		
		StackPane playerInfo = new StackPane();
		playerInfo.getChildren().addAll(sWaySeats, lobeSeats, sWayPlaces, lobePlaces);
		return playerInfo;
	}
	
	public BorderPane setActionBar(Player user, double winWidth, double winHeight) {
		BorderPane actionBar = new BorderPane();
		actionBar.setPrefSize(winWidth, winHeight / 10.0);
		actionBar.setStyle("-fx-border-style: solid inside;"
		        + "-fx-border-width: 5;" + "-fx-border-color: #4B0905;"
				+ "-fx-background-color: maroon");
		
		//=====================================================================
		//Action Buttons
		
		StackPane actions = new StackPane();
		
		//Main buttons
		HBox buttons = new HBox();
		
			Button fold = new Button("Fold");
			fold.setPrefSize(600, winHeight / 10 - 10);
			fold.getStyleClass().add("button-large");

			Button raise = new Button("Raise");
			raise.setPrefSize(600, winHeight / 10 - 10);
			raise.getStyleClass().add("button-large");
			
			Button call = new Button("Call");
			call.setPrefSize(600, winHeight / 10 - 10);
			call.getStyleClass().add("button-large");
		
		buttons.getChildren().addAll(fold, raise, call);
		
		//Input for raise
		HBox raiseInput = new HBox();
		raiseInput.setAlignment(Pos.CENTER);
		raiseInput.setSpacing(10);
		
			Label raiseFieldLabel = new Label("Enter your wager:");
			raiseFieldLabel.getStyleClass().add("bar-label");
			TextField raiseField = new TextField();
			raiseField.setMaxWidth(150);
			
			HBox raiseFieldButtons = new HBox();
			raiseFieldButtons.setAlignment(Pos.CENTER);
			raiseFieldButtons.setSpacing(5);
			
			Button raiseFieldConfirm = new Button("Enter");
			Button raiseFieldCancel = new Button("Cancel");
			
			raiseFieldButtons.getChildren().addAll(raiseFieldConfirm, raiseFieldCancel);
		
		raiseInput.getChildren().addAll(raiseFieldLabel, raiseField, raiseFieldButtons);
		raiseInput.setVisible(false);
		
		actions.getChildren().addAll(buttons, raiseInput);
		
		//=====================================================================
		//Settings Buttons
		
		VBox settingButtons = new VBox();
		settingButtons.setAlignment(Pos.CENTER);
		
		Button escapeClause = new Button("QUIT");
		
		settingButtons.getChildren().addAll(escapeClause);
		
		//=====================================================================
		//Event Handlers
		
		raise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				raiseInput.setVisible(true);
			}
		});
		
		raiseFieldCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				raiseInput.setVisible(false);
			}
		});
		
		escapeClause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		//=====================================================================
		//Setting to BorderPane to return
		
		actionBar.setLeft(actions);
		actionBar.setRight(settingButtons);
		return actionBar;
	}
	
	private StackPane setCommunity(ArrayList<Card> comm) {
		StackPane commFull = new StackPane();
			
			VBox community = new VBox();
			community.setAlignment(Pos.CENTER);	
			community.setSpacing(20);
			
				HBox flop = new HBox();
				flop.setAlignment(Pos.CENTER);	
				flop.setSpacing(5);
				for (int index = 0; index < 3; index++) {
					Image cardImage = new Image("/Images/" + comm.get(index).getSuit() + "/" + comm.get(index).getRank() + ".png");
					ImageView cardView = new ImageView(cardImage);
					cardView.setId("commFront" + index);
					flop.getChildren().add(cardView);
				}
				
				HBox streets = new HBox();
				streets.setAlignment(Pos.CENTER);	
				streets.setSpacing(20);
				for (int index = 3; index < 5; index++) {
					Image cardImage = new Image("/Images/" + comm.get(index).getSuit() + "/" + comm.get(index).getRank() + ".png");
					ImageView cardView = new ImageView(cardImage);
					cardView.setId("commFront" + index);
					streets.getChildren().add(cardView);
				}
				
			community.getChildren().addAll(flop, streets);
			
			VBox communityCover = new VBox();
			communityCover.setAlignment(Pos.CENTER);	
			communityCover.setSpacing(20);
				
				HBox flopCover = new HBox();
				flopCover.setAlignment(Pos.CENTER);	
				flopCover.setSpacing(5);
				Image backImage = new Image("/Images/Back.png");
				for (int i = 0; i < 3; i++) {		
					ImageView backView = new ImageView(backImage);
					backView.setId("commBack" + i);
					flopCover.getChildren().add(backView);
				}
				
				HBox streetsCover = new HBox();
				streetsCover.setAlignment(Pos.CENTER);	
				streetsCover.setSpacing(20);
				for (int i = 3; i < 5; i++) {
					ImageView backView = new ImageView(backImage);
					backView.setId("commBack" + i);
					streetsCover.getChildren().add(backView);
				}
				
			communityCover.getChildren().addAll(flopCover, streetsCover);
			
		commFull.getChildren().addAll(community, communityCover);
		
		return commFull;
	}
	
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
