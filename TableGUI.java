import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import javafx.scene.text.Text;

/**
 * 
 * @author Adam Hiles
 * @version 03/03/18
 */
public class TableGUI extends Application {
	private final static double tableWidth = 720; //Referring to the length of the straightway
	private final static double tableRatio = 1.25;
	private final static double tableRim = 20;
	
	private final static double playerOutset = 60; //The distance of player information from the table
	private final static double playerInset = 60; //The distance of player bets and cards? inside the table
	
	private final static double seatWidth = 50.0;
	private final static double seatHeight = 60.0;
	
	private final static double placeWidth = 100.0;
	private final static double placeHeight = 20.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		GUITest.testDeck.shuffle();
		ArrayList<Card> comm = GUITest.generateComm();
		ArrayList<Player> players = GUITest.generatePlayers();
		
		
		BorderPane root = new BorderPane();
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double windowW = primaryScreenBounds.getWidth();
		double windowH = primaryScreenBounds.getHeight();
		
		Scene scene = new Scene(root, windowW, windowH);
		primaryStage.setTitle("Texas Hold\'em");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		
		//Action Bar
		
		HBox actionBar = new HBox();
		actionBar.setPrefSize(windowW, windowH / 6.0);
		actionBar.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 5;");

		//Community cards
		
		VBox community = new VBox();
		community.setAlignment(Pos.CENTER);	
		community.setSpacing(5);
		
		HBox flop = new HBox();
		flop.setAlignment(Pos.CENTER);	
		flop.setSpacing(5);
		for (int index = 0; index < 3; index++) {
			Image cardImage = new Image("/Images/" + comm.get(index).getSuit() + "/" + comm.get(index).getRank() + ".png");
			flop.getChildren().add(new ImageView(cardImage));
		}
		
		HBox streets = new HBox();
		streets.setAlignment(Pos.CENTER);	
		streets.setSpacing(10);
		for (int index = 3; index < 5; index++) {
			Image cardImage = new Image("/Images/" + comm.get(index).getSuit() + "/" + comm.get(index).getRank() + ".png");
			flop.getChildren().add(new ImageView(cardImage));
		}
		
		community.getChildren().addAll(flop, streets);
		
		StackPane table = generateTable();
		StackPane playerInfo = seatPlayers(players, windowW, windowH);
		table.getChildren().addAll(community, playerInfo);
		
		root.setBottom(actionBar);
		root.setCenter(table);
		primaryStage.show();
	}
	
	/**
	 * The geometry of the image of the poker table is formed from the
	 * defined table size constants, returning such to the caller as a
	 * StackPane.
	 * 
	 * @param none.
	 * @return the nodes making up the table
	 */
	private static StackPane generateTable() {
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
	private static StackPane seatPlayers(ArrayList<Player> players, double windowW, double windowH) {
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
		double tableOriginX = windowW / 2.0;
		double tableOriginY = (windowH * (5.0 / 6.0)) / 2.0;
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
			Label stack = new Label("Stack: ");
			Label action = new Label("Action: ");
			
			VBox seat = new VBox();
			seat.setAlignment(Pos.CENTER);
			seat.setMinWidth(seatWidth);
			seat.setMinHeight(seatHeight);
			seat.getChildren().addAll(name, stack, action);
			
			//Placement
			Label bet = new Label("Current Bet: 0.00");
			
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
