package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import jig.ResourceManager;

public class UIHandler {
	
	Image image_items, image_activate, image_quest;
	Image image_health, image_healthbox, image_shield;
	ArrayList<Item> items_collected, quest_items;
	Item activatable;
	boolean show_cooldown;
	private int quest_count;
	
	public void setActivatable(Item i) { activatable = i; }
	public Item getActivatable() { return activatable; }
	
	public void showTimer() { show_cooldown = true; }
	public int getQuestCount() { return quest_count; }
	
	public UIHandler(Image bottom_ui) {
		items_collected =new ArrayList<Item>();
		quest_items =new ArrayList<Item>();
		//this.image_activate = i2;
		this.image_items = bottom_ui;
		reset();
	}
	
	public void reset() {
		if(!quest_items.isEmpty())
			quest_items.clear();
		quest_count = 0;
		activatable = null;
		show_cooldown = false;
	}
	
	public void addItem(Item item) {
//		// is this item a quest item?
//		// if so, add to top right of UI
//		if(item.isQuest()) {
//			// begin drawing UI for quest items
//			// check to see if you already have at least one of the items
//			for(int i = 0; i < items_collected.size(); i++) {
//				if(items_collected.get(i).isQuest()) {
//					// increment the number of collected,
//					
//					// draw the image of the machine part just collected
//					// adjacent to the most recent pick up
//				}
//			}
//		}
		if(item.isQuest()) {
			quest_items.add(item);
			return;
		}
		
		// check if this kind of item already exists in the UI
		for(int i = 0; i < items_collected.size(); i++) {
			if(item.getType().equals(items_collected.get(i).getType())) {
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
		image_quest = image_items.getScaledCopy(250, 120);
		
		// check players health and use appropiate image to display HP
		image_healthbox = ResourceManager.getImage(Game.UI_HEALTHBAR_RSC);
		image_healthbox = image_healthbox.getScaledCopy(250, 60);
		image_health = getHealthImage(tl.player.getHP());
		image_shield = getShieldImage(tl.player.getShieldHP());
		g.drawImage(image_healthbox, 900 - translateX, 720 - translateY);
		g.drawImage(image_health, 907 - translateX, 727 - translateY);
		g.drawImage(image_shield, 907 - translateX, 727 - translateY);
		// draw bottom-right player HP + shield
		g.drawString(""+tl.player.getHP(), 907 - translateX, 727 - translateY);
		if(tl.player.getShieldHP() > 0)
			g.drawString(" + "+tl.player.getShieldHP(), 935 - translateX, 727 - translateY);
		
		// draw top-right quest items
		if(!quest_items.isEmpty()) {
			g.drawImage(image_quest, 925 - translateX, 10 - translateY);
			// draw each machine part item collected
			for(int i = 0; i < quest_items.size(); i++) {
				if(tl.currLevel == 2 && i >= 5)
					quest_items.get(i).setPosition(950 + ((i-4)*50) - translateX, 80 - translateY);
				else
					quest_items.get(i).setPosition(950 + (i*50) - translateX, 40 - translateY);
				quest_items.get(i).render(g);
			}
			// draw the amount of collected and needed quest items
			int needed_quest_items = 0;
			if(tl.currLevel == 1)
				needed_quest_items = 5;
			else if(tl.currLevel == 2)
				needed_quest_items = 8;
			else if(tl.currLevel == 3)
				needed_quest_items = 5;
				
			g.drawString(quest_items.size()+"/"+needed_quest_items, 1130 - translateX, 100 - translateY);
		}
		
		// draw the top-center activatables
		if(activatable != null) {
			g.drawImage(image_activate, 540 - translateX, 10 - translateY);
			activatable.setPosition(625 - translateX, 40 - translateY);
			activatable.render(g);
			if(show_cooldown && Item.getCooldown() >= 0)
				g.drawString(""+Item.getCooldown(), 560 - translateX, 30 - translateY);
		}
		
		// draw bottom-left stats
		g.drawString("Movement Speed: " + (int)(tl.player.getMovementSpeed() *100), 25 - translateX, 710 - translateY);
		g.drawString("Attack Speed: " + (int)(tl.player.getRateOfFire() / 100), 25 - translateX, 735 - translateY);
		g.drawString("Attack Damage: " + (int)(tl.player.getAttackDamage()), 25 - translateX, 760 - translateY);
		g.drawString("Lives: " + (int)(tl.LIVES), 25 - translateX, 680 - translateY);
		
		// draw bottom-center all the items collected
		g.drawImage(image_items, 350 - translateX, 675 - translateY);
		for (int i = 0; i < items_collected.size(); i++) {
			// draw a number next to the item if multiple of them have been collected
			//System.out.print(items_collected.get(i).getIncrement());
			if(items_collected.get(i).getIncrement() > 1)
				g.drawString(""+items_collected.get(i).getIncrement(), 445 + (i*100) - translateX, 770 - translateY);
			
			items_collected.get(i).setPosition(450 + (i*100) - translateX, 735 - translateY);
			items_collected.get(i).render(g);
		}
	}
	private Image getHealthImage(int hp) {
		image_health = ResourceManager.getImage(Game.UI_HEALTHPIECE_RSC);
		image_health = image_health.getScaledCopy(236 * hp/100, 46);
		return image_health;
	}
	
	private Image getShieldImage(int shield) {
		image_shield = ResourceManager.getImage(Game.UI_HEALTHSHIELD_RSC);
		image_shield = image_shield.getScaledCopy(236 * shield/100, 46);
		return image_shield;
	}
	
}
