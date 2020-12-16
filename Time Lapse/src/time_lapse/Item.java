package time_lapse;

import jig.Entity;

/*
 * Keeps track of traits and qualities of a single item
 */

public class Item extends Entity {
	
	private String type;
	private boolean activatable, isQuest;
	static long startTime, elapsedTime, start_cooldown, current_cooldown;
	private int ui_count;
	
	public String getType() { return type; }
	public boolean isActivatable() { return activatable; }
	public int getIncrement() { return ui_count; }
	public boolean isQuest() { return isQuest; }

	public Item(final float x, final float y, String type) {
		super(x, y);
		this.type = type;
		this.ui_count = 1;
		if(type == "accelerator" || type == "fiery")
			activatable = true;
		if(type == "machine_piece1" || type == "machine_piece2" || type == "machine_piece3"
				|| type == "machine_piece4" || type == "machine_piece5" || type == "machine_piece6"
				|| type == "machine_piece7" || type == "machine_piece8")
			isQuest = true;

	}
	
	public void increment() {
		ui_count += 1;
	}
	
	public static void startTimer(int c) {
		startTime = System.currentTimeMillis();
		start_cooldown = c;
	}
	
	public static long getCooldown() {
		elapsedTime = System.currentTimeMillis() - startTime;
		current_cooldown = start_cooldown - (elapsedTime / 1000) % 60;
		return current_cooldown;
	}
	
	public static Item pickRandItem() {
		// get a random num generator from 1 to 6
		int rand = (int)(Math.random() * (6 - 1)) + 1;
		String type = null;
		if(rand == 1) {
			type = "hammer";
		} else if(rand == 2) {
			type = "feather";
		} else if(rand == 3) {
			type = "shield";
		} else if(rand == 4) {
			type = "arrow";
		} else if(rand == 5) {
			type = "accelerator";
		} else if(rand == 6) {
			type = "fiery";
		}
		Item item = new Item(0, 0, type);
		return item;
	}
	/*
	 * this function returns true IF the percent chance happens,
	 * otherwise it returns false. used for percent drop of the item
	 */
	public static boolean randomGenerator(int percent) {
		double result = Math.random();
		if(result > percent)
			return true;
		else 
			return false;
	}
	
}
