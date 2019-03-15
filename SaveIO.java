import java.io.*;
import java.util.ArrayList;

/**
 * Saves All names and their stacks in a file.
 * @author Kyle Wen
 * @version March 15, 2019
 */
public class SaveIO extends Game {
	/**
	 * pre: none
	 * post: The players' names and their stack values are saved to a .txt file.
	 */
	public void SaveState(){
		/*Copies the player list to a new ArrayList*/
		ArrayList<Player> copy = new ArrayList<Player>(super.getPlayerList());
		/*Two separate lists contain the name and stack of each player*/
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		int saves = 1;
		//writes to a new file each save
		File dataFile = new File("Save" + saves + ".txt");
		//how to load?
		FileWriter out;
		BufferedWriter writeScore;
		
		//adds the name and stack of each player to a separate list
		for (int k = 0; k < copy.size(); k++) {
			name.add(copy.get(k).getName());
			stacks.add(copy.get(k).getStack());
		}
		
		//writes to file
		try {
			out = new FileWriter(dataFile); //output stream
			writeScore = new BufferedWriter(out);
			for (int i = 0; i < super.getPlayerList().size(); i++) {
				writeScore.write(name.get(i));
				writeScore.newLine();
				writeScore.write(stacks.get(i));
				writeScore.newLine();
			}
			writeScore.close();
			out.close();
			System.out.println("Game Saved.");
		} catch (IOException e) {
			System.out.println("Game not saved!");
			System.err.println("IOException: " + e.getMessage());
		}
	}
}