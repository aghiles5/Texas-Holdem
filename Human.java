
public class Human extends Player {
	public Human(String n) {
		super.name = n;
	}

  	// Only implementing check and fold for now since there is no money available
  	public void getDecision(String decision) {
		decision = super.invalidChoice(decision);
		//super.call(decision, currentBet);
		//super.BetRaise(decision, newBet);
		//super.allIn(decision, currentBet);
		super.check(decision);
		super.fold(decision);
	}

}
