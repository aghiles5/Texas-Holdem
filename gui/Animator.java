package gui;

import java.util.ArrayList;

import game.Game;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import players.AI;
import players.Human;
import players.Player;

/**
 * This class handles all necessary animations card nodes on the table will
 * follow, including drawing, discarding, and shuffling.
 * 
 * @author Adam Hiles
 * @version 04/10/19
 */
public class Animator {
	/**
	 * At the beginning of each round of play the deck is shuffled, all 
	 * player holes are dealt, and the user's cards are revealed to them,
	 * with such animations being constructed and returned to the caller in
	 * order to set on finish conditions.
	 * 
	 * @param scene the master node tree
	 * @param game the current Game object
	 * @return the full round start animation
	 */
	public SequentialTransition roundStartAnim(Scene scene, Game game) {
		SequentialTransition fullMotion = new SequentialTransition();
		fullMotion.getChildren().addAll(shuffleDeck(scene, game), dealHoles(scene, game));
		return fullMotion;
	}
	
	/**
	 * At the end of each round of play the community cards and remaining hole
	 * cards are returned the deck visually by this animation.
	 * 
	 * @param scene the master node tree
	 * @param game the current Game object
	 * @return the full round end animation
	 */
	public SequentialTransition roundFinishAnim(Scene scene, Game game) {
		SequentialTransition fullMotion = new SequentialTransition();
		fullMotion.getChildren().addAll(returnComm(scene, game), returnAllHoles(scene, game));
		return fullMotion;
	}
	
	/**
	 * In order for the code to properly recognize the absolute of coordinates
	 * of nodes contained in layout nodes (HBox, VBox, etc.) an near
	 * instantaneous initializing animation is carried out in which the first
	 * player's first card is drawn then returned to the deck.
	 * 
	 * @param scene the master node tree
	 * @param game the current Game object
	 * @return the initialization animation
	 */
	public SequentialTransition iniMovement(Scene scene, Game game) {
		SequentialTransition motion = moveCard(scene, (ImageView) scene.lookup("#" + game.getPlayers().get(0).getName() + "Card1Back"), 
				(ImageView) scene.lookup("#" + game.getPlayers().get(0).getName() + "Card1"), false, true);
		SequentialTransition motion2 = moveCard(scene, (ImageView) scene.lookup("#" + game.getPlayers().get(0).getName() + "Card1Back"), 
				(ImageView) scene.lookup("#" + game.getPlayers().get(0).getName() + "Card1"), true, true);
		motion.getChildren().add(motion2);
		return motion;
	}
	
	/**
	 * To show the shuffling of the cards each image that makes up the deck's
	 * GUI representation has to be moved in a specific way as defined in the
	 * below function, which creates an animation for an image based on the
	 * specific instructions
	 * 
	 * @param scene the master node tree
	 * @param image the image to be moved
	 * @param mirrored whether the animation is a mirrored version or not
	 * @return a full motion animation
	 */
	private SequentialTransition shuffleAnimFactory(Scene scene, ImageView image, Boolean mirrored) {
		TranslateTransition shiftImageHoriz = new TranslateTransition(Duration.millis(250), image); //The image is shifted horizontally 40 pixels
		if (mirrored) //Mirrored images are shifted in the other direction
			shiftImageHoriz.setByX(-40);
		else
			shiftImageHoriz.setByX(40);
		shiftImageHoriz.setAutoReverse(true);
		shiftImageHoriz.setCycleCount(2);
		
		TranslateTransition shiftImageVerti = new TranslateTransition(Duration.millis(250), image); //The image is shifted vertically 56 pixels
		if (mirrored) //Mirrored images are shifted in the other direction
			shiftImageVerti.setByY(-56);
		else
			shiftImageVerti.setByY(56);
		shiftImageVerti.setAutoReverse(true);
		shiftImageVerti.setCycleCount(2);
		
		SequentialTransition shuffleAnim = new SequentialTransition(shiftImageHoriz, shiftImageVerti); //The image is shifted horizontally then vertically
		
		return shuffleAnim;
	}
	
