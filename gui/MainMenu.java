package gui;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Currently, this method produces a primitive main menu for demo purposes. The
 * default stack and blind information is displayed and the player can choose
 * their desired number of computer opponents before beginning a game. The menu
 * can also be quit to desktop.
 * 
 * @author Adam Hiles
 * @version 03/23/19
 */
public class MainMenu {
	private StackPane menu;
	
	/**
	 * On construction a new root menu is created.
	 */
	public MainMenu(){
		menu = setMenu();
	}
	
	/**
	 * The main menu's root StackPane is returned to the caller to be added to
	 * the scene tree.
	 * 
	 * @return the main menu root node
	 */
	public StackPane getMenu() {
		return menu;
	}
	
	/**
	 * All aspects of the menu are created and compiled here.
	 * 
	 * @return the menu StackPane
	 */
	private StackPane setMenu() {
		//Menu Background/Base
		StackPane menu = new StackPane();
		menu.setStyle("-fx-background-color: black;");
		
			//Encapsulating Box
			VBox menuBox = new VBox();
			menuBox.setAlignment(Pos.CENTER);
			menuBox.getStyleClass().add("custom-popup");
			menuBox.setSpacing(10);
			menuBox.setMaxSize(480, 600);
			
				//Main Title
				Label title = new Label("Texas Hold'em");
				title.setStyle("-fx-font-size: 36;");
				title.getStyleClass().add("bar-label");
				
				//Game Info and Settings
				HBox stackBox = new HBox();
				stackBox.setSpacing(10);
				stackBox.setAlignment(Pos.CENTER);
				
					Label stackLabel = new Label("Stack Amount:");
					stackLabel.getStyleClass().add("bar-label");
					
					ChoiceBox<String> stackChoice = new ChoiceBox<String>();
					stackChoice.setItems(FXCollections.observableArrayList(
							"$1 000", "$10 000", "$100 000", "$1 000 000"));
					stackChoice.getSelectionModel().selectFirst();
					stackChoice.setId("stackChoice");
					
				stackBox.getChildren().addAll(stackLabel, stackChoice);
					
				Label blindLabel= new Label("Blinds: $25/$50");
				blindLabel.getStyleClass().add("bar-label");
				Label sliderLabel = new Label("Choose number of coms:");
				sliderLabel.getStyleClass().add("bar-label");
				
				Slider comSlider = new Slider(1, 9, 5);
				comSlider.setMaxWidth(300);
				comSlider.setShowTickLabels(true);
				comSlider.setShowTickMarks(true);
				comSlider.setBlockIncrement(1);
				comSlider.setMajorTickUnit(1);
				comSlider.setMinorTickCount(0);
				comSlider.setSnapToTicks(true);
				comSlider.setId("comSlider");
			
				//Enter/Exit Buttons
				HBox buttonBox = new HBox();
				buttonBox.setAlignment(Pos.CENTER);
				buttonBox.setSpacing(10);
				
					Button start = new Button("Start Game");
					start.getStyleClass().add("button-large");
					start.setId("startButton");
					Button exit = new Button("Exit");
					exit.getStyleClass().add("button-large");
				
				buttonBox.getChildren().addAll(start, exit);
				
			menuBox.getChildren().addAll(title, stackBox, blindLabel, sliderLabel, comSlider, buttonBox);
			
			//=================================================================
			// Event Handlers/Listeners
			
			exit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});
			
			stackChoice.valueProperty().addListener(new ChangeListener<String>() { //On a change of stack the blinds label is automatically updated
				public void changed(ObservableValue<? extends String> observable, 
						String oldValue, String newValue) {
					double stack = Double.parseDouble(newValue.substring(1).replaceAll("\\s+", ""));
					MoneyFormatter smallBlind = new MoneyFormatter((int) (stack * 0.025));
					MoneyFormatter bigBlind = new MoneyFormatter((int) (stack * 0.05));
					blindLabel.setText("Blinds: " + smallBlind.toString() + "/" + bigBlind.toString());
				}
			});
			
		menu.getChildren().add(menuBox);
		
		return menu;
	}
	
}