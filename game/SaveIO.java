package game;
import java.io.*;
import java.util.ArrayList;

import players.Player;

/**
 * Saves All names and their stacks in a file.
 * @author Kyle Wen
 * @version March 30, 2019
 */
//JUNIT TESTING!!!
public class SaveIO {
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<Integer> stacks = new ArrayList<Integer>();
	
	public void clear() {
		File dat = new File("Save.txt");
		FileWriter out;
		BufferedWriter stuff;
		try {
			out = new FileWriter(dat);
			stuff = new BufferedWriter(out);
			stuff.write("");
			stuff.close();
		} catch (IOException e) {
			System.out.println("File not cleared.");
			System.out.println("IOException: " + e.getMessage());
		}
	}	
	
	/**
	 * pre: none
	 * post: The players' names and their stack values are saved to a .txt file.
	 */
	public void saveState(ArrayList<Player> players, int smlBlind){
		/*Copies the player list to a new ArrayList*/
		ArrayList<Player> copy = new ArrayList<Player>(players);
		/*Two separate lists contain the name and stack of each player*/
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> stacks = new ArrayList<Integer>();
		//writes to a new file each save
		File dataFile = new File("Save.txt");
		FileWriter out;
		BufferedWriter writeScore;
		int smallBlind = smlBlind;
		
		//adds the name and stack of each player to a separate list
		for (int k = 0; k < copy.size(); k++) {
			name.add(copy.get(k).getName());
			stacks.add(copy.get(k).getStack());
		}
		
		//writes to file
		try {
			out = new FileWriter(dataFile); //output stream
			writeScore = new BufferedWriter(out);
			for (int i = 0; i < copy.size(); i++) {
				writeScore.write(name.get(i));
				writeScore.newLine();
				writeScore.write(stacks.get(i));
				writeScore.newLine();
			}
			writeScore.write(Integer.toString(smallBlind));
			writeScore.close();
			out.close();
			System.out.println("Game Saved.");
		} catch (IOException e) {
			System.out.println("Game not saved.");
			System.err.println("IOException: " + e.getMessage());
		}
	}
	
	/**
	 * pre: A file exists containing the names and stacks of each player
	 * post: The names and stacks of each existing player have been passed to the loadPlayers method in the super class.
	 */
	public Game loadState() {
		int smlBlind = 0;
		try {
			//Opens a BufferedReader stream
			BufferedReader saveState = new BufferedReader(new FileReader("Save.txt"));
			String names = "";
			int Money;
			try {
				//two file approach if doesn't work
				while (names != null) {
					names = saveState.readLine();
					if(names != null) {
						name.add(names);
					}
					if(names != null) {
						Money = Integer.parseInt(saveState.readLine());
						stacks.add(Money);
					}
				}
				smlBlind = Integer.parseInt(name.get(name.size() - 1));
				saveState.close();
			} catch (IOException e) {
				System.out.println("File could not be read.");
				System.err.println("IOException: " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Game could not be loaded.");
			System.err.println("IOException: " + e.getMessage());
		}
		clear();
		Game nGame = new Game(name, stacks, smlBlind);
		return nGame;
	}
	
	public ArrayList<String> getName() {
		return name;
	}
	
	public ArrayList<Integer> getStacks() {
		return stacks;
	}
}