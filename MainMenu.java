import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenu {
	private StackPane menu;
	
	public MainMenu(){
		menu = setMenu();
	}
	
	public StackPane getMenu() {
		return menu;
	}
	
	private StackPane setMenu() {
		StackPane menu = new StackPane();
		menu.setStyle("-fx-background-color: black;");
		
			VBox menuBox = new VBox();
			menuBox.setAlignment(Pos.CENTER);
			menuBox.setStyle("-fx-background-color: maroon;" 
					+ "-fx-border-color: goldenrod;" + "-fx-border-radius: 5;"
					+ "-fx-border-width: 5;" + "-fx-border-insets: 10;");
			menuBox.setSpacing(10);
			menuBox.setMaxSize(480, 600);
			
				Label title = new Label("Texas Hold'em");
				title.setStyle("-fx-font-size: 36;"
						+ "-fx-text-fill: goldenrod;");
				
				Label stacks = new Label("Stack Amount: $100 000");
				stacks.setStyle("-fx-text-fill: goldenrod;");
				Label blinds = new Label("Blinds: $250/$500");
				blinds.setStyle("-fx-text-fill: goldenrod;");
				Label sliderLabel = new Label("Choose number of coms:");
				sliderLabel.setStyle("-fx-text-fill: goldenrod;");
				
				Slider comSlider = new Slider(1, 9, 5);
				comSlider.setMaxWidth(300);
				comSlider.setShowTickLabels(true);
				comSlider.setShowTickMarks(true);
				comSlider.setBlockIncrement(1);
				comSlider.setMajorTickUnit(1);
				comSlider.setMinorTickCount(0);
				comSlider.setSnapToTicks(true);
				comSlider.setId("comSlider");
			
				HBox buttonBox = new HBox();
				buttonBox.setAlignment(Pos.CENTER);
				buttonBox.setSpacing(10);
				
					Button start = new Button("Start Game");
					start.getStyleClass().add(".button-large");
					start.setId("startButton");
					Button exit = new Button("Exit");
					exit.getStyleClass().add(".button-large");
				
				buttonBox.getChildren().addAll(start, exit);
				
			menuBox.getChildren().addAll(title, stacks, blinds, sliderLabel, comSlider, buttonBox);
			
			exit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});
			
		menu.getChildren().add(menuBox);
		
		return menu;
	}
	
}
