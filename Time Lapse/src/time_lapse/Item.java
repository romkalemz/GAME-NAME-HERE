package time_lapse;

import jig.Entity;

/*
 * Keeps track of traits and qualities of a single item
 */

public class Item extends Entity {
	
	private String type;
	private boolean activatable;
	static long startTime, elapsedTime, start_cooldown, current_cooldown;
	private int ui_count;
	
	public String getType() { return type; }
	public boolean isActivatable() { return activatable; }
	public int getIncrement() { return ui_count; }

	public Item(final float x, final float y, String type) {
		super(x, y);
		this.type = type;
		this.ui_count = 1;
		if(type == "accelerator" || type == "fiery")
			activatable = true;
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
	
}
