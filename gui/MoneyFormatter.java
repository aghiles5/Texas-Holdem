package gui;
/**
 * For money related integers this class produces a string form with a
 * proceeding dollar symbol and spaces in between groups of three digits, as is
 * the Canadian standard.
 * 
 * @author Adam Hiles
 * @version 03/27/19
 */
public class MoneyFormatter {
	private String label = "";
	
	/**
	 * On the creation of a new formatter the integer money amount passed will
	 * be used to create an appropriate string.
	 * 
	 * @param money the integer to be formatted
	 */
	public MoneyFormatter(int money) {
		int digitCounter = 0;  
		if (money == 0) //Zero amounts are equated to $0
			label = "$0";
		else {
			while (money != 0) { //The amount is reduced to zero eventually be division
				if (digitCounter % 3 == 0 && digitCounter != 0) //Thousand spaces are added as necessary
					label = " " + label;
				label = money % 10 + label;
				money /= 10; //The next digit is moved to
				digitCounter++;
			}
		label = "$" + label;
		}
		
	}
	
	/**
	 * The string created in the constructor is retrieved from here.
	 * 
	 * @return the formatted money string
	 */
	public String toString() {
		return label;
	}
}
