package time_lapse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/*
 * Map renders tiles and maintains information
 */
public class Map {
	private int tileSize;
	private int mapSizeX;
	private int mapSizeY;
	private int mapScreenSizeX = 1200;
	private int mapScreenSizeY = 800;
	private Tile[][] tiles;
	
	public Map(int mapSizeX, int mapSizeY, int tileSize) {
		this.tileSize = tileSize;
		this.mapSizeX = mapSizeX;
		this.mapSizeY = mapSizeY;
		tiles = new Tile[mapSizeX][mapSizeY];
		this.tileSize = tileSize;
	}
	public int getTileSize() {
		return this.tileSize;
	}
	public int getMapSizeX() {
		return this.mapSizeX;
	}
	public int getMapSizeY() {
		return this.mapSizeY;
	}
	public int getScreenSizeX() {
		return this.mapScreenSizeX;
	}
	public int getScreenSizeY() {
		return this.mapScreenSizeY;
	}
	
	// setTile is called in LevelManager to add tile into array
	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
	// renderMap is called in PlayingState render
	public void renderMap(Graphics g) {
		for(int x = 0; x < this.mapSizeX; x++) {
			for(int y = 0; y< this.mapSizeY; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
	}
}
