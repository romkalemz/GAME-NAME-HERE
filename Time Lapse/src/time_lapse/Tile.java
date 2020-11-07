package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {
	
	private int type;
	
	public Tile(final float x, final float y, int type) {
		super(x,y);
		if(type == 0) {
			Image img = ResourceManager.getImage(MainGame.TILE_DIRT_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
		}
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
