package gui;

import java.util.ArrayList;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Tutorial {
	private BorderPane tutorial = new BorderPane();
	private ArrayList<VBox> slides = new ArrayList<VBox>();
	private int slideIndex = 0;
	
	public Tutorial(double winWidth) {
		setTutorialPane(winWidth);
	}
	
	public BorderPane getTutorial() {
		return tutorial;
	}
	
	private void setTutorialPane(double winWidth) {
		setSlides();
		
		BorderPane controls = new BorderPane();
		
			Button back = new Button("Back");
			back.setAlignment(Pos.CENTER);
			back.setMinWidth(winWidth / 3);
			back.getStyleClass().add("button-tutorial");
			back.setDisable(true);
			controls.setLeft(back);
			
			Button next = new Button("Next");
			next.setAlignment(Pos.CENTER);
			next.setMinWidth(winWidth / 3);
			next.getStyleClass().add("button-tutorial");
			controls.setRight(next);
		
			Button returnButton = new Button("Return");
			returnButton.setAlignment(Pos.CENTER);
			returnButton.setMinWidth(winWidth / 3);
			returnButton.setId("return");
			returnButton.getStyleClass().add("button-tutorial");
			controls.setCenter(returnButton);
			
		tutorial.setBottom(controls);
		tutorial.setCenter(slides.get(slideIndex));
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				slideIndex -= 1;
				tutorial.setCenter(slides.get(slideIndex));
				if (slideIndex == 0) {
					back.setDisable(true);
					next.setDisable(false);
				}
				else {
					back.setDisable(false);
					next.setDisable(false);
				}
			}
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				slideIndex += 1;
				tutorial.setCenter(slides.get(slideIndex));
				if (slideIndex == slides.size() - 1) {
					back.setDisable(false);
					next.setDisable(true);
				}
				else {
					back.setDisable(false);
					next.setDisable(false);
				}
			}
		});
	}
	
	private void setSlides() {
		VBox slideA = new VBox();
		slideA.getStyleClass().add("custom-popup");
		
		VBox slideB = new VBox();
		slideB.getStyleClass().add("custom-popup");
		
		slides.add(slideA);
		slides.add(slideB);
	}
}
