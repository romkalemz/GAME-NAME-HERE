package time_lapse;

import jig.Entity;

/*
 * Keeps track of traits and qualities of a single item
 */

public class Item extends Entity {
	
	private String type;
	private boolean activatable;
	static long startTime, elapsedTime, start_cooldown, current_cooldown;
	
	public String getType() { return type; }
	public boolean isActivatable() { return activatable; }

	public Item(final float x, final float y, String type) {
		super(x, y);
		this.type = type;
		if(type == "accelerator")
			activatable = true;
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
	
}
