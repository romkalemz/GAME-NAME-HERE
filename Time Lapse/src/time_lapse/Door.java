package time_lapse;
import java.util.ArrayList;

import jig.Entity;

public class Door extends Entity{
	private boolean isPassable;
	private int mapSizeX = 2368;

	private int mapSizeY = 1280;
	private int Rotation;
	
	public int getRotationofImage() {return Rotation;}
	public void setIsPass(boolean pass) {isPassable = pass;}
	public boolean getIsPass() {return isPassable;}
	
	public Door(final float x, final float y, int rotation) {
		super(x, y);
		Rotation = rotation;
	}
	public int getMapSizeX() {
		return this.mapSizeX;
	}
	
	public int getMapSizeY() {
		return this.mapSizeY;
	}
}
