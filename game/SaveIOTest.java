package game;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import org.junit.Test;

import players.Player;

public class SaveIOTest extends SaveIO {
	
	@Test
	public void testLoadState() {
		String[] linesInFile = {"You","1500","Adam", "500"};
		String[] lineInFile = {"25"};
		try {
			createFile("Save.txt", linesInFile);
			createFile("Blind.txt", lineInFile);
			Game MockGame = super.loadState();
			assertEquals("Player one's name should be You", "You", MockGame.getPlayerList().get(0).getName());
			assertEquals("Player one's stack should be 1500", 1500, MockGame.getPlayerList().get(0).getStack());
			assertEquals("Player two's name should be Adam", "Adam", MockGame.getPlayerList().get(1).getName());
			assertEquals("Player two's stack should be 1500", 500, MockGame.getPlayerList().get(1).getStack());
			assertEquals("Small blind should be 25", 25, MockGame.getSmallBlind());
		} catch (IOException e) {
			
		}	
	}
	@Test
	public void testSaveState() throws IOException {
		ArrayList<Player> testPlayer = new ArrayList<Player>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		BufferedReader saveState = new BufferedReader(new FileReader("Save.txt"));
		BufferedReader sBlind = new BufferedReader(new FileReader("Blind.txt"));
		String[] linesInFile1 = {"Kyle","1500","Adam", "500"};
		String[] linesInFile2 = {"25"};
		testPlayer.add(new Player("Kyle",1500));
		testPlayer.add(new Player("Adam",500));
		super.saveState(testPlayer, 25);
		String in = saveState.readLine();
		while (in != null) {
				name.add(in);
				in = saveState.readLine();
				stacks.add(Integer.parseInt(in));
				in = saveState.readLine();
		}
		
		String blind = sBlind.readLine();
		sBlind.close();
		saveState.close();
		if(name.get(0) != linesInFile1[0]) {
			assertEquals("Players name was saved incorrectly.", "Kyle", testPlayer.get(0).getName());
		} else if(name.get(1) != linesInFile1[2]) {
			assertEquals("Players name was saved incorrectly.", "Adam", testPlayer.get(1).getName());
		}
		if(stacks.get(0) != Integer.parseInt(linesInFile1[1])) {
			assertEquals("Players stack was saved incorrectly.", 1500, testPlayer.get(0).getStack());
		} else if(stacks.get(1) != Integer.parseInt(linesInFile1[3])) {
			assertEquals("Players stack was saved incorrectly.", 500, testPlayer.get(1).getName());
		}
		if(Integer.parseInt(blind) != Integer.parseInt(linesInFile2[0])) {
			assertEquals("The small blind was saved incorrectly.", 25, Integer.parseInt(blind));
		}
		
	}
	
	private void createFile(String filename, String[] lines) throws IOException {
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        for (String line : lines) {
        	output.println(line);
        }
		output.close();		
	}	

}
