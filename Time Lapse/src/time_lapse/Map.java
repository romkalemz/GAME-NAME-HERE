package time_lapse;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;

/*
 * Map renders tiles and maintains information
 */
public class Map {
	private int tileSize;
	private int numOfTilesX;
	private int numOfTilesY;
	// mapsize is calculated by tileSize * numOfTiles
	private int mapSizeX = 2960;
	private int mapSizeY = 1600;
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

	public void clearAndReset(Game tl) {
		// clear all entities
		tl.items.clear();
		tl.enemy.clear();
		tl.projectiles.clear();
		tl.UIHandler.reset();
		tl.player.reset();
		tl.doors.clear();
		tl.doorSwitch.clear();
		tl.LIVES = 3;
		// Reset scrolling world
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

	public float getTranslateX() {
		return this.translateX;
	}

	public float getTranslateY() {
		return this.translateY;
	}

	// setTile is called in LevelManager to add tile into array
	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	public Tile[][] getTileMap() {
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
		} else if (playerPosX < prevMaxX - 600 && playerPosX > 300 && playerSpeedX < 0) {
			// if player is going left, save prevmax so when player
			// travels right, map scrolls
			prevMaxX = playerPosX + 600;
			translateX = 300 - playerPosX;
		}
		// Same concept for y-axis
		if (playerPosY > prevMaxY && prevMaxY < mapSizeY - 267) {
			if (playerPosY > minScrollY && playerSpeedY > 0) {
				prevMaxY = playerPosY;
				translateY = minScrollY - playerPosY;
			}
		} else if (playerPosY < prevMaxY - 267 && playerPosY > 267 && playerSpeedY < 0) {
			prevMaxY = playerPosY + 267;
			translateY = 267 - playerPosY;
		}
		// updates the dikstra elements for the map
		updateEnemies(tl);
	}

	// renderMap is called in PlayingState render
	public void renderMap(Graphics g) {
		// g.translate translates all rendered graphics
		// based on calculations from updateMap()
		g.translate(translateX, translateY);
		for (int x = 0; x < this.numOfTilesX; x++) {
			for (int y = 0; y < this.numOfTilesY; y++) {
				if (tiles[x][y] != null) {
					tiles[x][y].render(g);
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= numOfTilesX || y >= numOfTilesY)
			return null;
		else
			return tiles[x][y];
	}

	// find shortest path between two entities
	// returns an array of points needed to follow
	public void dijkstraPath(Entity player) {
		// populate graph with costs of tiles from player to enemies
		// place initial tile and neighbors into queue and adjust costs
		Tile startTile = getTile((int) player.getX() / tileSize, (int) player.getY() / tileSize); // grab first tile
		startTile.setCost(0); // set cost of travel to 0
		// unexplored tiles are placed in the queue
		PriorityQueue<Tile> Q = new PriorityQueue<Tile>();
		Q.add(startTile); // add to the queue/stack
		// if tile is unexplored, visit it and adjust costs
		while (!Q.isEmpty()) {
			Tile curTile = Q.poll(); // pop the head of the stack
			curTile.setVisited(true);
			curTile.setNeighbors(findNeighbors(curTile)); // finds all valid tiles around it
			// adjust and compare values of neighboring tiles, add to Q
			for (int i = 0; i < curTile.getNeighbors().size(); i++) {
				Tile neighbor = curTile.getNeighbors().get(i);
				// check if you can walk on the neighbor tile AND adjust costs
				// first check if this is a neighboring corner tile
				double travelDistance = travelCost(curTile, neighbor);
				if (!neighbor.getSolid() && neighbor.getCost() > curTile.getCost() + travelDistance) {
					neighbor.setCost((float) (curTile.getCost() + travelDistance)); // reduce cost of neighbor
					// since its closer than before
					neighbor.setPrev(curTile); // keep track of the prev
												// so you can backtrack
				}

				if (!neighbor.getVisited())
					Q.add(neighbor);
			}
		}
	}

	// find the surrounding tiles for the given tile
	public ArrayList<Tile> findNeighbors(Tile t) {
		int x = (int) t.getX() / tileSize;
		int y = (int) t.getY() / tileSize;

		ArrayList<Tile> n = new ArrayList<Tile>();
		// SW
		if (x > 0 && y < numOfTilesY - 1) {
			tiles[x - 1][y + 1].setCorner(true);
			n.add(tiles[x - 1][y + 1]);
		}
		// W
		if (x > 0) {
			n.add(tiles[x - 1][y]);
		}
		// NW
		if (x > 0 && y < 0) {
			tiles[x - 1][y - 1].setCorner(true);
			n.add(tiles[x - 1][y - 1]);
		}
		// N
		if (y > 0) {
			n.add(tiles[x][y - 1]);
		}
		// NE
		if (x < numOfTilesX - 1 && y > 0) {
			tiles[x + 1][y - 1].setCorner(true);
			n.add(tiles[x + 1][y - 1]);
		}
		// E
		if (x < numOfTilesX - 1) {
			n.add(tiles[x + 1][y]);
		}
		// SE
		if (x > 0 && y < numOfTilesY - 1) {
			tiles[x - 1][y + 1].setCorner(true);
			n.add(tiles[x - 1][y + 1]);
		}
		// E
		if (y < numOfTilesY - 1) {
			n.add(tiles[x][y + 1]);
		}

		return n;
	}

	private double travelCost(Tile t1, Tile t2) {

		double lx = Math.abs(t1.getX() - t2.getX());
		double ly = Math.abs(t1.getY() - t2.getY());
		double distance;
		if (lx == 0) // vertical distance travel
			distance = ly;
		else if (ly == 0) // horizontal distance travel
			distance = lx;
		else // diagonal distance travel
			distance = Math.hypot(lx, ly);
		return distance / 40; // converting to tile distance
	}

	public void updateEnemies(Game game) {
		Game g = (Game) game;
		// reset dijkstra elements for each tile
		for (int x = 0; x < numOfTilesX; x++)
			for (int y = 0; y < numOfTilesY; y++)
				tiles[x][y].reset();

		// find shortest path from enemies to player
		for (int i = 0; i < g.enemy.size(); i++) {
			dijkstraPath(g.player);
			g.enemy.get(i)
					.setPath(getTile((int) g.enemy.get(i).getX() / tileSize, (int) g.enemy.get(i).getY() / tileSize));
		}
	}
	
	// adds an item back into the world in the middle of the game (based on desired entity location
	public void addItem(Item item, Entity entity) {
		
	}
}
