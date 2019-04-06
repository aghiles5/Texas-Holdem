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
	private int smallBlind;
	
	public void clear() {
		File dat = new File("Save.txt");
		File blind = new File("Blind.txt");
		FileWriter out;
		FileWriter b;
		BufferedWriter stuff;
		BufferedWriter cBlind;
		try {
			out = new FileWriter(dat);
			stuff = new BufferedWriter(out);
			stuff.write("");
			b = new FileWriter(blind);
			cBlind = new BufferedWriter(b);
			cBlind.write("");
			cBlind.close();
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
		File blind = new File("Blind.txt");
		FileWriter out;
		FileWriter nblind;
		BufferedWriter writeScore;
		BufferedWriter writeBlind;
		
		//adds the name and stack of each player to a separate list
		for (int k = 0; k < copy.size(); k++) {
			name.add(copy.get(k).getName());
			stacks.add(copy.get(k).getStack());
		}
		
		//writes to file
		try {
			out = new FileWriter(dataFile); //output stream
			nblind = new FileWriter(blind);
			writeScore = new BufferedWriter(out);
			writeBlind = new BufferedWriter(nblind);
			writeBlind.write(Integer.toString(smlBlind));
			for (int i = 0; i < copy.size(); i++) {
				writeScore.write(name.get(i));
				writeScore.newLine();
				writeScore.write(Integer.toString(stacks.get(i)));
				writeScore.newLine();
			}
			writeBlind.close();
			writeScore.close();
			nblind.close();
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
		try {
			//Opens a BufferedReader stream
			BufferedReader saveState = new BufferedReader(new FileReader("Save.txt"));
			BufferedReader sBlind = new BufferedReader(new FileReader("Blind.txt"));
			try {
				smallBlind = Integer.parseInt(sBlind.readLine());
				//two file approach if doesn't work
				String in = saveState.readLine();
				while (in != null) {
						name.add(in);
						in = saveState.readLine();
						stacks.add(Integer.parseInt(in));
						in = saveState.readLine();
				}
				sBlind.close();
				saveState.close();
				
			} catch (IOException e) {
				System.out.println("File could not be read.");
				System.err.println("IOException: " + e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Game could not be loaded.");
			System.err.println("IOException: " + e.getMessage());
		}
		Game nGame = new Game(name, stacks, smallBlind);
		return nGame;
	}
	
	public ArrayList<String> getName() {
		return name;
	}
	
	public ArrayList<Integer> getStacks() {
		return stacks;
	}
	
	public int getSmallBlind() {
		return smallBlind;
	}
}