package time_lapse;

import jig.Entity;

/*
 * Keeps track of traits and qualities of a single item
 */

public class Item extends Entity {
	
	private String name;
	private String type;
	
	public String getName() { return name; }
	public String getType() { return type; }
	

	public Item(String name, String type) {
		
		this.name = name;
		this.type = type;
		
		setType();
		
	}
	
	private void setType() {
		
		if(type == "arrow") {
			
		}
		
	}
	
}
