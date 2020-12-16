package time_lapse;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Player extends Entity {

	
	// stats 
	private float movement_speed, movement_speed_buffer;
	private int rate_of_fire, rate_of_fire_buffer;
	private float bullet_speed, bullet_speed_buffer;
	private int attack_damage, attack_damage_buffer;
	private int hp, max_hp, curr_shield_hp, total_shield_hp;
	private boolean activatable;
	private int imgRotation;
	private int prev_dir;  // This is for idle animations
	private int direction; // This if for use with animations.'
	private Animation animation; // This is the actual animation for character
	// setters and getters for stats
	public void canActivate(boolean b) { activatable = b; }
	public boolean canActivate() { return activatable; }
	
	// This if for getting/setting the direction of character for animations
	public int getDirection() { return direction; }
	public void setDirection(int dir) { direction = dir;}
	// This if for getting/setting the direction of character for animations
	public int getPrevDirection() { return prev_dir; }
	public void setPrevDirection(int dir) { prev_dir = dir;}
	
	public void setAnimation(Animation ani) { animation = ani;}
	
	
	public void setMovementSpeed(float n) { movement_speed = n; }
	public float getMovementSpeed() { return movement_speed; }
	
	public void setRateOfFire(int rof) { rate_of_fire = rof; }
	public int getRateOfFire() { return rate_of_fire; }
	
	public void setBulletSpeed(float s) { bullet_speed = s; }
	public float getBulletSpeed() { return bullet_speed; }
	
	public void setAttackDamage(int d) { attack_damage = d; }
	public int getAttackDamage() { return attack_damage; }
	
	public void setHP(int a) { hp = a; }
	public int getHP() { return hp; }
	
	public int getShieldHP() { return curr_shield_hp; }
	
	// other attributes to player
	private Image image;
	private int pushback = 20; 		// a number the player is pushed back by
	private Vector velocity;	
	private int rotate_delay;
	private int shoot_delay;
	private int active_delay;
	private int action_duration;
	private int take_damage_delay;
	
	public void setVelocity(final Vector v) { velocity = v; }
	public Vector getVelocity() { return velocity; }
	
	public void setRotateDelay(int rd) { rotate_delay = rd; }
	public int getRotateDelay() { return rotate_delay; }
	
	public void setShootDelay() { shoot_delay = rate_of_fire; }
	public int getShootDelay() { return shoot_delay; }
	
	public void setActiveDelay(int delay) { active_delay = delay; }
	public int getActiveDelay()	{ return active_delay; }
	
	public Player(final float x, final float y) {
		super(x, y);

		image = ResourceManager.getImage(Game.PLAYER_DEFAULT_RSC).getScaledCopy(40, 40);
		addImageWithBoundingBox(image);

		imgRotation = 0;
		reset(false);
		direction = 1;
		prev_dir = 1;
	}
	
	
	public void reset( Boolean isGod) {
		velocity = new Vector(0, 0);
		setPosition(400, 300);
		// reset, or set initial stats
		if(!isGod) {
			movement_speed_buffer = movement_speed = 0.2f;
			rate_of_fire_buffer = rate_of_fire = 450;
			attack_damage_buffer = attack_damage = 5;
			max_hp = hp = 100;
			bullet_speed_buffer = bullet_speed = 0.3f;
			activatable = false;
		}
	}
	
	public void takeDamage(int dmg) {
		if(curr_shield_hp > 0)
			curr_shield_hp -= dmg;
		else
			hp -= dmg;
		take_damage_delay = 800;
	}
	
	public void setImageRotation(int dir) {
		image.setRotation(dir);
		imgRotation = dir;
		addImageWithBoundingBox(image);
	}
	public int getImageRotation() {
		return imgRotation;
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
	public void checkTimeMachineWin(Game g) {
		int totalCount = 0;
		if(g.currLevel == 1) {
			totalCount = g.UIHandler.quest_items.size();
			if(totalCount == 5 && collides(g.machine)!=null) {
				g.currLevel = 2;
				g.enterState(Game.TRANSITIONSTATE);
			}
		}
		else if(g.currLevel == 2) {
			totalCount = g.UIHandler.quest_items.size();
			if(totalCount == 8 && collides(g.machine)!=null) {
				g.currLevel = 3;
				g.enterState(Game.TRANSITIONSTATE);
			}
		}
		else if(g.currLevel == 3) {
			totalCount = g.UIHandler.quest_items.size();
			if(totalCount == 5 && collides(g.machine)!=null) {
				g.currLevel = 4;
				g.enterState(Game.TRANSITIONSTATE);
			}
		}
	}
	public void checkDoorCollision(Game g, Map m) {
		Tile t;
		int sideX = (int) Math.floor(getX() / m.getTileSize());
		int sideY = (int) Math.floor(getY() / m.getTileSize());
		
		for(int i = 0; i<g.doors.size(); i++) {
			//W
			if(sideX + 1 < m.getNumOfTilesX()) {
				t = m.getTile(sideX + 1, sideY);
				if((!g.doors.get(i).getIsPass()) && collides(g.doors.get(i)) != null) {
					setX(t.getCoarseGrainedMinX()-pushback);
				} else if(collides(g.doors.get(i)) != null) {
					if(g.currLevel == 1 || g.currLevel == 3) {//temp
						g.rooms.get(g.doors.get(i).getRoomNum()).removeRoomFog();
					}
				}
			}
			//N
			if(sideY + 1 < m.getNumOfTilesY()) {
				t = m.getTile(sideX, sideY + 1);
				if((!g.doors.get(i).getIsPass()) && collides(g.doors.get(i)) != null) {
					setY(t.getCoarseGrainedMinY()-pushback);
				}else if(collides(g.doors.get(i)) != null) {
					if(g.currLevel == 1 || g.currLevel == 3) {//temp
						g.rooms.get(g.doors.get(i).getRoomNum()).removeRoomFog();
					}
				}
			}
			if(sideX - 1 < 0) {
				t = m.getTile(sideX - 1, sideY);
				if((!g.doors.get(i).getIsPass()) && collides(g.doors.get(i)) != null) {
					setX(t.getCoarseGrainedMaxX()+pushback);
				}else if(collides(g.doors.get(i)) != null) {
					if(g.currLevel == 1 || g.currLevel == 3) {//temp
						g.rooms.get(g.doors.get(i).getRoomNum()).removeRoomFog();
					}
				}
			}
			if(sideX - 1 < 0) {
				t = m.getTile(sideX, sideY - 1);
				if((!g.doors.get(i).getIsPass()) && collides(g.doors.get(i)) != null) {
					setY(t.getCoarseGrainedMaxY() + pushback);
				}else if(collides(g.doors.get(i)) != null) {
					if(g.currLevel == 1 || g.currLevel == 3) {//temp
						g.rooms.get(g.doors.get(i).getRoomNum()).removeRoomFog();
					}
				}
			}
		}
	}
	public void checkCollision(Map m) {
		
		// get all 4 adjacent tiles next to player
		Tile t;
		int sideX = (int) Math.floor(getX() / m.getTileSize());
		int sideY = (int) Math.floor(getY() / m.getTileSize());
		// checking W side
		if(sideX + 1 < m.getNumOfTilesX()) {
			t = m.getTile(sideX + 1, sideY);
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
	

	
	// permanemently adjust stats of the player (perma items)
	public void adjustStats(Item i) {
		if(i.getType() == "hammer") {
			attack_damage += 1;
		}
		if(i.getType() == "shield") {
			total_shield_hp = curr_shield_hp += 10;
		}
		if(i.getType() == "feather") {
			movement_speed += 0.025f;
		}
		if(i.getType() == "arrow") {
			bullet_speed += 0.05f;
		}
		if(i.getType() == "accelerator" || i.getType() == "fiery") {
			activatable = true;
		}
	}
	
	public void cheatStats() {
		saveStats();
		attack_damage = 100;
		bullet_speed = 0.7f;
		rate_of_fire = 10;
		movement_speed = 0.5f;
	}
	
	public void saveStats() {
		bullet_speed_buffer = bullet_speed;
		rate_of_fire_buffer = rate_of_fire;
		movement_speed_buffer = movement_speed;
		attack_damage_buffer = attack_damage;
	}
	
	public void returnStats() {
		bullet_speed = bullet_speed_buffer;
		rate_of_fire = rate_of_fire_buffer;
		attack_damage = attack_damage_buffer;
		movement_speed = movement_speed_buffer;
	}
	
	// do the action of the target activatable item
	public void doAction(Item i) {
		if(i.getType() == "accelerator") {
			saveStats();
			bullet_speed = 0.5f;
			rate_of_fire = 250;
			// start the active timer
			action_duration = 3000;
			// reset values back to normal once it finishes
		}
		if(i.getType() == "fiery") {
		}
	}
	
	private void updateVariables(final int delta, boolean isCheatMode) {
		rotate_delay -= delta;
		shoot_delay -= delta;
		active_delay -= delta;
		action_duration -= delta;
		take_damage_delay -= delta;
		if(action_duration <= 0 && !isCheatMode)
			returnStats();
		if(take_damage_delay <= -5000)
			curr_shield_hp = total_shield_hp;
	}
	

	public void update(StateBasedGame game, final int delta) {
		Game g = (Game) game;
		checkBounds(g.map);
		checkTimeMachineWin(g);
		if(!g.cheatMode) {
			checkDoorCollision(g,g.map);
			checkCollision(g.map);
			// check if the player collides with any enemy
			for(int i = 0; i < g.enemy.size(); i++) {
				if(this.collides(g.enemy.get(i)) != null && take_damage_delay <= 0)
					takeDamage(5);
			}
			if(getHP()<=0) {
				g.LIVES = g.LIVES - 1;
				if(g.LIVES <= 0) {
					setHP(100);
					g.LIVES = 2;
					g.map.clearAndReset(g);
					g.enterState(Game.GAMEOVERSTATE);
				}
				else {
					setHP(100);
				}
			}
		}
		updateVariables(delta, g.cheatMode);
		//System.out.println(this.getX() +" "+this.getY());
		
		translate(velocity.scale(delta * movement_speed));
	}

}
