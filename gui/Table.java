package gui;
import java.util.ArrayList;
import java.util.Collections;

import cards.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import players.Player;

/**
 * All aspects relating to the poker table image in the GUI are created here.
 * 
 * @author Adam Hiles
 * @version 03/28/19
 */
public class Table {
	private final double WIN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	private final double WIN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	
	private final double TABLE_TO_SCREEN_RATIO = 8.0 / 3.5;
	private final double TABLE_HEIGHT_RATIO = 1.25;
	private final double TABLE_OUTER_RIM_RATIO = 1.0 / 36.0;
	private final double TABLE_OUTSET_RATIO = 1.0 / 10.0;
	private final double TABLE_INSET_RATIO = 1.0 / 10.0;
	
	private final double TABLE_WIDTH = WIN_WIDTH / TABLE_TO_SCREEN_RATIO; //Referring to the length of the straightaway
	private final double TABLE_RIM = TABLE_WIDTH * TABLE_OUTER_RIM_RATIO;
	
	private final double PLAYER_OUTSET = TABLE_WIDTH * TABLE_OUTSET_RATIO; //The distance of player information from the table
	private final double PLAYER_INSET = TABLE_WIDTH * TABLE_INSET_RATIO; //The distance of player bets and cards? inside the table
	
	private final double SEAT_WIDTH = 160.0;
	private final double SEAT_HEIGHT = 60.0;
	
	private final double PLACE_WIDTH = 200.0;
	private final double PLACE_HEIGHT = 90.0;
	
	private StackPane tablePane = new StackPane();
	
	/**
	 * Construction creates the three elements of the table and combines them
	 * into the root StackPane.
	 * 
	 * @param players the Game's player list
	 * @param comm the pre-drawn community card list
	 */
	public Table(ArrayList<Player> players, ArrayList<Card> comm) {
		tablePane.getChildren().addAll(setTable(), setPlayers(players), setTableCentre(comm));
	}
	
	/**
	 * The table's root StackPane is returned to the caller to be put into the
	 * scene tree.
	 * 
	 * @return the root table node
	 */
	public StackPane getTablePane() {
		return tablePane;
	}
	
