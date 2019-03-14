import java.io.*;

public class SaveIO {
	public void SaveState(){
		File dataFile = new File("Save1.txt");
		FileWriter out;
		BufferedWriter writeScore;
		
		try {
			
		} catch (IOException e) {
			System.out.println("File not saved!");
			System.err.println("IOException: " + e.getMessage());
		}
	}
}
