import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ActionBar {
	BorderPane barPane;
	
	public ActionBar() {
		barPane = setActionBar(1920, 1080); 
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
		HBox buttons = new HBox();
		
			Button fold = new Button("Fold");
			fold.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			fold.getStyleClass().add("button-large");

			Button raise = new Button("Raise");
			raise.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			raise.getStyleClass().add("button-large");
			
			Button call = new Button("Call");
			call.setPrefSize(winWidth * (3.0 / 10.0), winHeight / 10 - 10);
			call.getStyleClass().add("button-large");
		
		buttons.getChildren().addAll(fold, raise, call);
		
		//Input for raise
		HBox raiseInput = new HBox();
		raiseInput.setAlignment(Pos.CENTER);
		raiseInput.setSpacing(10);
		
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