	/**
	 * The geometry of the image of the poker table is formed from the
	 * defined table size constants, returning such to the caller as a
	 * StackPane.
	 * 
	 * @param none.
	 * @return the nodes making up the table
	 */
	private StackPane setTable() {
		
		//The table's black rubber rim geometry
		ImagePattern rubberTexture = new ImagePattern(new Image("gui/textures/rubber.png"),
				0, 0, 128, 128, false);
		Path rim = new Path(new MoveTo(0, 0),
				new HLineTo(TABLE_WIDTH),
				new ArcTo(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM,
						((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM, 0,
						TABLE_WIDTH,
						(TABLE_WIDTH / TABLE_HEIGHT_RATIO) + (2 * TABLE_RIM),
						false, true),
				new HLineTo(0),
				new ArcTo(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM,
						((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) + TABLE_RIM, 
						0, 0, 0,
						false, true),
				new ClosePath()
				);
		rim.setFill(rubberTexture);
		
		//The table's wooden panel
		ImagePattern woodTexture = new ImagePattern(new Image("gui/textures/wood.png"),
				0, 0, 128, 128, false);
		Path wood = new Path(new MoveTo(0, 0),
				new HLineTo(TABLE_WIDTH),
				new ArcTo(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2),
						((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2), 0,
						TABLE_WIDTH,
						(TABLE_WIDTH / TABLE_HEIGHT_RATIO),
						false, true),
				new HLineTo(0),
				new ArcTo(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2),
						((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2), 
						0, 0, 0,
						false, true),
				new ClosePath()
				);
		wood.setFill(woodTexture);
		
		//The table's green felt geometry
		ImagePattern feltTexture = new ImagePattern(new Image("gui/textures/felt.png"),
				0, 0, 128, 128, false);
		Path felt = new Path(new MoveTo(0, 0),
				new HLineTo(TABLE_WIDTH - (2 * (PLACE_HEIGHT))),
				new ArcTo(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) - 3 * PLACE_HEIGHT,
						((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) - 3 * PLACE_HEIGHT, 0,
						TABLE_WIDTH - (2 * (PLACE_HEIGHT)),
						(TABLE_WIDTH / TABLE_HEIGHT_RATIO) - (3 * PLACE_HEIGHT),
						false, true),
				new HLineTo(0),
				new ArcTo((((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) - 3 * PLACE_HEIGHT),
						(((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2) - 3 * PLACE_HEIGHT), 
						0, 0, 0,
						false, true),
				new ClosePath()
				);
		felt.setFill(feltTexture);
		
		//The background for the table to be placed on
		StackPane table = new StackPane();
		table.setAlignment(Pos.CENTER);
		table.getStyleClass().add("flooring");
		//table.getChildren().addAll(lobesRims, tableRectRim, tableRect, lobes);
		table.getChildren().addAll(rim, wood, felt);
		
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
	 * @return the stack of all player information nodes
	 */
	private StackPane setPlayers(ArrayList<Player> players) {
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
		double tableOriginX = WIN_WIDTH / 2.0;
		double tableOriginY = (WIN_HEIGHT * (9.0 / 10.0)) / 2.0;
		double leftPolarOriginX = tableOriginX - (TABLE_WIDTH / 2.0);
		double leftStartRad = (3.0 * Math.PI) / 2.0;
		double rightPolarOriginX = tableOriginX + (TABLE_WIDTH / 2.0);
		double rightStartRad = Math.PI / 2.0;
		double outsetEllipseRadiusY = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) + (PLAYER_OUTSET / 2) + (SEAT_HEIGHT / 2) + TABLE_RIM;
		double outsetEllipseRadiusX = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) + (PLAYER_OUTSET / 2) + (SEAT_WIDTH / 3) + TABLE_RIM;
		double insetEllipseRadiusY = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) - PLAYER_INSET;
		double insetEllipseRadiusX = ((TABLE_WIDTH / TABLE_HEIGHT_RATIO)  / 2.0) - 1.5*PLAYER_INSET;
		
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
		sWaySeats.setSpacing(2 * outsetEllipseRadiusY - SEAT_HEIGHT);
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
		sWayPlaces.setSpacing(2 * insetEllipseRadiusY - PLACE_HEIGHT);
		sWayPlaces.getChildren().addAll(topPlaces, bottomPlaces);

		Pane lobePlaces = new Pane();
		
		//=====================================================================
		//Seat and Placement Child Nodes 
		
		int currentPlayer = 0;
		for (Player player : players) {
			double distanceFromUser = currentPlayer * playerSpacing;
			
			//Seat
			Label name = new Label(player.getName());
			Label stack = new Label("Stack: " + (new MoneyFormatter(player.getStack())).toString());
			Label action = new Label(" ");
			
			name.setId(player.getName() + "Name");
			stack.setId(player.getName() + "Stack");
			action.setId(player.getName() + "Action");
			
			VBox seat = new VBox();
			seat.setAlignment(Pos.CENTER);
			seat.setMinWidth(SEAT_WIDTH);
			seat.setMinHeight(SEAT_HEIGHT);
			seat.getChildren().addAll(name, stack, action);
			
			//Placement
			Label bet = new Label("Current Bet: $0");
			bet.setId(player.getName() + "Bet");
			
			//The pane containing the player's hole cards and their backs
			StackPane cardPane = new StackPane();
			cardPane.setAlignment(Pos.CENTER);
			
				HBox cardFrontPane = new HBox();
				cardFrontPane.setAlignment(Pos.CENTER);
				cardFrontPane.setSpacing(10);
				
				HBox cardBackPane = new HBox();
				cardBackPane.setAlignment(Pos.CENTER);
				cardBackPane.setSpacing(10);
				
					Image card1 = new Image("/Images/" + player.getHole().get(0).getSuit() + "/" + player.getHole().get(0).getRank() + ".png");
					Image card2 = new Image("/Images/" + player.getHole().get(1).getSuit() + "/" + player.getHole().get(1).getRank() + ".png");
					
					ImageView card1Front = new ImageView(card1);
					ImageView card2Front = new ImageView(card2);
					card1Front.setScaleX(0);
					card2Front.setScaleX(0);
					card1Front.setVisible(false);
					card2Front.setVisible(false);
					
					card1Front.setId(player.getName() + "Card1");
					card2Front.setId(player.getName() + "Card2");
					
				cardFrontPane.getChildren().addAll(card1Front, card2Front);
					
					Image cardBack = new Image("/Images/Back.png");
					ImageView card1Back = new ImageView(cardBack);
					ImageView card2Back = new ImageView(cardBack);
					card1Back.setVisible(false);
					card2Back.setVisible(false);
					card1Back.setId(player.getName() + "Card1Back");
					card2Back.setId(player.getName() + "Card2Back");
					
				cardBackPane.getChildren().addAll(card1Back, card2Back);
				
			cardPane.getChildren().addAll(cardFrontPane, cardBackPane);
	
			Ellipse chip = new Ellipse(12, 12);
			chip.setId(player.getName() + "Chip");
			chip.setVisible(false);
			
			VBox place = new VBox();
			place.setAlignment(Pos.CENTER);
			place.setSpacing(5);
			place.setMinWidth(PLACE_WIDTH);
			place.setMinHeight(PLACE_HEIGHT);
			place.getChildren().addAll(bet, cardPane, chip);
			
			//Based on the distance from the origin player each player's seat and place is set in the appropriate position
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
				seat.setLayoutX(leftPolarOriginX + (outsetEllipseRadiusX * Math.cos(phi)) - (SEAT_WIDTH / 2));
				seat.setLayoutY(tableOriginY - (outsetEllipseRadiusY * Math.sin(phi)) - (SEAT_HEIGHT / 2));
				place.setLayoutX(leftPolarOriginX + (insetEllipseRadiusX * Math.cos(phi)) - (PLACE_WIDTH / 2));
				place.setLayoutY(tableOriginY - (insetEllipseRadiusY * Math.sin(phi)) - (PLACE_HEIGHT / 2));
				lobeSeats.getChildren().add(seat);
				lobePlaces.getChildren().add(place);
			}
			else if (distanceFromUser < topEnd) {
				topSeats.getChildren().add(seat);
				topPlaces.getChildren().add(place);
			}
			else if (distanceFromUser < rightEnd){
				double phi = rightStartRad - ((distanceFromUser - topEnd) / ((TABLE_WIDTH / TABLE_HEIGHT_RATIO) / 2));
				seat.setLayoutX(rightPolarOriginX + (outsetEllipseRadiusX * Math.cos(phi)) - (SEAT_WIDTH / 2));
				seat.setLayoutY(tableOriginY - (outsetEllipseRadiusY * Math.sin(phi)) - (SEAT_HEIGHT / 2));
				place.setLayoutX(rightPolarOriginX + (insetEllipseRadiusX * Math.cos(phi)) - (PLACE_WIDTH / 2));
				place.setLayoutY(tableOriginY - (insetEllipseRadiusY * Math.sin(phi)) - (PLACE_HEIGHT / 2));
				lobeSeats.getChildren().add(seat);
				lobePlaces.getChildren().add(place);
			}
			else {
				bottomSeats.getChildren().add(seat);
				bottomPlaces.getChildren().add(place);
			}
			currentPlayer++;
		}

		if (topSeats.getChildren().size() == 0) {//A dummy seat is added to the top seats to make sure the bottom is offset
			topSeats.getChildren().add(new Label("\n\n\n"));
			topPlaces.getChildren().add(new Label("\n\n\n\n"));
		}
		
		StackPane playerInfo = new StackPane();
		playerInfo.getChildren().addAll(sWaySeats, lobeSeats, sWayPlaces, lobePlaces);
		return playerInfo;
	}
	
	/**
	 * The nodes at the centre of the table will consist of the community card
	 * depictions, their backs, and the pot and highest bet labels. These nodes
	 * are appropriately placed and passed back to the caller to be 
	 * superimposed on the table geometry and player information.
	 * 
	 * @param comm the Game's community cards
	 * @return the StackPane of centre table nodes
	 */
	private StackPane setTableCentre(ArrayList<Card> comm) {
		StackPane tableCentre = new StackPane();
			HBox centreProps = new HBox();
			centreProps.setAlignment(Pos.CENTER);
			centreProps.setSpacing(200);
			
				//=============================================================
				// Deck Image
			
				StackPane deck = new StackPane();
				deck.setAlignment(Pos.CENTER);
					
					ImageView deckA = new ImageView(new Image("/Images/Deck.png"));
					ImageView deckB = new ImageView(new Image("/Images/Deck.png"));
					ImageView drawCard = new ImageView(new Image("/Images/Back.png"));
					ImageView returnCard = new ImageView(new Image("/Images/Back.png"));
					deckA.setId("deckA");
					deckB.setId("deckB");
					drawCard.setId("drawCard");
					returnCard.setId("returnCard");
				
				deck.getChildren().addAll(returnCard, deckA, deckB, drawCard);
			
				//=============================================================
				// Community Cards
			
				StackPane commFull = new StackPane();
				commFull.setAlignment(Pos.CENTER);
					
					// Card Faces
				
					VBox community = new VBox();
					community.setAlignment(Pos.CENTER);	
					community.setSpacing(20);
					
						HBox flop = new HBox();
						flop.setAlignment(Pos.CENTER);	
						flop.setSpacing(5);
						for (int index = 0; index < 3; index++) {
							Image cardImage = new Image("/Images/Back.png");
							ImageView cardView = new ImageView(cardImage);
							cardView.setScaleX(0);
							cardView.setId("commFront" + index);
							cardView.setVisible(false);
							flop.getChildren().add(cardView);
						}
						
						HBox streets = new HBox();
						streets.setAlignment(Pos.CENTER);	
						streets.setSpacing(20);
						for (int index = 3; index < 5; index++) {
							Image cardImage = new Image("/Images/Back.png");
							ImageView cardView = new ImageView(cardImage);
							cardView.setScaleX(0);
							cardView.setId("commFront" + index);
							cardView.setVisible(false);
							streets.getChildren().add(cardView);
						}
						
					community.getChildren().addAll(flop, streets);
					
					// Card Backs
					
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
							backView.setVisible(false);
							flopCover.getChildren().add(backView);
						}
						
						HBox streetsCover = new HBox();
						streetsCover.setAlignment(Pos.CENTER);	
						streetsCover.setSpacing(20);
						for (int i = 3; i < 5; i++) {
							ImageView backView = new ImageView(backImage);
							backView.setId("commBack" + i);
							backView.setVisible(false);
							streetsCover.getChildren().add(backView);
						}
						
					communityCover.getChildren().addAll(flopCover, streetsCover);
					
				commFull.getChildren().addAll(community, communityCover);
				
				//=============================================================
				// Centre Money Indicators
				
				VBox dealerMoney = new VBox();
				dealerMoney.setAlignment(Pos.CENTER);
				dealerMoney.setSpacing(5);
				dealerMoney.setMinWidth(300);
				
					Label pot = new Label("Pot: $0");
					pot.setId("pot");
					pot.setMaxWidth(300);
					pot.setAlignment(Pos.CENTER);
					Label wager = new Label("Highest Wager: $0");
					wager.setId("wager");
					wager.setMaxWidth(300);
					wager.setAlignment(Pos.CENTER);
					
				dealerMoney.getChildren().addAll(pot, wager);
				
				//=============================================================
				// Game Over Notification
				
				HBox endGameNotif = new HBox();
				endGameNotif.setMaxSize(WIN_WIDTH / 4, WIN_HEIGHT / 4);
				endGameNotif.setSpacing(50);
				endGameNotif.setAlignment(Pos.CENTER);
				endGameNotif.setVisible(false);
				endGameNotif.setId("endGameNotif");
				endGameNotif.getStyleClass().add("custom-popup");
				
					Label endGameMsg = new Label("Game Over");
					endGameMsg.getStyleClass().add("bar-label");
					endGameMsg.setStyle("-fx-font-size: 36;");
					endGameMsg.setId("endGameMsg");
					
					Button playAgain = new Button("Play Again");
					playAgain.setId("playAgain");
					
				endGameNotif.getChildren().addAll(endGameMsg, playAgain);
					
			centreProps.getChildren().addAll(commFull, dealerMoney);
			
		tableCentre.getChildren().addAll(centreProps, deck, endGameNotif);
		
		return tableCentre;
	}
}
