package gui;

import java.util.Random;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	private final String[] filenames = {"/gunfighterBallads/01.mp3", "/gunfighterBallads/02.mp3", "/gunfighterBallads/09.mp3"};
	private int prevIndex = -1;
	
	public void play() {
		int index = -1;
		Random rand = new Random();
		while (index == prevIndex) {
			index = rand.nextInt(filenames.length);
		}
		
		URL resource = getClass().getResource(filenames[index]);
		Media medium = new Media(resource.toString());
		MediaPlayer player = new MediaPlayer(medium);
		
		player.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		        play();
		    }
		});
		
		player.play();
	}
}
