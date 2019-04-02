package game;
import java.util.*;
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
	public void testClear() {
		String[] linesInFile = {"Kyle","1500","Adam", "500","25"};
		createFile("Save.txt", linesInFile);
		String[] expectedLinesInFile = {""};
		assertFileContent("File is not clear.", expectedLinesInFile, "Save.txt");
		
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
	
	private void assertFileContent(String message, String[] expectedLinesInFile, String filename) {
		try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            int index = 0;
			String line = input.readLine();
			while (line != null) {
				if (index >= expectedLinesInFile.length) {
					fail(message + " Found more lines in file than expected.  Only expected " + expectedLinesInFile.length + " lines");
				}
				assertEquals(message + " testing line " + index + "in output file", expectedLinesInFile[index], line);
				line = input.readLine();
				index++;
			}
			assertEquals(message + " Expected more lines in output file.", expectedLinesInFile.length, index);
			input.close();
		} catch (IOException ioe) {
			fail("Unexpected exception when testing file content.");
		}		
	}	

}
