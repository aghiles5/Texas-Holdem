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
		
		for (int k = 0; k < copy.size(); k++) {
			name.add(copy.get(k).getName());
			stacks.add(copy.get(k).getStack());
		}
		
		try {
			out = new FileWriter(dataFile);
			writeScore = new BufferedWriter(out);
			for (int i = 0; i < super.getPlayerList().size(); i++) {
				//doesn't write with separate lines just one jumbled mess
				writeScore.write(name.get(i));
				writeScore.write(stacks.get(i));
			}
		} catch (IOException e) {
			System.out.println("File not saved!");
			System.err.println("IOException: " + e.getMessage());
		}
	}
}