	/**
	 * At the start of each round of play the deck's GUI representation has 
	 * its components shifted to simulate a shuffle as controlled by the below
	 * method. 
	 * 
	 * @param scene the node tree
	 * @param game the current game object
	 * @return the full shuffle animation for all involved nodes
	 */
	private ParallelTransition shuffleDeck(Scene scene, Game game) {
		//The deck components are listed
		ImageView deckA = (ImageView) scene.lookup("#deckA");
		ImageView deckB = (ImageView) scene.lookup("#deckB");
		ImageView drawCard = (ImageView) scene.lookup("#drawCard");
		ImageView returnCard = (ImageView) scene.lookup("#returnCard");
		
		ParallelTransition shuffleAnim = new ParallelTransition();
		
		SequentialTransition deckAAnim = shuffleAnimFactory(scene, deckA, false);
		SequentialTransition deckBAnim = shuffleAnimFactory(scene, deckB, true);
		SequentialTransition drawCardAnim = shuffleAnimFactory(scene, drawCard, true);
		SequentialTransition returnCardAnim = shuffleAnimFactory(scene, returnCard, false);
		
		shuffleAnim.getChildren().addAll(deckAAnim, deckBAnim, drawCardAnim, returnCardAnim); //All components animations are played in unison
		
		return shuffleAnim;
	}
	
	/**
	 * At the beginning of each round of play after the deck is shuffled by its
	 * animation each player is dealt their hole cards, beginning with the
	 * first card clockwise then the second clockwise.
	 * 
	 * @param scene the game node tree
	 * @param game the current Game object
	 * @return the full motion for all hole cards
	 */
	private SequentialTransition dealHoles(Scene scene, Game game) {
		SequentialTransition dealAllHoles = new SequentialTransition();
		ParallelTransition showUserCards = new ParallelTransition();
		
		for (int card = 1; card <= 2; card++) { //Each of a player's two cards is iterated through
			for (Player player : game.getPlayers()) { //Each player is iterated through
				ImageView cardBack = (ImageView) scene.lookup("#" + player.getName() + "Card" + card + "Back");
				ImageView cardFront = (ImageView) scene.lookup("#" + player.getName() + "Card" + card);
				
				Boolean fast = false;
				if (game.isUserFolded())
					fast = true;
				
				if (player instanceof Human) { //If the player is the user their cards are set to reveal
					SequentialTransition cardFlip = flipCard(cardBack, cardFront, false, fast);
					showUserCards.getChildren().add(cardFlip);
				}
				
				SequentialTransition cardMotion = moveCard(scene, cardBack, cardFront, false, fast);
				dealAllHoles.getChildren().add(cardMotion);
			}
		}
		
		dealAllHoles.getChildren().add(showUserCards);
		return dealAllHoles;
	}
	
	/**
	 * For each AI player in the passed list, i.e. those whose cards are
	 * covered, their hole cards are revealed through the card reveal 
	 * animation.
	 * 
	 * @param players the players still in the game
	 * @param scene the game node tree
	 */
	public void revealAllCards(ArrayList<Player> players, Scene scene, Boolean fast) {
		ParallelTransition revealAnim = new ParallelTransition();
		
		for (int card = 1; card <= 2; card++) {
			for (Player player : players) {
				if (player instanceof AI) { 
					ImageView cardBack = (ImageView) scene.lookup("#" + player.getName() + "Card" + card + "Back");
					ImageView cardFront = (ImageView) scene.lookup("#" + player.getName() + "Card" + card);
					
					SequentialTransition revealCard = flipCard(cardBack, cardFront, false, fast);
					revealAnim.getChildren().add(revealCard);
				}
			}
		}
		
		revealAnim.play();
	}
	
	/**
	 * For the second betting round the first three community cards are
	 * revealed sequentially via the below method and the card deal and
	 * reveal animations.
	 * 
	 * @param scene the game node tree
	 */
	public void dealFlop(Scene scene, Game game) {
		SequentialTransition dealingAnim = new SequentialTransition();
		
		ParallelTransition revealCards = new ParallelTransition();
		
		Boolean fast = false;
		if (game.isUserFolded())
			fast = true;
		
		for (int commCard = 0; commCard < 3; commCard++) {
			ImageView cardBack = (ImageView) scene.lookup("#commBack" + commCard);
			ImageView cardFront = (ImageView) scene.lookup("#commFront" + commCard);
			
			SequentialTransition revealCard = flipCard(cardBack, cardFront, false, fast);
			revealCards.getChildren().add(revealCard);
			
			SequentialTransition dealCardAnim = moveCard(scene, cardBack, cardFront, false, fast);
			dealingAnim.getChildren().add(dealCardAnim);
		}
		
		dealingAnim.getChildren().add(revealCards); //After all cards are dealt they are simultaneously revealed
		
		dealingAnim.play();
	}
	
