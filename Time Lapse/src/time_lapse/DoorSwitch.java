package time_lapse;
import java.util.ArrayList;

import jig.Entity;

public class DoorSwitch extends Entity{
	private boolean switched;
	private int mapSizeX = 2368;
	private int mapSizeY = 1280;
	private ArrayList<Door> ds;
	private int Rotation;
	public int getRotationofImage() {return Rotation;}
	public void setIsSwitched(boolean pass) {switched = pass;}
	public boolean isSwitched() {return switched;}
	
	public DoorSwitch(final float x, final float y, int rotation, ArrayList<Door> d) {
		super(x, y);
		Rotation = rotation;
		ds = d;
		switched = false;
	}
	public int getMapSizeX() {
		return this.mapSizeX;
	}
	
	public ArrayList<Door> getDoor(){
		return ds;
	}
	
	public int getMapSizeY() {
		return this.mapSizeY;
	}
}
