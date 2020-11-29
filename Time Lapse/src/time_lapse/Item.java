package time_lapse;

import jig.Entity;

/*
 * Keeps track of traits and qualities of a single item
 */

public class Item extends Entity {
	
	private String type;
	
	public String getType() { return type; }
	

	public Item(final float x, final float y, String type) {
		super(x, y);
		this.type = type;
		
	}
	
}