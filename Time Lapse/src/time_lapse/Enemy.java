package time_lapse;

import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Entity {
	
	private int pushback; 		// a number the player is pushed back by
	private float speed;
	
	private Image newEnemy;
	private Vector velocity;
	public void setVelocity(final Vector v) { velocity = v; }
	public Vector getVelocity() { return velocity; }
	
	private int rotate_delay;
	public void setRotateDelay(int rd) { rotate_delay = rd; }
	public int getRotateDelay() { return rotate_delay; }
	
	public Enemy(final float x, final float y, int type){
		super(x,y);
		newEnemy = ResourceManager.getImage(Game.PLAYER_DEFAULT_RSC).getScaledCopy(40, 40);
		addImageWithBoundingBox(newEnemy);
		if(type == 1)
			newEnemy.setColor(2, 255, 0, 0);
		else if(type == 2)
			newEnemy.setColor(2, 0, 255, 0);
		else if(type == 3)
			newEnemy.setColor(2, 0, 0, 255);
		
		reset();
	}
	public void reset() {
		velocity = new Vector(0, 0);
		speed = 0.2f;
		pushback = 20;
	}
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
	
	public void update(StateBasedGame game, final int delta) {
		Game g = (Game) game;
		checkBounds(g.map);
		//checkCollision();
		translate(velocity.scale(delta * speed));
	}
}
