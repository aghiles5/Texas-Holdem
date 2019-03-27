package gui;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * @version 03/23/19
 */
public class ActionBar {
	BorderPane barPane;
	
	/**
	 * On construction all the elements of the action bar are created.
	 * 
	 * @param winWidth the width of the window
	 * @param winHeight the heigh of the window
	 */
	public ActionBar(double winWidth, double winHeight, int stackSize) {
		barPane = setActionBar(winWidth, winHeight, stackSize); 
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
	private BorderPane setActionBar(double winWidth, double winHeight, int stackSize) {
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
		
			Button fold = new Button("Fold"); //Fold button
			fold.setPrefSize(winWidth * (3.0 / 10.0) - 4, winHeight / 10 - 10);
			fold.getStyleClass().add("button-large");
			fold.setId("fold");

			Button raise = new Button("Raise"); //Raise/Bet button
			raise.setPrefSize(winWidth * (3.0 / 10.0) - 3, winHeight / 10 - 10);
			raise.getStyleClass().add("button-large");
			raise.setId("raise");
			
			Button call = new Button("Call"); //Call/Check Button
			call.setPrefSize(winWidth * (3.0 / 10.0) - 3, winHeight / 10 - 10);
			call.getStyleClass().add("button-large");
			call.setId("call");
		
		controls.getChildren().addAll(fold, raise, call);
		controls.setDisable(true);
		
		//Input for raise
		HBox raiseInput = new HBox();
		raiseInput.setAlignment(Pos.CENTER);
		raiseInput.setSpacing(20);
		raiseInput.setId("raiseInput");
		
			Label raiseFieldLabel = new Label("Enter your wager:");
			raiseFieldLabel.getStyleClass().add("bar-label");
			
			VBox sliderBox = new VBox(); //The raise slider and its amount label
			sliderBox.setAlignment(Pos.CENTER);
			
				Slider raiseSlider = new Slider(stackSize * 0.025, stackSize, stackSize / 2);
				raiseSlider.setMinWidth(250);
				raiseSlider.setShowTickLabels(true);
				raiseSlider.setMajorTickUnit(stackSize);
				raiseSlider.setMinorTickCount((int) ((stackSize * 0.001) - 1));
				raiseSlider.setSnapToTicks(true);
				raiseSlider.setId("raiseSlider");
				
				Label sliderLabel = new Label("$50 000");
				sliderLabel.getStyleClass().add("bar-label");
				
			sliderBox.getChildren().addAll(raiseSlider, sliderLabel);
			
			VBox raiseFieldButtons = new VBox(); //The enter or cancel buttons for raise/bet
			raiseFieldButtons.setAlignment(Pos.CENTER);
			raiseFieldButtons.setSpacing(5);
			
				Button raiseFieldConfirm = new Button("Enter");
				raiseFieldConfirm.setId("raiseConfirm");
				Button raiseFieldCancel = new Button("Cancel");
			
			raiseFieldButtons.getChildren().addAll(raiseFieldConfirm, raiseFieldCancel);
		
		raiseInput.getChildren().addAll(raiseFieldLabel, sliderBox, raiseFieldButtons);
		raiseInput.setVisible(false);
		
		//Notification window
		HBox notif = new HBox();
		notif.getStyleClass().add("custom-popup");
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
		notif.setVisible(false);
		
		actions.getChildren().addAll(controls, raiseInput, notif);
				
		//=====================================================================
		//Settings Buttons
		
		VBox settingButtons = new VBox();
		settingButtons.setAlignment(Pos.CENTER);
		
			Button save = new Button("Save");
			save.setMinSize(winWidth / 10, winHeight / 30 - 4);
			save.setId("save");
			save.setDisable(true);
			
			Button help = new Button("Help");
			help.setMinSize(winWidth / 10, winHeight / 30 - 3);
			help.setId("help");
			help.setDisable(true);
			
			Button escapeClause = new Button("Quit");
			escapeClause.setId("quit");
			escapeClause.setMinSize(winWidth / 10, winHeight / 30 - 3);
			escapeClause.setDisable(true);

		settingButtons.getChildren().addAll(save, help, escapeClause);
		
		//=====================================================================
		//Event Handlers/Listeners
		
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
				raise.setText("Bet");
			}
		});
		
		escapeClause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		raiseSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observables,
                Number oldValue, Number newValue) {
            		String betAmount = "";
            		int intAmount = ((newValue.intValue() + (int) (stackSize * 0.0005)) / (int) (stackSize * 0.001)) * (int) (stackSize * 0.001), digitCounter = 0;
            		while (intAmount != 0) {
            			if (digitCounter % 3 == 0 && digitCounter != 0)
            				betAmount = intAmount % 10 + " " + betAmount;
            			else
            				betAmount = intAmount % 10 + betAmount;
            			intAmount /= 10;
            			digitCounter++;
            		}
            		betAmount = "$" + betAmount;
                    sliderLabel.setText(betAmount);
            }
        });
		
		//=====================================================================
		//Setting to BorderPane to return
		
		actionBar.setLeft(actions);
		actionBar.setRight(settingButtons);
		return actionBar;
	}
}
