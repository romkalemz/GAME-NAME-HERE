package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Entity {
	
	private int pushback; 		// a number the player is pushed back by
	private float speed;
	private ArrayList<Vector> path;	// the path the enemy follows in the game
	public ArrayList<Vector> getPath() { return path; }
	private int enemyType;
	public int shootCooldown;
	private int followPoint;
	private Image newEnemy, healthBar;
	private Vector velocity;
	private int hp, total_hp, KO;
	private float translateX, translateY;
	
	public int getKO() { return KO; }
	public int getHP() { return hp; }
	
	public void setVelocity(final Vector v) { velocity = v; }
	public Vector getVelocity() { return velocity; }
	public int getEnemyType() {return enemyType; }
	
	private int rotate_delay;
	
	public void setRotateDelay(int rd) { rotate_delay = rd; }
	public int getRotateDelay() { return rotate_delay; }
	
	public Enemy(final float x, final float y, int type){
		super(x,y);
		
		newEnemy = ResourceManager.getImage(Game.PLAYER_DEFAULT_RSC).getScaledCopy(32, 32);
		addImageWithBoundingBox(newEnemy);
		// set the healthBar image
		healthBar = ResourceManager.getImage(Game.UI_HEALTHPIECE_RSC).getScaledCopy(50, 5);
		enemyType = type;
		if(type == 1) {
			newEnemy.setColor(2, 255, 0, 0);
		}
		else if(type == 2){
			newEnemy.setColor(2, 0, 255, 0);
		}
		else if(type == 3) {
			newEnemy.setColor(2, 0, 0, 255);
		}
		path = new ArrayList<Vector>();
		reset();
	}
	
	public void reset() {
		velocity = new Vector(0, 0);
		speed = 0.2f;
		pushback = 20;
		total_hp = hp = 15;
		KO = 0;
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
	
	public void takeDamage(Projectile projectile, int damage) {
		// have a knockback for enemies (realistic)
		KO = 75;
		velocity = velocity.add(projectile.getVelocity().scale(1.5f));
		hp -= damage;
	}
	
	public void checkCollision(Map m) {
		
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
	
	public void chasePath() {
		Vector vel = new Vector(0, 0);
		if(path.isEmpty())
			return;
		if(path.size() == 1)
			followPoint = 0;
		Vector des = path.get(followPoint);
		Vector dif = new Vector(des.getX()-getX(), des.getY()-getY());
		
		if (dif.length() <= 3) {
			followPoint++;
			if(followPoint >= path.size())
				followPoint = 0;
			setPosition(des.getX(), des.getY());
		}
		else
			vel = new Vector(dif.getX() / dif.length(), dif.getY() / dif.length());
		
		velocity = vel;
	}
	
	public void setPath(Tile current) {
		path.clear();
		while(current != null) {
			Vector loc = current.getPosition();
			path.add(loc);
			current = current.getPrev();
		}
	}
	
	private void updateVariables(final int delta) {
		KO -= delta;
		// update the healthBar image
		healthBar = healthBar.getScaledCopy(30 * hp/total_hp, 5);
	}
	
	public void render(Graphics g) {
		g.drawImage(healthBar, this.getX() - 15, this.getY() - 25);
		super.render(g);
	}
	
	public void update(StateBasedGame game, final int delta) {
		Game g = (Game) game;
		
		checkBounds(g.map);
		checkCollision(g.map);
		
		updateVariables(delta);
		
		translate(velocity.scale(delta * speed));
	}
}
