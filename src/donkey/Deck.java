package donkey;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Deck {

	Card[] deck = new Card[37];
	Set<Integer> indexes = new HashSet<Integer>();
	
	public Deck() {
		
		int count = 0;
		
		for (int i=0; i<2; i++) {
			deck[count++] = new Card("CAT");
			deck[count++] = new Card("DOG");
			deck[count++] = new Card("HORSE");
			deck[count++] = new Card("COW");
			deck[count++] = new Card("TURKEY");
			deck[count++] = new Card("SHEEP");
			deck[count++] = new Card("SHEEPDOG");
			deck[count++] = new Card("WABBIT");
			deck[count++] = new Card("DUCK");
			deck[count++] = new Card("FROGSTER");
			deck[count++] = new Card("PIG");
			deck[count++] = new Card("CHICKEN");
			deck[count++] = new Card("TRACTOR");
			deck[count++] = new Card("WHEAT");
			deck[count++] = new Card("MR.SEED");
			deck[count++] = new Card("WINDMILL");
			deck[count++] = new Card("SCARECROW");
			deck[count++] = new Card("FARMHOUSE");
		}
		deck[count++] = new Card("DONKEY");
	}
	
	public Card distribute() {
		Random n = new Random();
		int index = n.nextInt(37);
		Card x = deck[index];
		
		while (indexes.contains(index)) {
			index = n.nextInt(37);
			x = deck[index];
		}
		
		indexes.add(index);
		
		return x;
	}
}
