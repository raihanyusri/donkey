package donkey;

import java.util.Random;
import java.util.Scanner;

public class DonkeyGame {
	
	Scanner sc = new Scanner(System.in);
	HumanPlayer you = new HumanPlayer();
	Player player1 = new Player("Jacob");
	Player player2 = new Player("Betty");
	Player player3 = new Player("Pablo");
	private boolean donkey = false;

	public void start() {
		System.out.println("Do you want to play 'Donkey'? (Yes/No)");
		String ans = sc.nextLine().toLowerCase();
		
		if (ans.equals("yes")) {
			showRules();
			setup();
		}
		else if (ans.equals("no")){
			System.out.println("Ok then... :(");
		}
		else {
			System.out.println("Please key in either 'Yes' or 'No'.");
			start();
		}
	}
	
	public void setup() {
		you.hand.clear();
		player1.hand.clear();
		player2.hand.clear();
		player3.hand.clear();
		donkey = false;
		Deck mydeck = new Deck();
		
		//deal cards to players (36 cards out of 37)
		for (int i=1; i<=9; i++) {
			you.hand.add(mydeck.distribute());
			player1.hand.add(mydeck.distribute());
			player2.hand.add(mydeck.distribute());
			player3.hand.add(mydeck.distribute());
		}
		
		Random r = new Random();
		int index = r.nextInt(4);
		
		//decide who gets the extra card
		if (index == 0) {
			you.hand.add(mydeck.distribute());
		}
		else if (index == 1) {
			player1.hand.add(mydeck.distribute());
		}
		else if (index == 2) {
			player2.hand.add(mydeck.distribute());
		}
		else if (index == 3) {
			player3.hand.add(mydeck.distribute());
		}
		events();
	}
	
	public void showRules() {
		System.out.println("Great! Here are the rules: ");
		System.out.println("- Each player will be dealt either 9 or 10 cards randomly.");
		System.out.println("- Any pair of cards that a player has will be placed down.");
		System.out.println("- To place a pair of cards down, enter the name of ONE pair.");
		System.out.println("- For example, if you have the cards 'DUCK CHICKEN DUCK CAT DOG' in your hand, enter \n'DUCK' to place the 'DUCK' pair down, not 'DUCK DUCK'.");
		System.out.println("- Amongst the cards dealt, there is a pairless 'DONKEY'.");
		System.out.println("- Each player will offer their hand face down to the player on their left, who draws \none card from it randomly.");
		System.out.println("- This player places down any pair that may have been formed by the drawn card.");
		System.out.println("- This player then offers their hand face down to the player on their left,  and so on.");
		System.out.println("- The player left with the pairless 'DONKEY' is declared the donkey and loses!");
		System.out.println();
		System.out.println("You are playing with 3 computers - Jacob, Betty and Pablo.");
		System.out.println("Each round will go as follows: You -> Jacob -> Betty -> Pablo");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println();
	}
	
