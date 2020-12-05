package time_lapse;

import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Player extends Entity {

	
	// stats 
	private float movement_speed;
	private int rate_of_fire;
	private float bullet_speed;
	private int attack_damage;
	private float hp, max_hp;
	
	// setters and getters for stats
	public void setMovementSpeed(float n) { movement_speed = n; }
	public float getMovementSpeed() { return movement_speed; }
	
	public void setRateOfFire(int rof) { rate_of_fire = rof; }
	public int getRateOfFire() { return rate_of_fire; }
	
	public void setBulletSpeed(float s) { bullet_speed = s; }
	public float getBulletSpeed() { return bullet_speed; }
	
	public void setAttackDamage(int d) { attack_damage = d; }
	public int getAttackDamage() { return attack_damage; }
	
	public void setHP(float a) { hp = a; }
	public float getHP() { return hp; }
	
	// other attributes to player
	private Image image;
	private int pushback = 20; 		// a number the player is pushed back by
	private Vector velocity;	
	private int rotate_delay;
	
	public void setVelocity(final Vector v) { velocity = v; }
	
	public Vector getVelocity() { return velocity; }
	
	public void setRotateDelay(int rd) { rotate_delay = rd; }
	
	public int getRotateDelay() { return rotate_delay; }
	
	
	public Player(final float x, final float y) {
		super(x, y);
		
		image = ResourceManager.getImage(Game.PLAYER_DEFAULT_RSC).getScaledCopy(40, 40);
		addImageWithBoundingBox(image);
		
		reset();
	}
	

	
	public void reset() {
		velocity = new Vector(0, 0);
		
		// reset, or set initial stats
		movement_speed = 0.2f;
		rate_of_fire = 450;
		attack_damage = 1;
		max_hp = hp = 3;
		bullet_speed = 0.3f;
		
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
	
	public void checkCollision(Map m) {
//		Tile[][] tiles = m.getTileMap();
//		
//		for(int x = 0; x < m.getNumOfTilesX(); x++) {
//			for(int y = 0; y< m.getNumOfTilesY(); y++) {
//				if(tiles[x][y].getType() != 0) {
//					if(this.collides(tiles[x][y]) != null) {
//						//System.out.println("Collided");
//						
//					}
//				}
//			}
//		}
		
		// get all 4 adjacent tiles next to player
		Tile t;
		int sideX = (int) Math.floor(getX() / m.getTileSize());
		int sideY = (int) Math.floor(getY() / m.getTileSize());
		// checking W side
		if(sideX + 1 < m.getNumOfTilesX()) {
			t = m.getTile(sideX +1, sideY);
			if(t.getSolid() && collides(t) != null) {
				setX(t.getCoarseGrainedMinX() - pushback);
			}
		}
		// checking N side
		if(sideY + 1 < m.getNumOfTilesY()) {
			t = m.getTile(sideX, sideY + 1);
			if(t.getSolid() && collides(t) != null) {
				setY(t.getCoarseGrainedMinY() - pushback);
			}
		}
		// checking E side
		if(sideX - 1 > 0) {
			t = m.getTile(sideX - 1, sideY);
			if(t.getSolid() && collides(t) != null) {
				setX(t.getCoarseGrainedMaxX() + pushback);
			}
		}
		// checking S side
		if(sideY - 1 > 0) {
			t = m.getTile(sideX, sideY - 1);
			if(t.getSolid() && collides(t) != null) {
				setY(t.getCoarseGrainedMaxY() + pushback);
			}
		}
	}
	

	public void update(StateBasedGame game, final int delta) {
		Game g = (Game) game;
		checkBounds(g.map);
		//checkCollision();
		translate(velocity.scale(delta * movement_speed));
	}

}
