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
		// offset difference of map scrolled
		float translateX = tl.map.getTranslateX();
		float translateY = tl.map.getTranslateY();
		
		image.setFilter(Image.FILTER_LINEAR);
		
		// draw the elements of the UI (box, stats)
		g.drawImage(image, 300 - translateX, 675 - translateY);
		g.drawString("Movement Speed: " + (int)(tl.player.getMovementSpeed() *100), 25 - translateX, 710 - translateY);
		g.drawString("Attack Speed: " + (int)(tl.player.getRateOfFire() / 100), 25 - translateX, 735 - translateY);
		g.drawString("Attack Damage: " + (int)(tl.player.getAttackDamage()), 25 - translateX, 760 - translateY);
		//g.drawString("Score: "+score, 550, 10);
		
		// draw all the items collected
		for (int i = 0; i < items_collected.size(); i++) {
			items_collected.get(i).setPosition(350 + (i*100) - translateX, 750 - translateY);
			items_collected.get(i).render(g);
		}
	}
	
}
