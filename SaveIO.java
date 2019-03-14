import java.io.*;
import java.util.ArrayList;

/**
 * Saves All names and their stacks in a file.
 * @author Kyle Wen
 *
 */
public class SaveIO extends Game {
	public void SaveState(){
		ArrayList<Player> copy = new ArrayList<Player>(super.getPlayerList());
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		File dataFile = new File("Save1.txt");
		FileWriter out;
		BufferedWriter writeScore;
		
		name = copy.getName(); //Need method to get name.
		stacks = copy.getStack(); //Need method to get stack.
		
		try {
			out = new FileWriter(dataFile);
			writeScore = new BufferedWriter(out);
			for (int i = 0; i < super.getPlayerList().size(); i++) {
				writeScore.write(name.get(i));
				writeScore.write(stacks.get(i));
			}
		} catch (IOException e) {
			System.out.println("File not saved!");
			System.err.println("IOException: " + e.getMessage());
		}
	}
}