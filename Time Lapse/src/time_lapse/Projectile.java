package time_lapse;

import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.Vector;

public class Projectile extends Entity {

	private float speed;
	private int damage;
	private float force;
	private Vector velocity;
	public boolean isFromEnemy;
	public float getSpeed() 			{ return speed; }
	public void setSpeed(float f) 		{ speed = f; }
	public int getDamage() 				{ return damage; }
	public void setDamage(int d) 		{ damage = d; }
	public void setForce(float f) 		{ force = f; }
	public float getForce() 			{ return force; }
	public Vector getVelocity() 		{ return velocity; }
	public void setVelocity(Vector v) 	{ velocity = v; }
	
	public Projectile(final float x, final float y) {
		super(x, y);
		velocity = new Vector(0, 0);
		speed = 0.22f;
		damage = 1;
		force = 1;
		
	}
	public boolean hitOrMiss(StateBasedGame game) {
		Game tl = (Game) game;
		Tile tmp;
		if(this.getCoarseGrainedMinX() < 0) {
			return true;
		} else if(this.getCoarseGrainedMaxX() > tl.map.getMapSizeX()) {
			return true;
		}
		
		if(this.getCoarseGrainedMinY() < 0) {
			return true;
		}else if(this.getCoarseGrainedMaxY() > tl.map.getMapSizeY()) {
			return true;
		}
		
		int sideX = (int) Math.floor(getX() / tl.map.getTileSize());
		int sideY = (int) Math.floor(getY() / tl.map.getTileSize());
		
		if(sideX + 1 < tl.map.getNumOfTilesX()) {
			tmp = tl.map.getTile(sideX +1, sideY);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking N side
		if(sideY + 1 < tl.map.getNumOfTilesY()) {
			tmp = tl.map.getTile(sideX, sideY + 1);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking E side
		if(sideX - 1 > 0) {
			tmp = tl.map.getTile(sideX - 1, sideY);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking S side
		if(sideY - 1 > 0) {
			tmp = tl.map.getTile(sideX, sideY - 1);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// check enemies
		for(int i = 0; i < tl.enemy.size(); i++) {
			if(collides(tl.enemy.get(i)) != null) {
				return true;
			}
		}
		//check doors
		for(int i = 0; i < tl.doors.size(); i++) {
			if((collides(tl.doors.get(i)) != null) && (!tl.doors.get(i).getIsPass())) {
				return true;
			}
		}
		return false;
	}
	public boolean hitOrMissForEnemies(StateBasedGame game) {
		Game tl = (Game) game;
		Tile tmp;
		if(this.getCoarseGrainedMinX() < 0) {
			return true;
		} else if(this.getCoarseGrainedMaxX() > tl.map.getMapSizeX()) {
			return true;
		}
		
		if(this.getCoarseGrainedMinY() < 0) {
			return true;
		}else if(this.getCoarseGrainedMaxY() > tl.map.getMapSizeY()) {
			return true;
		}
		
		int sideX = (int) Math.floor(getX() / tl.map.getTileSize());
		int sideY = (int) Math.floor(getY() / tl.map.getTileSize());
		
		if(sideX + 1 < tl.map.getNumOfTilesX()) {
			tmp = tl.map.getTile(sideX +1, sideY);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking N side
		if(sideY + 1 < tl.map.getNumOfTilesY()) {
			tmp = tl.map.getTile(sideX, sideY + 1);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking E side
		if(sideX - 1 > 0) {
			tmp = tl.map.getTile(sideX - 1, sideY);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// checking S side
		if(sideY - 1 > 0) {
			tmp = tl.map.getTile(sideX, sideY - 1);
			if(tmp.getSolid() && collides(tmp) != null) {
				return true;
			}
		}
		// check enemies
		
		if(collides(tl.player) != null) {
				return true;
		}
		
		return false;
	}
	public void setDirection(Entity e, Vector v) {
		setPosition(e.getPosition());
		setVelocity(v);
//		image.setRotation((int) v.getRotation() + 90);
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta*speed));
	}

}
