public class MoneyFormatter {
	private String label = "";
	
	public MoneyFormatter(int money) {
		int digitCounter = 0;  
		if (money == 0)
			label = "$0";
		else {
			while (money != 0) {
				if (digitCounter % 3 == 0 && digitCounter != 0)
					label = money % 10 + " " + label;
				else
					label = money % 10 + label;
				money /= 10;
				digitCounter++;
			}
		label = "$" + label;
		}
		
	}
	
	public String toString() {
		return label;
	}
}
