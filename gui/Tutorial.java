package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Tutorial {
	private BorderPane tutorial = new BorderPane();
	
	public Tutorial() {
		setTutorialPane();
	}
	
	public BorderPane getTutorial() {
		return tutorial;
	}
	
	private void setTutorialPane() {
		BorderPane controls = new BorderPane();
		
			Button back = new Button("Back");
			back.setAlignment(Pos.CENTER);
			back.getStyleClass().add("button-large");
			controls.setLeft(back);
			
			Button next = new Button("Next");
			next.setAlignment(Pos.CENTER);
			next.getStyleClass().add("button-large");
			controls.setRight(next);
		
			Button returnButton = new Button("Return");
			returnButton.setAlignment(Pos.CENTER);
			returnButton.setId("return");
			returnButton.getStyleClass().add("button-large");
			controls.setCenter(returnButton);
			
		tutorial.setBottom(controls);
	}
}
