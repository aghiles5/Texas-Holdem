import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This class creates new action bars, containing all player controls such as
 * fold, call, and raise actions and quitting the game. End of round of play
 * and end game messages and controls are also displayed here.
 * 
 * @author Adam Hiles
 * @version 03/10/19
 */
public class ActionBar {
	BorderPane barPane;
	
	/**
	 * On construction all the elements of the action bar are created.
	 * 
	 * @param winWidth the width of the window
	 * @param winHeight the heigh of the window
	 */
	public ActionBar(double winWidth, double winHeight) {
		barPane = setActionBar(winWidth, winHeight); 
	}
	
	/**
	 * The action bar's root node is returned to the caller to be added to
	 * a scene tree.
	 * 
	 * @return the master action bar node
	 */
	public BorderPane getBarPane() {
		return barPane;
	}
	
	/**
	 * Here, all buttons, panels, labels, and controls of the action bar are
	 * generated, id'd if applicable, and returned to the constructor packaged
	 * in a root node.
	 * 
	 * @param winWidth the width of the window
	 * @param winHeight the heigh of the window
	 * @return the root action bar node
	 */
	private BorderPane setActionBar(double winWidth, double winHeight) {
		BorderPane actionBar = new BorderPane();
		actionBar.setPrefSize(winWidth, winHeight / 10.0);
		actionBar.setStyle("-fx-border-style: solid inside;"
		        + "-fx-border-width: 5;" + "-fx-border-color: #4B0905;"
				+ "-fx-background-color: maroon");
		
		//=====================================================================
		//Action Buttons
		
		StackPane actions = new StackPane();
		
		//Main buttons
		HBox controls = new HBox();
		controls.setId("controls");
		
			Button fold = new Button("Fold");
			fold.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			fold.getStyleClass().add("button-large");
			fold.setId("fold");

			Button raise = new Button("Raise");
			raise.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			raise.getStyleClass().add("button-large");
			
			Button call = new Button("Call");
			call.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			call.getStyleClass().add("button-large");
			call.setId("call");
		
		controls.getChildren().addAll(fold, raise, call);
		
		//Input for raise
		HBox raiseInput = new HBox();
		raiseInput.setAlignment(Pos.CENTER);
		raiseInput.setSpacing(10);
		raiseInput.setId("raiseInput");
		
			Label raiseFieldLabel = new Label("Enter your wager:");
			raiseFieldLabel.getStyleClass().add("bar-label");
			
			Slider raiseSlider = new Slider(0, 100000, 50000);
			raiseSlider.setMinWidth(250);
			raiseSlider.setShowTickLabels(true);
			raiseSlider.setShowTickMarks(true);
			raiseSlider.setMajorTickUnit(10000);
			raiseSlider.setMinorTickCount(4);
			raiseSlider.setSnapToTicks(true);
			raiseSlider.setId("raiseSlider");
			
			HBox raiseFieldButtons = new HBox();
			raiseFieldButtons.setAlignment(Pos.CENTER);
			raiseFieldButtons.setSpacing(5);
			
			Button raiseFieldConfirm = new Button("Enter");
			raiseFieldConfirm.setId("raiseConfirm");
			Button raiseFieldCancel = new Button("Cancel");
			
			raiseFieldButtons.getChildren().addAll(raiseFieldConfirm, raiseFieldCancel);
		
		raiseInput.getChildren().addAll(raiseFieldLabel, raiseSlider, raiseFieldButtons);
		raiseInput.setVisible(false);
		
		//Notification window
		HBox notif = new HBox();
		notif.getStyleClass().add("popup");
		notif.setAlignment(Pos.CENTER);
		notif.setSpacing(50);
		notif.setId("notif");
		Label notifLabel = new Label("Blind Round");
		notifLabel.getStyleClass().add("bar-label");
		notifLabel.setStyle("-fx-font-size: 32;");
		notifLabel.setId("notifLabel");
		Button notifCont = new Button("Continue");
		notifCont.getStyleClass().add("button-large");
		notifCont.setId("notifCont");
		notif.getChildren().addAll(notifLabel, notifCont);
		
		
		actions.getChildren().addAll(controls, raiseInput, notif);
		
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
				raise.setText("");
			}
		});
		
		raiseFieldCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				raiseInput.setVisible(false);
				raise.setText("Raise");
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
}
