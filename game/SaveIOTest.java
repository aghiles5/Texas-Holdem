package game;
import java.util.*;

import players.Player;

import java.io.*;

public class SaveIOTest extends SaveIO {
	
	@Test
	public void testLoadState() {
		String[] linesInFile = {"Kyle","1500","Adam", "500","25"};
		createFile("Save.txt", linesInFile);
		assertEquals("Player one's name should be Kyle", "Kyle", super.getName().get(0));
		assertEquals("Player one's stack should be 1500",  1500, super.getStacks().get(0));
		assertEquals("Player two's name should be Adam", "Adam", super.getName().get(1));
		assertEquals("Player two's stack should be 1500",  500, super.getStacks().get(1));
		assertEquals("Small blind should be 25", 25, super.getSmallBlind());
	}
	
	@Test
	//not necessary???
	public void testClear() {
		String[] linesInFile = {"Kyle","1500","Adam", "500"};
		createFile("Save.txt", linesInFile);
		String[] expectedLinesInFile = {""};
		assertFileContent("File is not clear.", expectedLinesInFile, "Save.txt");
		
	}
	
	@Test
	public void testSaveState() throws IOException {
		ArrayList<Player> testPlayer = new ArrayList<Player>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		int smallBlind;
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
		sBlind.close();
		saveState.close();
		if(name.get(0) != linesInFile1[0]) {
			fail("Players name was saved incorrectly.", "Kyle", name.get(0));
		} else if(name.get(1) != linesInFile1[2]) {
			fail("Players name was saved incorrectly.", "Adam", name.get(1));
		}
		if(stacks.get(0) != Integer.parseInt(linesInFile1[1])) {
			fail("Players name was saved incorrectly.", 1500, stacks.get(0));
		} else if(stacks.get(1) != Integer.parseInt(linesInFile1[3])) {
			fail("Players name was saved incorrectly.", 500, stacks.get(2));
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
