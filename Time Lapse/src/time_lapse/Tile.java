package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity implements Comparable<Tile> {
	
	private int type;
	private boolean isSolid;
	
	// Dikjstra fields
	private float cost;
	private boolean visited;
	private ArrayList<Tile> neighbors;
	private Tile prev;
	private boolean corner;
	private Image tileImg;
	public Image fogImg;
	public void setCost(float c) { cost = c; }
	public float getCost() { return cost; }
	public void setVisited(boolean sv) { visited = sv; }
	public boolean getVisited() { return visited; }
	public void setNeighbors(ArrayList<Tile> n) { neighbors = n; }
	public ArrayList<Tile> getNeighbors() { return neighbors; }
	public void setPrev(Tile p) { prev = p; }
	public Tile getPrev() { return prev; }
	public void setCorner(boolean c) { corner = c; }
	public boolean getCorner() { return corner; }
	
	
	public Tile(final float x, final float y, int type, int isSolid) {
		super(x,y);
		if(type == 0) {
			tileImg = ResourceManager.getImage(Game.TILE_DIRT_RSC).getScaledCopy(40,40);
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = false;
		}
		if(type == 3) {
			tileImg = ResourceManager.getImage(Game.TREE_DIRT_RSC).getScaledCopy(40,40);
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = true;
		}
		if(type == 4) {
			tileImg = ResourceManager.getImage(Game.LEFT_TREE_DIRT_RSC).getScaledCopy(40,40);;
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = true;
		}
		if(type == 5) {
			tileImg = ResourceManager.getImage(Game.RIGHT_TREE_DIRT_RSC).getScaledCopy(40,40);;
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = true;
		}
		if(type == 6) {
			tileImg = ResourceManager.getImage(Game.BOULDER_RSC).getScaledCopy(40,40);;
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = true;
		}
		if(type == 7) { //gate
			tileImg = ResourceManager.getImage(Game.TILE_DIRT_RSC).getScaledCopy(40,40);;
			tileImg.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(tileImg);
			this.isSolid = true;
		}
		this.type = type;
		reset();
	}
	public void addTileFog() {
		fogImg = ResourceManager.getImage(Game.TILE_FOG_RSC).getScaledCopy(40, 40);
		addImage(fogImg);
	}
	public void reset() {
		visited = false;
		cost = (float) Double.POSITIVE_INFINITY;
		prev = null;
	}
	
	
	public boolean getSolid() {
		return isSolid;
	}
	public void setSolid(boolean bool) {
		isSolid = bool;
	}
	
	public int getType() {
		return type;
	}
	@Override
	public int compareTo(Tile o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
