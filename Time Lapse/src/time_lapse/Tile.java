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
		if(type == 6) {
			Image img = ResourceManager.getImage(Game.BOULDER_RSC);
			img.setFilter(Image.FILTER_LINEAR);
			addImageWithBoundingBox(img);
			this.isSolid = true;
		}
		this.type = type;
		reset();
	}
	
	public void reset() {
		visited = false;
		cost = (float) Double.POSITIVE_INFINITY;
		prev = null;
	}
	
	
	public boolean getSolid() {
		return isSolid;
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
