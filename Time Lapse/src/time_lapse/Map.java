package time_lapse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/*
 * Map renders tiles and maintains information
 */
public class Map {
	private int tileSize;
	private int numOfTilesX;
	private int numOfTilesY;
	private int mapSizeX = 2368;
	private int mapSizeY = 1280;
	private int screenSizeX = 1200;
	private int screenSizeY = 800;
	private Tile[][] tiles;
	
	public Map(int mapSizeX, int mapSizeY, int tileSize) {
		this.tileSize = tileSize;
		this.numOfTilesX = mapSizeX;
		this.numOfTilesY = mapSizeY;
		tiles = new Tile[mapSizeX][mapSizeY];
		this.tileSize = tileSize;
	}
	public int getTileSize() {
		return this.tileSize;
	}
	public int getNumOfTilesX() {
		return this.numOfTilesX;
	}
	public int getNumOfTilesY() {
		return this.numOfTilesY;
	}
	public int getMapSizeX() {
		return this.mapSizeX;
	}
	public int getMapSizeY() {
		return this.mapSizeY;
	}
	
	// setTile is called in LevelManager to add tile into array
	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
	
	// updateMap calculates render for map scrolling
	public void updateMap(StateBasedGame game) {
		
	}
	
	// renderMap is called in PlayingState render
	public void renderMap(Graphics g) {
		for(int x = 0; x < this.numOfTilesX; x++) {
			for(int y = 0; y< this.numOfTilesY; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
	}
}
