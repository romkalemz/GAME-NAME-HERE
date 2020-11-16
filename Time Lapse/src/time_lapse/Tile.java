package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {
	
	private int type;
	private int isSolid;
	
	public Tile(final float x, final float y, int type, int isSolid) {
		super(x,y);
		if(type == 0) {
			Image img = ResourceManager.getImage(MainGame.TILE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = 0;
		}
		if(type == 3) {
			Image img = ResourceManager.getImage(MainGame.TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = 1;
		}
		if(type == 4) {
			Image img = ResourceManager.getImage(MainGame.LEFT_TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = 1;
		}
		if(type == 5) {
			Image img = ResourceManager.getImage(MainGame.RIGHT_TREE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = 1;
		}
		this.type = type;
	}
	
	
	public int getSolid() {
		return isSolid;
	}
	
	public int getType() {
		return type;
	}
}