	/**
	 * For the river and turn rounds a single card is dealt and revealed.
	 * 
	 * @param scene the game node tree
	 * @param cardBack the back of the subject card
	 * @param cardFront the front of the subject card
	 */
	public void dealStreet(Scene scene, ImageView cardBack, ImageView cardFront, Boolean fast) {
		SequentialTransition cardAnim = moveCard(scene, cardBack, cardFront, false, fast);
		cardAnim.setOnFinished(e -> flipCard(cardBack, cardFront, false, fast).play());
		
		cardAnim.play();
	}
	
	/**
	 * All of the community cards are simultaneously flipped then returned to
	 * the deck one by one.
	 * 
	 * @param scene the game node tree
	 * @param game the current Game object
	 * @return the full return animation for the community cards
	 */
	private SequentialTransition returnComm(Scene scene, Game game) {
		SequentialTransition returnCards = new SequentialTransition();
		ParallelTransition hideCards = new ParallelTransition();
		returnCards.getChildren().add(hideCards); //The parallel hide animation is played before any returns to the deck
		
		Boolean fast = false;
		if (game.isUserFolded())
			fast = true;
		
		for (int commCard = 0; commCard < 5; commCard++) { //Each of the five community cards is iterated through
			ImageView commFront = (ImageView) scene.lookup("#commFront" + commCard);
			ImageView commBack = (ImageView) scene.lookup("#commBack" + commCard);
			hideCards.getChildren().add(flipCard(commBack, commFront, true, fast));
			returnCards.getChildren().add(moveCard(scene, commBack, commFront, true, fast));
		}
		
		return returnCards;
	}
	
	/**
	 * To return all of the players' hole cards at the end of a round of play
	 * each player is passed to the returnHole method to produce a returning
	 * animation which is added to the whole method's animation. On the end of
	 * the animation the Game object is reset for the next round of play and
	 * a new round of play is started.
	 * 
	 * @param scene the game node tree
	 * @param game the current Game object
	 * @return the full return animation for all remaining hole cards
	 */
	private SequentialTransition returnAllHoles(Scene scene, Game game) {
		SequentialTransition returnCards = new SequentialTransition();
		
		for (Player player : game.getPlayers()) { //Each player is iterated
			Boolean fast = false;
			if (game.isUserFolded())
				fast = true;
			returnCards.getChildren().add(returnHole(scene, player, true, fast));
		}
		
		return returnCards;
	}
	
	/**
	 * To return a player's cards to the deck image they are first
	 * simultaneously flipped (if applicable) then returned to the deck one at
	 * a time.
	 * 
	 * @param scene the game node tree
	 * @param player the player whose hole cards need to be returned
	 * @param flip if the player's cards also need to be flipped, for a folding player and end round returns
	 * @return the return hole animation
	 */
	public SequentialTransition returnHole(Scene scene, Player player, Boolean flip, Boolean fast) {
		ParallelTransition hideCards = new ParallelTransition();
		
		SequentialTransition returnCards = new SequentialTransition();
		returnCards.getChildren().add(hideCards); //The cards are flipped before returning (if applicable)
		
		for (int card = 1; card <= 2; card++) { //Each card is iterated to be returned
			ImageView cardBack = (ImageView) scene.lookup("#" + player.getName() + "Card" + card + "Back");
			ImageView cardFront = (ImageView) scene.lookup("#" + player.getName() + "Card" + card);
			
			if (flip) { //If the card is needed to be flipped it is set to do so
				SequentialTransition hideCard = flipCard(cardBack, cardFront, true, fast);
				hideCards.getChildren().add(hideCard);
			}
			
			SequentialTransition returnCard = moveCard(scene, cardBack, cardFront, true, fast);
			returnCards.getChildren().add(returnCard);
		}
		
		return returnCards;
	}
	
