package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
		setSlides(winWidth);
		
		BorderPane controls = new BorderPane();
		controls.setStyle("-fx-background-color: maroon;");
		
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
				if (slideIndex == 0)
					back.setDisable(true);
				else
					next.setDisable(false);
			}
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				slideIndex += 1;
				tutorial.setCenter(slides.get(slideIndex));
				if (slideIndex == slides.size() - 1)
					next.setDisable(true);
				else
					back.setDisable(false);
			}
		});
	}
	
	private void setSlides(double winWidth) {
		VBox slideA = new VBox();
		slideA.getStyleClass().add("custom-popup");
		slideA.setSpacing(20);
		slideA.setAlignment(Pos.CENTER);
		
			Label titleA = new Label("Card and Hand Rankings");
			titleA.setWrapText(true);
			titleA.getStyleClass().add("tutorial-title");
			
			Label bodyA = new Label("");
			bodyA.setWrapText(true);
			bodyA.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyA.getStyleClass().add("tutorial-body");
			
			ImageView imageA = new ImageView();
		
		slideA.getChildren().addAll(titleA, bodyA, imageA);
		
		VBox slideB = new VBox();
		slideB.getStyleClass().add("custom-popup");
		slideB.setSpacing(20);
		slideB.setAlignment(Pos.CENTER);
		
			Label titleB = new Label("Table Layout");
			titleB.setWrapText(true);
			titleB.getStyleClass().add("tutorial-title");
			
			Label bodyB = new Label("");
			bodyB.setWrapText(true);
			bodyB.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyB.getStyleClass().add("tutorial-body");
			
			ImageView imageB = new ImageView();
		
		slideB.getChildren().addAll(titleB, bodyB, imageB);
		
		VBox slideC = new VBox();
		slideC.getStyleClass().add("custom-popup");
		slideC.setSpacing(20);
		slideC.setAlignment(Pos.CENTER);
		
			Label titleC = new Label("Objective of the Game");
			titleC.setWrapText(true);
			titleC.getStyleClass().add("tutorial-title");
			
			Label bodyC = new Label("");
			bodyC.setWrapText(true);
			bodyC.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyC.getStyleClass().add("tutorial-body");
		
		slideC.getChildren().addAll(titleC, bodyC);
		
		VBox slideD = new VBox();
		slideD.getStyleClass().add("custom-popup");
		slideD.setSpacing(20);
		slideD.setAlignment(Pos.CENTER);
		
			Label titleD = new Label("Actions");
			titleD.setWrapText(true);
			titleD.getStyleClass().add("tutorial-title");
			
			Label bodyD = new Label("");
			bodyD.setWrapText(true);
			bodyD.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyD.getStyleClass().add("tutorial-body");
		
		slideD.getChildren().addAll(titleD, bodyD);
		
		VBox slideE = new VBox();
		slideE.getStyleClass().add("custom-popup");
		slideE.setSpacing(20);
		slideE.setAlignment(Pos.CENTER);
		
			Label titleE = new Label("Blind Round");
			titleE.setWrapText(true);
			titleE.getStyleClass().add("tutorial-title");
			
			Label bodyE = new Label("");
			bodyE.setWrapText(true);
			bodyE.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyE.getStyleClass().add("tutorial-body");
		
		slideE.getChildren().addAll(titleE, bodyE);
		
		VBox slideF = new VBox();
		slideF.getStyleClass().add("custom-popup");
		slideF.setSpacing(20);
		slideF.setAlignment(Pos.CENTER);
		
			Label titleF = new Label("Community Card Rounds");
			titleF.setWrapText(true);
			titleF.getStyleClass().add("tutorial-title");
			
			Label bodyF = new Label("");
			bodyF.setWrapText(true);
			bodyF.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyF.getStyleClass().add("tutorial-body");
		
		slideF.getChildren().addAll(titleF, bodyF);
		
		VBox slideG = new VBox();
		slideG.getStyleClass().add("custom-popup");
		slideG.setSpacing(20);
		slideG.setAlignment(Pos.CENTER);
		
			Label titleG = new Label("Showdown");
			titleG.setWrapText(true);
			titleG.getStyleClass().add("tutorial-title");
			
			Label bodyG = new Label("");
			bodyG.setWrapText(true);
			bodyG.setMaxWidth(winWidth * (5.0 / 6.0));
			bodyG.getStyleClass().add("tutorial-body");
		
		slideG.getChildren().addAll(titleG, bodyG);
		
		slides.add(slideA);
		slides.add(slideB);
		slides.add(slideC);
		slides.add(slideD);
		slides.add(slideE);
		slides.add(slideF);
		slides.add(slideG);
	}
}