	public void events() {
		humanPlace(you); //you place your pairs, players place their pairs 
		
		while (donkey == false) {
			
			if (player1.hasFinished() && player2.hasFinished() && player3.hasFinished()) {
				System.out.println("You lost! You are the donkey!");
				playAgain();
				donkey = true;
				
			}
			//you take a card
			if (!player3.hasFinished() && !you.hand.isEmpty()) {
				humanMove(you, player3);
			}
			else if (!player2.hasFinished() && !you.hand.isEmpty()) {
				humanMove(you, player2);
			}
			else if (!player1.hasFinished() && !you.hand.isEmpty()) {
				humanMove(you, player1);
			}
			//you place a pair if you have a new pair
			if (!you.hand.isEmpty()) {
				humanPlace(you);
			}
			
			System.out.println("EVENTS:");
			//player 1 move
			//you havent finished, p1 takes from you || you finished, p3 hasnt finished, p1 takes from p3
			if (!player1.hasFinished() && !you.hand.isEmpty() || !player1.hasFinished() && you.hand.isEmpty() && !player3.hasFinished()) {
				playerMove(player1, player3, you);
			}
			//you finished, p3 finished, p1 takes from p2
			else if (!player1.hasFinished() && you.hand.isEmpty() && player3.hasFinished() && !player2.hasFinished()) {
				playerMove(player1, player2, you);
			}
			else if (!player1.hasFinished() && you.hand.isEmpty() && player3.hasFinished() && player2.hasFinished()) {
				System.out.println(player1.getName() + " is the donkey!");
				playAgain();
				donkey = true;
				
			}
			
			//player 2 move
			//p1 hasnt finished, p2 takes from p1 || p1 finished, you havent finished, p2 takes from you
			if (!player2.hasFinished() && !player1.hasFinished() || !player2.hasFinished() && player1.hasFinished() && !you.hand.isEmpty()) {
				playerMove(player2, player1, you);
			}
			//p1 finished, you finished, p2 takes from p3
			else if (!player2.hasFinished() && player1.hasFinished() && you.hand.isEmpty() && !player3.hasFinished()) {
				playerMove(player2, player3, you);
			}
			//p1 finished, you finished, p3 finished
			else if (!player2.hasFinished() && player1.hasFinished() && you.hand.isEmpty() && player3.hasFinished()) {
				System.out.println(player2.getName() + " is the donkey!");
				playAgain();
				donkey = true;
			}
			
			//player 3 move
			//p2 hasnt finished, p3 takes from p2
			if (!player3.hasFinished() && !player2.hasFinished()) {
				playerMove(player3, player2, you);
			}
			//p2 finished, p3 takes from p1 || p2 finished, p1 finished, p3 takes from you
			else if (!player3.hasFinished() && player2.hasFinished() && !player1.hasFinished() || !player3.hasFinished() && player2.hasFinished() && !you.hand.isEmpty()) {
				playerMove(player3, player1, you);
			}
			else if (!player3.hasFinished() && player2.hasFinished() && player1.hasFinished() && you.hand.isEmpty()) {
				System.out.println(player3.getName() + " is the donkey!");
				playAgain();
				donkey = true;
			}
			
			
		}
		
	}
	
	public void humanPlace(HumanPlayer you) {
		you.seeHand(you.hand);
		if (you.hand.isEmpty()) {
			System.out.println("Congratulations! This is how the rest of the game turned out:");
			System.out.println();
		}
		else {
			System.out.println();
			System.out.println("Enter a card pair that you would like to place down.");
			System.out.println("Note that you only need to key in ONE of the card names eg. 'DUCK' and not 'DUCK DUCK'.");
			System.out.println("If you have no more pairs to place, enter 's'.");
			String input = sc.nextLine().toUpperCase();
			
			if (you.isADouble(input) == true) {
				you.toRemove(input);
				humanPlace(you);
			}
			else if (input.equals("S")) {
				System.out.println("--------------------------------------------------------------------------");
				while (player1.hasDoubles()) {
					playerPlace(player1);
				}
				while (player2.hasDoubles()) {
					playerPlace(player2);
				}
				while (player3.hasDoubles()) {
					playerPlace(player3);
				}
				System.out.println();
			}
			else {
				System.out.println("Something went wrong! Either check your spelling or the card you indicated does not have a pair.");
				System.out.println();
				humanPlace(you);
			}
		}
		
		
		
	}
	
	
	public void humanMove(HumanPlayer you, Player p) {
		System.out.println("You are now drawing a card from " + p.getName() + ":");
		System.out.println("Enter a number from 1 to " + p.hand.size() + " to draw a card from " + p.getName() + ".");
		String str = sc.nextLine();
		int n = 0;
		try {
			n = Integer.parseInt(str);
		}
		catch (Exception e) {
			System.out.println("Please key in a number from 1 to " + p.hand.size() + ".");
		}
		
		
		if (n > p.hand.size() || n <= 0) {
			System.out.println("Invalid number! Try again.");
			System.out.println();
			humanMove(you, p);
		}
		else {
			int index = n-1;
			Card get = p.hand.get(index);
			p.hand.remove(index);
			if (p.hasFinished()) {
				System.out.println(p.getName() + " has finished!");
			}
			you.hand.add(get);
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("You got " + get.getCardName() + " from " + p.getName() + "!");
			
		}
	}
	
