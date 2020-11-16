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
	
	// Used for calculating scrolling map
	private float translateX;
	private float maxX;
	private float prevMaxX;
	
	public Map(int numOfTilesX, int numOfTilesY, int tileSize) {
		this.tileSize = tileSize;
		this.numOfTilesX = numOfTilesX;
		this.numOfTilesY = numOfTilesY;
		tiles = new Tile[numOfTilesX][numOfTilesY];
		this.tileSize = tileSize;
		translateX = 0;
		maxX = 900;
		prevMaxX = 900;
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
		MainGame tl = (MainGame) game;
		float playerPosX = tl.player.getX();
		float playerSpeedX = tl.player.getVelocity().getX();
		// If player distance (from origin) is larger than prev dist
		if (playerPosX > prevMaxX && prevMaxX < 1968) {
			// Start scrolling when player pos is larger than 400
			if (playerPosX > maxX && playerSpeedX > 0) {
				prevMaxX = playerPosX;
				translateX = maxX - playerPosX;
			}
		} else if (playerPosX < prevMaxX-600 && playerPosX > 300 && playerSpeedX < 0) {
			// if player is going left, save prevmax so when player
			// travels right, map scrolls
			prevMaxX = playerPosX + 600;
			translateX = 300 - playerPosX;
		}
	}
	
	// renderMap is called in PlayingState render
	public void renderMap(Graphics g) {
		// g.translate translates all rendered graphics
		// based on calculations from updateMap()
		g.translate(translateX, 0);
		for(int x = 0; x < this.numOfTilesX; x++) {
			for(int y = 0; y< this.numOfTilesY; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
	}
}