	/**
	 * The following method controls the animation of a card either being drawn
	 * from or returned to the deck. 
	 * 
	 * @param scene the game node tree
	 * @param cardBack the image of the back of the card
	 * @param cardFront the image of the front of the card
	 * @param reversed whether or not the animation should be reversed
	 * @param fast whether or not the animation should move fast
	 * @return the card's full motion
	 */
	private SequentialTransition moveCard(Scene scene, ImageView cardBack, ImageView cardFront, Boolean reversed, Boolean fast) {
		ImageView subCard; //Depending on if the card needs to be drawn or returned the appropriate dummy image is found
		if (reversed)
			subCard = (ImageView) scene.lookup("#returnCard");
		else
			subCard = (ImageView) scene.lookup("#drawCard");
		
		double originX = subCard.localToScene(subCard.getBoundsInLocal()).getMinX(); //The to and from coordinates are calculated
		double originY = subCard.localToScene(subCard.getBoundsInLocal()).getMinY(); 
		double targetX = cardBack.localToScene(cardBack.getBoundsInLocal()).getMinX(); 
		double targetY = cardBack.localToScene(cardBack.getBoundsInLocal()).getMinY(); 
		
		SequentialTransition fullMotion = new SequentialTransition();
		
		TranslateTransition moveCard = new TranslateTransition(); //The deck-to-card motion is set
		moveCard.setNode(subCard);
		if (reversed || fast) //In return motion this animation will be nearly instantaneous, also for fast
			moveCard.setDuration(Duration.millis(1));
		else
			moveCard.setDuration(Duration.millis(250));
		moveCard.setFromX(0.0);
		moveCard.setFromY(0.0);
		moveCard.setByX(targetX - originX);
		moveCard.setByY(targetY - originY);
		
		TranslateTransition returnCard = new TranslateTransition(); //The card-to-deck motion is set
		returnCard.setNode(subCard);
		if (reversed && !fast) //In dealing animation and fast this animation will be nearly instantaneous
			returnCard.setDuration(Duration.millis(250));
		else
			returnCard.setDuration(Duration.millis(1));
		returnCard.setByX(originX - targetX);
		returnCard.setByX(originY - targetY);
		returnCard.setToX(0.0);
		returnCard.setToY(0.0);
		
		fullMotion.getChildren().addAll(moveCard, returnCard);
		
		moveCard.setOnFinished(new EventHandler<ActionEvent>() { //On the end of the deck-to-card motion the target card will either be visible or invisible
			@Override
			public void handle(ActionEvent event) {
				if (reversed) {
					cardBack.setVisible(false);
					cardFront.setVisible(false);
				}
				else {
					cardBack.setVisible(true);
					cardFront.setVisible(true);
				}
			}
		});
		
		return fullMotion; //The full motion is returned for further setOnFinished actions
	}
	
	/**
	 * The set of ImageViews passed into this method will be swapped by an
	 * appropriate flipping animation. The positions of the card back and 
	 * front imageviews are swapped if a hiding animation is required over a
	 * revealing animation.
	 * 
	 * @param cardBack the ImageView of the card's back
	 * @param cardFront the ImageView of the card's face
	 * @param reversed whether or not the animation should be reversed
	 * @param fast whether or not the animation should be fast
	 */
	private SequentialTransition flipCard(ImageView cardBack, ImageView cardFront, Boolean reversed, Boolean fast) {
		SequentialTransition fullMotion = new SequentialTransition();
		
		ScaleTransition hide = new ScaleTransition(); //The back is scaled to a line to be invisible
		hide.setByX(-1);
		if (fast)
			hide.setDuration(Duration.millis(1));
		else
			hide.setDuration(Duration.millis(200));
		if (reversed)
			hide.setNode(cardFront);
		else
			hide.setNode(cardBack);
		
		ScaleTransition show = new ScaleTransition(); //The face is returned from a line to full size
		show.setByX(1);
		if (fast)
			show.setDuration(Duration.millis(1));
		else
			show.setDuration(Duration.millis(200));
		if (reversed)
			show.setNode(cardBack);
		else
			show.setNode(cardFront);
		
		fullMotion.getChildren().addAll(hide, show);
		
		return fullMotion;
	}
}
