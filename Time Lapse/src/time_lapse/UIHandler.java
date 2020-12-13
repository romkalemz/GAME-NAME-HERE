package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.ResourceManager;

public class UIHandler {
	
	Image image_items, image_activate;
	ArrayList<Item> items_collected;
	Item activatable;
	boolean show_cooldown;
	
	public void setActivatable(Item i) { activatable = i; }
	public Item getActivatable() { return activatable; }
	
	public void showTimer() { show_cooldown = true; }
	
	public UIHandler(Image i1) {
		items_collected =new ArrayList<Item>();
		//this.image_activate = i2;
		this.image_items = i1;
		show_cooldown = false;
	}
	
	public void reset() {
		items_collected.clear();
		activatable = null;
	}
	
	public void addItem(Item item) {
		// first check if this kind of item already exists in the UI
		for(int i = 0; i < items_collected.size(); i++) {
			//System.out.println(item.getType()+" "+items_collected.get(i).getType());
			if(item.getType().equals(items_collected.get(i).getType())) {
				System.out.println("FOUND A MATCH");
				// increment count of this item
				items_collected.get(i).increment();
				//System.out.println(item.getIncrement());
				return;
			}
		}
		
		if(item.isActivatable())
			setActivatable(item);
		else
			items_collected.add(item);
	}

	public void render(Game tl, Graphics g) {
		// offset difference of map scrolled
		float translateX = tl.map.getTranslateX();
		float translateY = tl.map.getTranslateY();
		
		image_items.setFilter(Image.FILTER_LINEAR);
		image_activate = image_items.getScaledCopy(120, 60);
		
		// draw the elements of the UI (box, stats, activatable)
		g.drawImage(image_items, 350 - translateX, 675 - translateY);

		if(activatable != null) {
			g.drawImage(image_activate, 540 - translateX, 10 - translateY);
			activatable.setPosition(625 - translateX, 40 - translateY);
			activatable.render(g);
			if(show_cooldown && Item.getCooldown() >= 0)
				g.drawString(""+Item.getCooldown(), 560 - translateX, 30 - translateY);
		}
		
		g.drawString("Movement Speed: " + (int)(tl.player.getMovementSpeed() *100), 25 - translateX, 710 - translateY);
		g.drawString("Attack Speed: " + (int)(tl.player.getRateOfFire() / 100), 25 - translateX, 735 - translateY);
		g.drawString("Attack Damage: " + (int)(tl.player.getAttackDamage()), 25 - translateX, 760 - translateY);
		//g.drawString("Score: "+score, 550, 10);
		
		// draw all the items collected
		for (int i = 0; i < items_collected.size(); i++) {
			// draw a number next to the item if multiple of them have been collected
			//System.out.print(items_collected.get(i).getIncrement());
			if(items_collected.get(i).getIncrement() > 1)
				g.drawString(""+items_collected.get(i).getIncrement(), 415 + (i*50) - translateX, 770 - translateY);
			
			items_collected.get(i).setPosition(400 + (i*50) - translateX, 750 - translateY);
			items_collected.get(i).render(g);
		}
	}
	
}
