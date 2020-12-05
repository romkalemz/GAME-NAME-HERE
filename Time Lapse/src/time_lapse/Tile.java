package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {
	
	private int type;
	private boolean isSolid;
	
	public Tile(final float x, final float y, int type, int isSolid) {
		super(x,y);
		if(type == 0) {
			Image img = ResourceManager.getImage(Game.TILE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = false;
		}
		if(type == 3) {
			Image img = ResourceManager.getImage(Game.TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = true;
		}
		if(type == 4) {
			Image img = ResourceManager.getImage(Game.LEFT_TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = true;
		}
		if(type == 5) {
			Image img = ResourceManager.getImage(Game.RIGHT_TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = true;
		}
		this.type = type;
	}
	
	
	public boolean getSolid() {
		return isSolid;
	}
	
	public int getType() {
		return type;
	}
}
