package donkey;

import java.util.ArrayList;

public class Player {

	ArrayList<Card> hand = new ArrayList<Card>();
	private String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean hasDoubles() {
		
		for (int i=0; i<hand.size(); i++) {
			Card first = hand.get(i);
			for (int j=0; j<hand.size(); j++) {
				Card second = hand.get(j);
				if (first.equals(second) && i != j ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void removeDoubles() {
		for (int i=0; i<hand.size(); i++) {
			Card first = hand.get(i);
			for (int j=0; j<hand.size(); j++) {
				Card second = hand.get(j);
				if (first.equals(second) && i != j ) {
					System.out.println(this.name + " placed a set of " + first.getCardName() + "!");
					hand.remove(first);
					hand.remove(second);
				}
			}
		}
	}
	
	public boolean hasFinished() {
		if (hand.size() == 0) {
			return true;
		}
		return false;
	}
}
