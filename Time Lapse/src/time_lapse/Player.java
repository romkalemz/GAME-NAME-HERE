package time_lapse;

import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Player extends Entity {

	
	private float speed;
	private Image image;
	private int pushback = 20; 		// a number the player is pushed back by
	
	
	private Vector velocity;
	public void setVelocity(final Vector v) { velocity = v; }
	public Vector getVelocity() { return velocity; }
	
	private int rotate_delay;
	public void setRotateDelay(int rd) { rotate_delay = rd; }
	public int getRotateDelay() { return rotate_delay; }
	
	public Player(final float x, final float y) {
		super(x, y);
		
		image = ResourceManager.getImage(MainGame.PLAYER_DEFAULT_RSC).getScaledCopy(40, 40);
		addImageWithBoundingBox(image);
		
		reset();
	}
	

	
	public void reset() {
		velocity = new Vector(0, 0);
		speed = 0.2f;
	}
	
//	public void setRotation(int dir) {
//		image.setRotation(dir);
//		addImageWithBoundingBox(image);
//	}
	
	public void checkBounds(Map m) {
		if(this.getCoarseGrainedMinX() < 0) {
			this.setPosition(pushback, this.getY());
		}else if(this.getCoarseGrainedMaxX() > m.getMapSizeX()) {
			this.setPosition(m.getMapSizeX() - pushback, this.getY());
		}
		
		if(this.getCoarseGrainedMinY() < 0) {
			this.setPosition(this.getX(), pushback);
		}else if(this.getCoarseGrainedMaxY() > m.getMapSizeY()) {
			this.setPosition(this.getX(), m.getMapSizeY() - pushback);
		}
		
	}
	
	public void checkWall(Map m) {
		Tile[][] tiles = m.getTileMap();
		
		for(int x = 0; x < m.getNumOfTilesX(); x++) {
			for(int y = 0; y< m.getNumOfTilesY(); y++) {
				if(tiles[x][y].getType() != 0) {
					if(this.collides(tiles[x][y]) != null) {
						System.out.println("Collided");
						
					}
				}
			}
		}
	}
	
	
//	
//	public void checkCollision(Map map) {
//		
//		// CHECKING OUTER SIDES OF PLAYERS' TILES METHOD
//		int sideX = (int) Math.floor(getX() / map.tileSize);
//		int sideY = (int) Math.floor(getY() / map.tileSize);
//		Tile t;
//		// checking W side
//		if(sideX + 1 < map.number_of_tilesX) {
//			t = map.getTile(sideX +1, sideY);
//			if(t.isSolid() && collides(t) != null) {
//				setX(t.getCoarseGrainedMinX() - pushback);
//			}
//		}
//		// checking N side
//		if(sideY + 1 < map.number_of_tilesY) {
//			t = map.getTile(sideX, sideY + 1);
//			if(t.isSolid() && collides(t) != null) {
//				setY(t.getCoarseGrainedMinY() - pushback);
//			}
//		}
//		// checking E side
//		if(sideX - 1 > 0) {
//			t = map.getTile(sideX - 1, sideY);
//			if(t.isSolid() && collides(t) != null) {
//				setX(t.getCoarseGrainedMaxX() + pushback);
//			}
//		}
//		// checking S side
//		if(sideY - 1 > 0) {
//			t = map.getTile(sideX, sideY - 1);
//			if(t.isSolid() && collides(t) != null) {
//				setY(t.getCoarseGrainedMaxY() + pushback);
//			}
//		}	
//	}
	

	public void update(StateBasedGame game, final int delta) {
		MainGame g = (MainGame) game;
		checkBounds(g.map);
		//checkCollision();
		translate(velocity.scale(delta * speed));
	}

}
