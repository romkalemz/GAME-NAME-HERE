package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.ResourceManager;

public class UIHandler {
	
	Image image;
	ArrayList<Item> items_collected;
	
	public UIHandler(Image image) {
		items_collected =new ArrayList<Item>();
		this.image = image;
	}
	
	public void addItem(Item item) {
		items_collected.add(item);
	}

	public void render(Game tl, Graphics g) {
		image.setFilter(Image.FILTER_LINEAR);
		g.drawImage(image, 300, 675);
		
		// draw all the items collected
		for (int i = 0; i < items_collected.size(); i++) {
			items_collected.get(i).setPosition(350 + (i*100), 750);
			items_collected.get(i).render(g);
		}
	}
	
}
