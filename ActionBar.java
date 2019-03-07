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

public class ActionBar {
	BorderPane barPane;
	
	public ActionBar(double winWidth, double winHeight) {
		barPane = setActionBar(winWidth, winHeight); 
	}
	
	public BorderPane getBarPane() {
		return barPane;
	}
	
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
			Button raiseFieldCancel = new Button("Cancel");
			
			raiseFieldButtons.getChildren().addAll(raiseFieldConfirm, raiseFieldCancel);
		
		raiseInput.getChildren().addAll(raiseFieldLabel, raiseSlider, raiseFieldButtons);
		raiseInput.setVisible(false);
		
		actions.getChildren().addAll(controls, raiseInput);
		
		//=====================================================================
		//Settings Buttons
		
		VBox settingButtons = new VBox();
		settingButtons.setAlignment(Pos.CENTER);
		
		Button escapeClause = new Button("QUIT");
		
		Button disableTest = new Button("Test Control Disable");

		settingButtons.getChildren().addAll(escapeClause, disableTest);
		
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
		
		disableTest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (controls.isDisable()) {
					controls.setDisable(false);
				}
				else {
					controls.setDisable(true);
					raiseInput.setVisible(false);
					raise.setText("Raise");
				}
			}
		});

		//=====================================================================
		//Setting to BorderPane to return
		
		actionBar.setLeft(actions);
		actionBar.setRight(settingButtons);
		return actionBar;
	}
}
