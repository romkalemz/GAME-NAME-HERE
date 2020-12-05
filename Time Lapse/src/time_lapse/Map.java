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
	// mapsize is calculated by tileSize * numOfTiles
	private int mapSizeX = 2368;
	private int mapSizeY = 1280;
	private int screenSizeX = 1200;
	private int screenSizeY = 800;
	private Tile[][] tiles;
	
	// Used for calculating scrolling map
	private float translateX, translateY;
	private float minScrollX, minScrollY;
	private float prevMaxX, prevMaxY;
	
	public Map(int numOfTilesX, int numOfTilesY, int tileSize) {
		this.tileSize = tileSize;
		this.numOfTilesX = numOfTilesX;
		this.numOfTilesY = numOfTilesY;
		tiles = new Tile[numOfTilesX][numOfTilesY];
		this.tileSize = tileSize;
		translateX = 0;
		translateY = 0;
		minScrollX = 900;
		minScrollY = 533;
		prevMaxX = 900;
		prevMaxY = 533;
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
	
	public Tile[][] getTileMap(){
		return this.tiles;
	}
	
	// updateMap calculates render for map scrolling
	public void updateMap(StateBasedGame game) {
		Game tl = (Game) game;
		float playerPosX = tl.player.getX();
		float playerPosY = tl.player.getY();
		float playerSpeedX = tl.player.getVelocity().getX();
		float playerSpeedY = tl.player.getVelocity().getY();
		// If player distance (from origin) is larger than prev dist
		if (playerPosX > prevMaxX && prevMaxX < mapSizeX - 300) {
			// Start scrolling when player pos is larger than 400
			if (playerPosX > minScrollX && playerSpeedX > 0) {
				prevMaxX = playerPosX;
				translateX = minScrollX - playerPosX;
			}
		} else if (playerPosX < prevMaxX-600 && playerPosX > 300 && playerSpeedX < 0) {
			// if player is going left, save prevmax so when player
			// travels right, map scrolls
			prevMaxX = playerPosX + 600;
			translateX = 300 - playerPosX;
		}
		// Same concept for y-axis
		if(playerPosY > prevMaxY && prevMaxY < mapSizeY - 267) {
			if(playerPosY > minScrollY && playerSpeedY > 0) {
				prevMaxY = playerPosY;
				translateY = minScrollY - playerPosY;
			}
		} else if(playerPosY < prevMaxY - 267 && playerPosY > 267 && playerSpeedY < 0) {
			prevMaxY = playerPosY + 267;
			translateY = 267 - playerPosY;
		}
	}
	
	// renderMap is called in PlayingState render
	public void renderMap(Graphics g) {
		// g.translate translates all rendered graphics
		// based on calculations from updateMap()
		g.translate(translateX, translateY);
		for(int x = 0; x < this.numOfTilesX; x++) {
			for(int y = 0; y< this.numOfTilesY; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		if( x<0 || y<0 || x>=numOfTilesX || y>=numOfTilesY)
			return null;
		else
			return tiles[x][y];
	}
}