	public void playerMove(Player pget, Player pgive, HumanPlayer you) {
		Random r = new Random();
		if (!pget.hasFinished()) { //has not finished
			if (pget.equals(player1)) {
				//player 1 takes a card from you if I have not finished
				if (!you.hand.isEmpty()) {
					int index = 0;
					if (you.hand.size() > 1) {
						index = r.nextInt(you.hand.size());
					}
					else {
						index = 0;
					}
					Card get = you.hand.get(index);
					you.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took your " + get.getCardName() + "!");
					if (you.hand.isEmpty()) {
						you.seeHand(you.hand);
						System.out.println("Congratulations! This is how the rest of the game turned out:");
						System.out.println();
					}
					playerPlace(pget);
					if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
				//if I have finished, player 1 takes a card from player 2/3
				else {
					int index = 0;
					if (pgive.hand.size() > 1) {
						index = r.nextInt(pgive.hand.size());
					}
					else {
						index = 0;
					}
					Card get = pgive.hand.get(index);
					pgive.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took a card from " + pgive.getName() + ".");
					playerPlace(pget);
					if (pgive.hasFinished()) {
						System.out.println(pgive.getName() + " has finished!");
					}
					if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
			}
			if (pget.equals(player2)) {
				//player 2 takes a card from player 1 is player 1 has not finished OR
				//if player 1 and you have finished, player 2 takes a card from player 3
				if (!player1.hasFinished() || (player1.hasFinished() && you.hand.isEmpty())) {
					int index = 0;
					if (pgive.hand.size() > 1) {
						index = r.nextInt(pgive.hand.size());
					}
					else {
						index = 0;
					}
					Card get = pgive.hand.get(index);
					pgive.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took a card from " + pgive.getName() + ".");
					playerPlace(pget);
					if (pgive.hasFinished()) {
						System.out.println(pgive.getName() + " has finished!");
					}
					else if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
				else {
					//if player 1 has finished, player 2 takes a card from you
					int index = 0;
					if (you.hand.size() > 1) {
						index = r.nextInt(you.hand.size());
					}
					else {
						index = 0;
					}
					Card get = you.hand.get(index);
					you.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took your " + get.getCardName() + "!");
					if (you.hand.isEmpty()) {
						you.seeHand(you.hand);
						System.out.println("Congratulations! This is how the rest of the game turned out:");
						System.out.println();
					}
					playerPlace(pget);
					if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
			}
			if (pget.equals(player3)) {
				//player 3 takes a card from player 2 if player 2 has not finished
				//if player 2 has finished, player 3 takes a card from player 1
				if (!player2.hasFinished() || (player2.hasFinished() && !player1.hasFinished())) {
					int index = 0;
					if (pgive.hand.size() > 1) {
						index = r.nextInt(pgive.hand.size());
					}
					else {
						index = 0;
					}
					Card get = pgive.hand.get(index);
					pgive.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took a card from " + pgive.getName() + ".");
					playerPlace(pget);
					if (pgive.hasFinished()) {
						System.out.println(pgive.getName() + " has finished!");
					}
					if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
				else {
					//if player 2 and player 1 have finished, player 3 takes a card from you
					int index = 0;
					if (you.hand.size() > 1) {
						index = r.nextInt(you.hand.size());
					}
					else {
						index = 0;
					}
					Card get = you.hand.get(index);
					you.hand.remove(index);
					pget.hand.add(get);
					System.out.println(pget.getName() + " took your " + get.getCardName() + ".");
					if (you.hand.isEmpty()) {
						you.seeHand(you.hand);
						System.out.println("Congratulations! This is how the rest of the game turned out:");
						System.out.println();
					}
					playerPlace(pget);
					if (pget.hand.size() == 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " card left.");
					}
					else if (pget.hand.size() > 1) {
						System.out.println(pget.getName() + " has " + pget.hand.size() + " cards left.");
					}
					System.out.println();
				}
			}
		}
		
	}
	
	public void playerPlace(Player p) {
		while (p.hasDoubles() == true) {
			p.removeDoubles();
			if (p.hasFinished()) {
				System.out.println(p.getName() + " has finished!");
			}
		}
	}
	
	public void playAgain() {
		System.out.println("Would you like to play again? (Yes/No)");
		String ans = sc.nextLine().toLowerCase();
		if (ans.equals("yes")) {
			showRules();
			setup();
		}
		else if (ans.equals("no")){
			System.out.println("Ok then... :(");
		} 
		else {
			System.out.println("Please key in either 'Yes' or 'No'.");
			start();
		}
	}
	


}
