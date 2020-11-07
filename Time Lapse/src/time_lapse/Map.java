package time_lapse;

/*
 * Map renders tiles and maintains information
 */
public class Map {
	private int tileSize;
	private int mapSizeX;
	private int mapSizeY;
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
}
