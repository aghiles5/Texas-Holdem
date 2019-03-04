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
	private final static double tableWidth = 720; //Referring to the length of the straightaway
	private final static double tableRatio = 1.25;
	private final static double tableRim = 20;
	
	private final static double playerOutset = 60; //The distance of player information from the table
	private final static double playerInset = 60; //The distance of player bets and cards? inside the table
	
	private final static double seatWidth = 50.0;
	private final static double seatHeight = 60.0;
	
	private final static double placeWidth = 150.0;
	private final static double placeHeight = 20.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		GUITest.testDeck.shuffle();
		ArrayList<Card> comm = GUITest.generateComm();
		ArrayList<Player> players = GUITest.generatePlayers(8);
		
		BorderPane root = new BorderPane();
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double winWidth = primaryScreenBounds.getWidth();
		double winHeight = primaryScreenBounds.getHeight();
		
		Scene scene = new Scene(root, winWidth, winHeight);
		primaryStage.setTitle("Texas Hold\'em");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitHint("");
		
		//Action Bar
		
		BorderPane actionBar = setActionBar(players.get(0), winWidth, winHeight);
		
		//Main table
		
		StackPane table = new StackPane();
		table.getChildren().addAll(setTable(), setPlayers(players, winWidth, winHeight), setCommunity(comm));
		
		root.setBottom(actionBar);
		root.setCenter(table);
		primaryStage.show();
		
		Button flipButton = (Button) scene.lookup("#flipButton");
		
		flipButton.setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent event) {
				if (i < 5) {
					revealCard((ImageView) scene.lookup("#commBack" + i), (ImageView) scene.lookup("#commFront" + i));
					i++;
				}
			}
		});
	}
	
	/**
	 * The geometry of the image of the poker table is formed from the
	 * defined table size constants, returning such to the caller as a
	 * StackPane.
	 * 
	 * @param none.
	 * @return the nodes making up the table
	 */
	private static StackPane setTable() {
		Rectangle tableRectRim = new Rectangle(tableWidth, (tableWidth / tableRatio) + (tableRim * 2));
		tableRectRim.setFill(Color.BLACK);
		
		Ellipse tableLeftRim = new Ellipse(((tableWidth / tableRatio) / 2) + tableRim, ((tableWidth / tableRatio) / 2) + tableRim);
		tableLeftRim.setFill(Color.BLACK);
		
		Ellipse tableRightRim = new Ellipse(((tableWidth / tableRatio) / 2) + tableRim, ((tableWidth / tableRatio) / 2) + tableRim);
		tableRightRim.setFill(Color.BLACK);
		
		Rectangle tableRect = new Rectangle(tableWidth, tableWidth / tableRatio);
		tableRect.setFill(Color.FORESTGREEN);
		
		Ellipse tableLeft = new Ellipse((tableWidth / tableRatio) / 2, (tableWidth / tableRatio) / 2);
		tableLeft.setFill(Color.FORESTGREEN);
		
		Ellipse tableRight = new Ellipse((tableWidth / tableRatio) / 2, (tableWidth / tableRatio) / 2);
		tableRight.setFill(Color.FORESTGREEN);
		
		HBox lobesRims = new HBox();
		lobesRims.setAlignment(Pos.CENTER);
		lobesRims.setSpacing(tableWidth - (tableWidth / tableRatio) - (tableRim * 2));
		lobesRims.getChildren().addAll(tableLeftRim, tableRightRim);
		
		HBox lobes = new HBox();
		lobes.setAlignment(Pos.CENTER);
		lobes.setSpacing(tableWidth - (tableWidth / tableRatio));
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
	private static StackPane setPlayers(ArrayList<Player> players, double winWidth, double winHeight) {
		//=====================================================================
		//Coordinate Calculations
		
		//Table Perimeter Coordinates
		double tableBorder = (tableWidth * 2.0) + (Math.PI * (tableWidth / tableRatio));
		double playerSpacing = tableBorder / (double) players.size();
		double bottomEnd = tableWidth / 2.0;
		double leftEnd = bottomEnd + Math.PI * ((tableWidth / tableRatio) / 2.0);
		double topEnd = leftEnd + tableWidth;
		double rightEnd = topEnd + Math.PI * ((tableWidth / tableRatio) / 2.0);
		
		//Lobe Polar Coordinates
		double tableOriginX = winWidth / 2.0;
		double tableOriginY = (winHeight * (5.0 / 6.0)) / 2.0;
		double leftPolarOriginX = tableOriginX - (tableWidth / 2.0);
		double leftStartRad = (3.0 * Math.PI) / 2.0;
		double rightPolarOriginX = tableOriginX + (tableWidth / 2.0);
		double rightStartRad = Math.PI / 2.0;
		double outsetCircleRadius = ((tableWidth / tableRatio)  / 2.0) + playerOutset + tableRim;
		double insetCircleRadius = ((tableWidth / tableRatio)  / 2.0) - playerInset;
		
		//=====================================================================
		//Parent Nodes
		
		HBox bottomSeats = new HBox();
		bottomSeats.setAlignment(Pos.CENTER);
		bottomSeats.setSpacing(playerSpacing - seatWidth);
		HBox topSeats = new HBox();
		topSeats.setAlignment(Pos.CENTER);
		topSeats.setSpacing(playerSpacing - seatWidth);
		
		VBox sWaySeats = new VBox();
		sWaySeats.setAlignment(Pos.CENTER);
		sWaySeats.setSpacing((tableWidth / tableRatio) + playerOutset + tableRim);
		sWaySeats.getChildren().addAll(topSeats, bottomSeats);

		Pane lobeSeats = new Pane();
		
		HBox bottomPlaces = new HBox();
		bottomPlaces.setAlignment(Pos.CENTER);
		bottomPlaces.setSpacing(playerSpacing - placeWidth);
		HBox topPlaces = new HBox();
		topPlaces.setAlignment(Pos.CENTER);
		topPlaces.setSpacing(playerSpacing - placeWidth);
		
		VBox sWayPlaces = new VBox();
		sWayPlaces.setAlignment(Pos.CENTER);
		sWayPlaces.setSpacing((tableWidth / tableRatio) - playerInset);
		sWayPlaces.getChildren().addAll(topPlaces, bottomPlaces);

		Pane lobePlaces = new Pane();
		
		//=====================================================================
		//Seat and Placement Child Nodes 
		
		int currentPlayer = 0;
		for (Player player : players) {
			double distanceFromUser = currentPlayer * playerSpacing;
			
			//Seat
			Label name = new Label(player.getName());
			Label stack = new Label("");
			Label action = new Label("");
			if (currentPlayer != 0) {
				stack.setText("Stack: ");
				action.setText("Action: ");
			}
			
			VBox seat = new VBox();
			seat.setAlignment(Pos.CENTER);
			seat.setMinWidth(seatWidth);
			seat.setMinHeight(seatHeight);
			seat.getChildren().addAll(name, stack, action);
			
			//Placement
			Label bet = new Label("");
			if (currentPlayer != 0)
				bet.setText("Current Bet: 0.00");
	
			VBox place = new VBox();
			place.setAlignment(Pos.CENTER);
			place.setMinWidth(placeWidth);
			place.setMinHeight(placeHeight);
			place.getChildren().addAll(bet);
			
			
			
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
				double phi = leftStartRad - ((distanceFromUser - bottomEnd) / ((tableWidth / tableRatio) / 2));
				seat.setLayoutX(leftPolarOriginX + (outsetCircleRadius * Math.cos(phi)) - (seatWidth / 2));
				seat.setLayoutY(tableOriginY - (outsetCircleRadius * Math.sin(phi)) - (seatHeight / 2));
				place.setLayoutX(leftPolarOriginX + (insetCircleRadius * Math.cos(phi)) - (placeWidth / 2));
				place.setLayoutY(tableOriginY - (insetCircleRadius * Math.sin(phi)) - (placeHeight / 2));
				lobeSeats.getChildren().add(seat);
				lobePlaces.getChildren().add(place);
			}
			else if (distanceFromUser < topEnd) {
				topSeats.getChildren().add(seat);
				topPlaces.getChildren().add(place);
			}
			else if (distanceFromUser < rightEnd){
				double phi = rightStartRad - ((distanceFromUser - topEnd) / ((tableWidth / tableRatio) / 2));
				seat.setLayoutX(rightPolarOriginX + (outsetCircleRadius * Math.cos(phi)) - (seatWidth / 2));
				seat.setLayoutY(tableOriginY - (outsetCircleRadius * Math.sin(phi)) - (seatHeight / 2));
				place.setLayoutX(rightPolarOriginX + (insetCircleRadius * Math.cos(phi)) - (placeWidth / 2));
				place.setLayoutY(tableOriginY - (insetCircleRadius * Math.sin(phi)) - (placeHeight / 2));
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
	
	public static BorderPane setActionBar(Player user, double winWidth, double winHeight) {
		BorderPane actionBar = new BorderPane();
		actionBar.setPrefSize(winWidth, winHeight / 6.0);
		actionBar.setStyle("-fx-border-style: solid inside;"
		        + "-fx-border-width: 5;" + "-fx-border-color: #4B0905;"
				+ "-fx-background-color: maroon");
		
		//=====================================================================
		//Central Money Information/Cards
		
		HBox centrePane = new HBox();
		centrePane.setAlignment(Pos.CENTER);
		centrePane.setSpacing(100);
		
			VBox money = new VBox();
			money.setAlignment(Pos.CENTER);
			money.setSpacing(10);
			
				Label stack = new Label("Your Stack: ");
				stack.setStyle("-fx-text-fill: goldenrod;");
				Label bet = new Label("Your Current Bet: 0.00");
				bet.setStyle("-fx-text-fill: goldenrod;");
			
			money.getChildren().addAll(stack, bet);
			
			VBox hole = new VBox();
			hole.setAlignment(Pos.CENTER);
			hole.setSpacing(10);
			
				Label holeLabel = new Label("Your hole cards:");
				holeLabel.setStyle("-fx-text-fill: goldenrod;");
				
				HBox holeCards = new HBox();
				holeCards.setAlignment(Pos.CENTER);
				holeCards.setSpacing(20);
				
				for (Card card : user.getHole()) {
					Image cardImage = new Image("/Images/" + card.getSuit() + "/" + card.getRank() + ".png");
					holeCards.getChildren().add(new ImageView(cardImage));
				}
			
			hole.getChildren().addAll(holeLabel, holeCards);
		
		centrePane.getChildren().addAll(hole, money);
		
		//=====================================================================
		//Action Buttons
		
		StackPane actions = new StackPane();
		
		//Main buttons
		HBox buttons = new HBox();
		
			Button fold = new Button("Fold");
			fold.setPrefSize(200, winHeight / 6 - 10);
			fold.setStyle("-fx-font-size: 20;");
			
			Button raise = new Button("Raise");
			raise.setPrefSize(200, winHeight / 6 - 10);
			raise.setStyle("-fx-font-size: 20;");
			
			Button call = new Button("Call");
			call.setPrefSize(200, winHeight / 6 - 10);
			call.setStyle("-fx-font-size: 20;");
		
		buttons.getChildren().addAll(fold, raise, call);
		
		//Input for raise
		VBox raiseInput = new VBox();
		raiseInput.setAlignment(Pos.CENTER);
		raiseInput.setSpacing(20);
		
			Label raiseFieldLabel = new Label("Enter your wager:");
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
		
		//Grey pane and effect
		Pane greyPane = new Pane();
		greyPane.setVisible(false);
		
		ColorAdjust greyOut = new ColorAdjust();
		greyOut.setSaturation(-1.0);
		
		actions.getChildren().addAll(buttons, raiseInput, greyPane);
		
		//=====================================================================
		//Settings Buttons
		
		VBox settingButtons = new VBox();
		settingButtons.setAlignment(Pos.CENTER);
		
		Button escapeClause = new Button("QUIT");
		
		Button greyTest = new Button("GREY TEST");
		
		Button flipTest = new Button("FLIP TEST");
		flipTest.setId("flipButton");
		
		settingButtons.getChildren().addAll(escapeClause, greyTest, flipTest);
		
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
		
		greyTest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (greyPane.isVisible()) {
					greyPane.setVisible(false);
					actions.setEffect(null);
				}
				else {
					if (raiseInput.isVisible())
						raiseInput.setVisible(false);
					greyPane.setVisible(true);
					actions.setEffect(greyOut);
				}
			}
		});

		//=====================================================================
		//Setting to BorderPane to return
		
		actionBar.setLeft(actions);
		actionBar.setCenter(centrePane);
		actionBar.setRight(settingButtons);
		return actionBar;
	}
	
	private static StackPane setCommunity(ArrayList<Card> comm) {
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
	
	private static void revealCard(ImageView cardBack, ImageView cardFront) {
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
