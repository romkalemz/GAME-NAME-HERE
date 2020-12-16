package time_lapse;
import java.util.ArrayList;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Door extends Entity{
	private boolean isPassable;
	private int mapSizeX = 2368;

	private int mapSizeY = 1280;
	private int Rotation;
	private boolean hasRotated;
	public boolean hasRotated() {return hasRotated;}
	public void setHasRotated(boolean pass) {hasRotated = pass;}
	public int getRotationofImage() {return Rotation;}
	public void setIsPass(boolean pass) {isPassable = pass;}
	public boolean getIsPass() {return isPassable;}
	private Image mainimg;
	public Door(final float x, final float y, int rotation) {
		super(x, y);
		Rotation = rotation;
		Image img;
		if(rotation == 90 || rotation == 270) {
			img = ResourceManager.getImage(Game.CLOSED_DOOR_VERT).copy();
			addImageWithBoundingBox(img);
		}
		if(rotation == 180 || rotation == 270) {
			img = ResourceManager.getImage(Game.CLOSED_DOOR).copy();
			addImageWithBoundingBox(img);
		}
		hasRotated = false;
		//setDebug(true);
	}
	public void setimage() {
		mainimg = ResourceManager.getImage(Game.OPEN_DOOR).copy();
		mainimg.setRotation(Rotation);
		addImage(mainimg);
	}
	public void rmImage() {
		removeImage(ResourceManager.getImage(Game.CLOSED_DOOR));
	}
	public int getMapSizeX() {
		return this.mapSizeX;
	}
	public void setR() {
		mainimg.setRotation(Rotation);
	}
	public int getMapSizeY() {
		return this.mapSizeY;
	}
}
