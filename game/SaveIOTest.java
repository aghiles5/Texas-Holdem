package game;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import org.junit.Test;

import players.Player;

public class SaveIOTest extends SaveIO {
	
	//tests the loadState method
	@Test
	public void testLoadState() {
		//expected lines in the Save file
		String[] linesInFile = {"You","1500","Adam", "500"};
		//expected line in the Blind file
		String[] lineInFile = {"25"};
		try {
			//creates the two files
			createFile("Save.txt", linesInFile);
			createFile("Blind.txt", lineInFile);
			//tests the results of the load through a new Game object
			Game MockGame = super.loadState();
			assertEquals("Player one's name should be You", "You", MockGame.getPlayerList().get(0).getName());
			assertEquals("Player one's stack should be 1500", 1500, MockGame.getPlayerList().get(0).getStack());
			assertEquals("Player two's name should be Adam", "Adam", MockGame.getPlayerList().get(1).getName());
			assertEquals("Player two's stack should be 1500", 500, MockGame.getPlayerList().get(1).getStack());
			assertEquals("Small blind should be 25", 25, MockGame.getSmallBlind());
		} catch (IOException e) {
			//Does nothing
		}	
	}
	
	//tests the saveState method
	@Test
	public void testSaveState() throws IOException {
		//Create test objects and streams
		ArrayList<Player> testPlayer = new ArrayList<Player>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		BufferedReader saveState = new BufferedReader(new FileReader("Save.txt"));
		BufferedReader sBlind = new BufferedReader(new FileReader("Blind.txt"));
		
		//expected lines in Save.txt
		String[] linesInFile1 = {"Kyle","1500","Adam", "500"};
		//expected line in Blind.txt
		String[] linesInFile2 = {"25"};
		//adds two players to the testPlayer list
		testPlayer.add(new Player("Kyle",1500));
		testPlayer.add(new Player("Adam",500));
		//writes the players and small blind to separate files
		super.saveState(testPlayer, 25);
		
		//tests the output of the file to see if it matches the expected output
		String in = saveState.readLine();
		while (in != null) {
				name.add(in);
				in = saveState.readLine();
				stacks.add(Integer.parseInt(in));
				in = saveState.readLine();
		}
		
		String blind = sBlind.readLine();
		//closes the streams
		sBlind.close();
		saveState.close();
		
		//checks to see if the lines match
		assertEquals("Players name was saved incorrectly.", "Kyle", testPlayer.get(0).getName());
		assertEquals("Players name was saved incorrectly.", "Adam", testPlayer.get(1).getName());
		assertEquals("Players stack was saved incorrectly.", 1500, testPlayer.get(0).getStack());
		assertEquals("Players stack was saved incorrectly.", 500, testPlayer.get(1).getStack());
		assertEquals("The small blind was saved incorrectly.", 25, Integer.parseInt(blind));
	}
	
	private void createFile(String filename, String[] lines) throws IOException {
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        for (String line : lines) {
        	output.println(line);
        }
		output.close();		
	}	

}
