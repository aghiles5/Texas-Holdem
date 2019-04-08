package gui;

import java.util.Random;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MusicPlayer {
	private final String[] filenames = {"/gunfighterBallads/01.mp3", "/gunfighterBallads/02.mp3", "/gunfighterBallads/03.mp3",
			"/gunfighterBallads/04.mp3", "/gunfighterBallads/05.mp3", "/gunfighterBallads/06.mp3", "/gunfighterBallads/07.mp3",
			"/gunfighterBallads/08.mp3", "/gunfighterBallads/09.mp3", "/gunfighterBallads/11.mp3", "/gunfighterBallads/12.mp3",
			"/gunfighterBallads/13.mp3", "/gunfighterBallads/14.mp3", "/gunfighterBallads/15.mp3"};
	private int prevIndex = -1;
	private MediaView view = new MediaView();
	
	public void play() {
		int index = prevIndex;
		Random rand = new Random();
		while (index == prevIndex) {
			index = rand.nextInt(filenames.length);
		}
		
		URL resource = getClass().getResource(filenames[index]);
		//System.out.println(prevIndex + " " + index + " " + resource.toString()); //Debug message listing the previous index, current index, and filname to play
		Media medium = new Media(resource.toString());
		MediaPlayer player = new MediaPlayer(medium);
		view.setMediaPlayer(player);
		
		prevIndex = index;
		player.play();
		
		player.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		    	player.stop();
		        play();
		    }
		});
	}
}
