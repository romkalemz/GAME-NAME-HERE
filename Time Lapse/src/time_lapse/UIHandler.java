package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.ResourceManager;

public class UIHandler {
	
	Image image_items, image_activate;
	ArrayList<Item> items_collected;
	Item activatable;
	
	public UIHandler(Image i1) {
		items_collected =new ArrayList<Item>();
		//this.image_activate = i2;
		this.image_items = i1;
	}
	
	public void addItem(Item item) {
		if(item.isActivatable())
			activatable = item;
		else
		items_collected.add(item);
	}

	public void render(Game tl, Graphics g) {
		// offset difference of map scrolled
		float translateX = tl.map.getTranslateX();
		float translateY = tl.map.getTranslateY();
		
		image_items.setFilter(Image.FILTER_LINEAR);
		image_activate = image_items.getScaledCopy(120, 60);
		
		// draw the elements of the UI (box, stats)
		g.drawImage(image_items, 350 - translateX, 675 - translateY);
		if(activatable != null) {
			g.drawImage(image_activate, 540 - translateX, 10 - translateY);
			activatable.setPosition(625 - translateX, 40 - translateY);
			activatable.render(g);
		}
		
		g.drawString("Movement Speed: " + (int)(tl.player.getMovementSpeed() *100), 25 - translateX, 710 - translateY);
		g.drawString("Attack Speed: " + (int)(tl.player.getRateOfFire() / 100), 25 - translateX, 735 - translateY);
		g.drawString("Attack Damage: " + (int)(tl.player.getAttackDamage()), 25 - translateX, 760 - translateY);
		//g.drawString("Score: "+score, 550, 10);
		
		// draw all the items collected
		for (int i = 0; i < items_collected.size(); i++) {
			items_collected.get(i).setPosition(400 + (i*50) - translateX, 750 - translateY);
			items_collected.get(i).render(g);
		}
	}
	
}
