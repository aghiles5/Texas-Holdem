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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TableGUI extends Application {
	private final static double tableWidth = 720; //Referring to the length of the straightway
	private final static double tableRatio = 1.25;
	private final static double tableRim = 20;
	private final static double playerOutset = 60; //The distance of player information from the table
	private final static double playerInset = 20; //The distance of player bets and cards? inside the table
	
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
		
		//Table
		
		CircularPane playerRing = new CircularPane();
		playerRing.setPrefSize(windowW * (5.0 / 6.0), windowH * (5.0 / 6.0));
		
		for (Player player : players) {
			VBox comEntry = new VBox();
			
			Label name = new Label(player.getName());
			Text stack = new Text("Stack: ");
			Text bet = new Text("Bet: ");
			Text action = new Text("Action: ");
			
			HBox cards = new HBox();
			cards.setSpacing(10);
			cards.setAlignment(Pos.CENTER);
			Image card1 = new Image("/Images/" + player.getHole().get(0).getSuit() + "/" + player.getHole().get(0).getRank() + ".png");
			Image card2 = new Image("/Images/" + player.getHole().get(1).getSuit() + "/" + player.getHole().get(1).getRank() + ".png");
			cards.getChildren().addAll(new ImageView(card1), new ImageView(card2));
			
			comEntry.setAlignment(Pos.CENTER);
			comEntry.getChildren().addAll(name, stack, bet, action, cards);
			playerRing.add(comEntry);
		}
		
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
		StackPane playerInfo = seatPlayers(players);
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
	
	private static StackPane seatPlayers(ArrayList<Player> players) {
		double tableBorder = (tableWidth * 2) + (Math.PI * (tableWidth / tableRatio));
		double playerSpacing = tableBorder / (double) players.size();
		double bottomEnd = tableWidth / 2;
		double leftEnd = bottomEnd + Math.PI * ((tableWidth / tableRatio) / 2);
		double topEnd = leftEnd + tableWidth;
		double rightEnd = topEnd + Math.PI * ((tableWidth / tableRatio) / 2);
		
		CircularPane leftSeats = new CircularPane();
		CircularPane rightSeats = new CircularPane();
		leftSeats.setDiameter((tableWidth / tableRatio) + (playerOutset * 2));
		rightSeats.setDiameter((tableWidth / tableRatio) + (playerOutset * 2));
		HBox bottomSeats = new HBox();
		bottomSeats.setAlignment(Pos.CENTER);
		HBox topSeats = new HBox();
		topSeats.setAlignment(Pos.CENTER);
		
		VBox sWaySeats = new VBox();
		sWaySeats.setAlignment(Pos.CENTER);
		sWaySeats.setSpacing((tableWidth / tableRatio) + playerOutset);
		sWaySeats.getChildren().addAll(topSeats, bottomSeats);
		
		HBox lobeSeats = new HBox();
		lobeSeats.setAlignment(Pos.CENTER);
		lobeSeats.setSpacing(tableWidth + (tableWidth / tableRatio) + playerOutset);
		lobeSeats.getChildren().addAll(leftSeats, rightSeats);
		
		int currentPlayer = 0;
		for (Player player : players) {
			double distanceFromUser = currentPlayer * playerSpacing;
			
			Label name = new Label(player.getName());
			Text stack = new Text("Stack: ");
			Text action = new Text("Action: ");
			
			VBox seat = new VBox();
			seat.setAlignment(Pos.CENTER);
			seat.getChildren().addAll(name, stack, action);
			
			if (distanceFromUser < bottomEnd) {
				bottomSeats.getChildren().add(seat);
			}
			else if (distanceFromUser < leftEnd) {
				leftSeats.add(seat);
			}
			else if (distanceFromUser < topEnd) {
				topSeats.getChildren().add(seat);
			}
			else if (distanceFromUser < rightEnd){
				rightSeats.add(seat);
			}
			else {
				bottomSeats.getChildren().add(seat);
			}
			currentPlayer++;
		}
		
		bottomSeats.setSpacing(playerSpacing);
		topSeats.setSpacing(playerSpacing);
		leftSeats.setStartAngle(-135.0);
		leftSeats.setArc((playerSpacing / (leftSeats.getDiameter() / 2.0)) * (180.0 / Math.PI));
		rightSeats.setStartAngle(45.0);
		rightSeats.setArc((playerSpacing / (leftSeats.getDiameter() / 2.0)) * (180.0 / Math.PI));
		
		StackPane playerInfo = new StackPane();
		playerInfo.getChildren().addAll(lobeSeats, sWaySeats);
		return playerInfo;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
