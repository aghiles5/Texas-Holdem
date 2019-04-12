package gui;

import java.util.Random;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * This class handles playing the soundtrack for the GUI version: Marty
 * Robbin's "Gunfighter Ballads and Trail Songs".
 * 
 * @author Adam Hiles
 * @version 04/10/19
 */
public class MusicPlayer {
	private final String[] filenames = {"/gunfighterBallads/01.mp3", "/gunfighterBallads/02.mp3", "/gunfighterBallads/03.mp3",
			"/gunfighterBallads/04.mp3", "/gunfighterBallads/05.mp3", "/gunfighterBallads/06.mp3", "/gunfighterBallads/07.mp3",
			"/gunfighterBallads/08.mp3", "/gunfighterBallads/09.mp3", "/gunfighterBallads/11.mp3", "/gunfighterBallads/12.mp3",
			"/gunfighterBallads/13.mp3", "/gunfighterBallads/14.mp3", "/gunfighterBallads/15.mp3"};
	private int prevIndex = -1;
	private MediaView view = new MediaView();
	
	/**
	 * When a track is called to play a random one will be chosen from the
	 * filename list that was not just played. At the end of the track the
	 * method will be called recursively to play music indefinitely.
	 */
	public void play() {
		int index = prevIndex;
		Random rand = new Random();
		while (index == prevIndex) { //A random filename index is chosen that was not just played
			index = rand.nextInt(filenames.length);
		}
		
		//The required objects are set up here: the url resource, media object, and media player
		URL resource = getClass().getResource(filenames[index]);
		//System.out.println(prevIndex + " " + index + " " + resource.toString()); //Debug message listing the previous index, current index, and filname to play
		Media medium = new Media(resource.toString());
		MediaPlayer player = new MediaPlayer(medium);
		player.setMute(true);
		
		view.setMediaPlayer(player); //The media player is set to the MediaView
		prevIndex = index; //The current index is set to the previous index
		player.play(); //The track is played
		
		player.setOnEndOfMedia(new Runnable() { //Handler for the end of a track
		    @Override
		    public void run() { //On the end of a track the current player is track and the method is called for the next track
		    	player.stop();
		        play();
		    }
		});
	}
}
