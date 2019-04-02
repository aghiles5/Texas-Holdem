package game;
import java.util.*;
import java.io.*;

public class SaveIOTest extends SaveIO {
	
	@Test
	public void testLoadState() {
		String[] linesInFile = {"Kyle","1500","Adam", "500"};
		createFile("Save.txt", linesInFile);
		assertEquals("Player one's name should be Kyle", "Kyle", super.getName().get(0));
		assertEquals("Player one's stack should be 1500",  1500, super.getStacks().get(0));
		assertEquals("Player two's name should be Adam", "Adam", super.getName().get(1));
		assertEquals("Player two's stack should be 1500",  500, super.getStacks().get(1));
	}
	
	@Test
	public void testClear() {
		
	}
	
	@Test
	public void testSaveState() {
		
	}
	
	private void createFile(String filename, String[] lines) throws IOException {
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        for (String line : lines) {
        	output.println(line);
        }
		output.close();		
	}
	}
}
