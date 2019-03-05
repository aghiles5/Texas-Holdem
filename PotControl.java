import java.util.ArrayList;

//frame work for the money stuff will be done here.
//AI and Player objects need a base money value on start up.
//needs: a way to check the current highest bet, blinds
public class PotControl {
	public static int POT;
	public static int[] blinds = new int[2]; //can be done in main?
	//track the current money value
	public static ArrayList<Integer> PlayerMoney = new ArrayList<Integer>();
	
	public int[] setBlind(int smlBlind) {
		blinds[0] = smlBlind;
		blinds[1] = smlBlind*2;
		return blinds;
	}
	
	public void getBlinds() {
		//not the best for now
		System.out.println("Small Blind: " + blinds[0] + "\nBig Blind: " + blinds[1]);
	}
	
	public int CalcPot() {
		for (int i = 0; i < Main.players.size(); i++) {
			POT += players(i).getBet();
		}
		
		return POT;
	}
}
