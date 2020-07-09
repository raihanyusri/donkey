package donkey;

import java.util.ArrayList;

public class HumanPlayer {
	
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public void seeHand(ArrayList<Card> hand) {
		if(hand.size() == 0) {
			System.out.println("You finished!");
		}
		else {
			System.out.println("You have " + hand.size() + " cards:");
			for (Card x : hand) {
				System.out.print(x.getCardName() + " ");
			}
			System.out.println();
		}
		
	}
	
	public boolean isADouble(String cardname) {
		
		Card check = new Card(cardname);
		for (int i=0; i<hand.size(); i++) {
			if (hand.get(i).equals(check)) {
				for (int j=0; j<hand.size(); j++) {
					if (hand.get(j).equals(check) && hand.get(i) != hand.get(j)) {
						return true;
					}
				}
			}
		}

		return false;
	}
	
	public void toRemove(String cardname) {
		Card toremove = new Card(cardname);
		hand.remove(toremove);
		hand.remove(toremove);
	}

}
