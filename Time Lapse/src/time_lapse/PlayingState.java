package time_lapse;


import jig.Entity;
import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class PlayingState extends BasicGameState {
	private boolean debugMode;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Game tl = (Game)game;
		debugMode = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		Game tl = (Game)game;
		
		// render entities
		tl.map.renderMap(g);
		if(debugMode)
			tl.debug.renderDebug(g, tl);
		tl.player.render(g);
		
		// render enemies
		if(!tl.enemy.isEmpty()) {
			for(int i = 0; i < tl.enemy.size(); i++) {
				tl.enemy.get(i).render(g);
			}
		}
		// render items
		if(!tl.items.isEmpty()) {
			for(int i = 0; i < tl.items.size(); i++) {
				tl.items.get(i).render(g);
			}
		}
		// render projectiles
		if(!tl.projectiles.isEmpty()) {
			for (int i = 0; i <tl.projectiles.size(); i++) {
				tl.projectiles.get(i).render(g);
			}
		}
		
		// render UI
		tl.UIHandler.render(tl, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	
		Input input = container.getInput();
		Game tl = (Game)game;
		if(input.isKeyPressed(Input.KEY_F1)) {
			if(debugMode)
				debugMode = false;
			else
				debugMode = true;
		}
		
		playerMove(tl, input);
		
		itemCollision(tl, delta);
		
		tl.map.updateMap(game);
		tl.player.update(tl, delta);
		
		// update each bullet on the map
		for(int i = 0; i < tl.projectiles.size(); i++) {
			tl.projectiles.get(i).update(delta);
			if(tl.projectiles.get(i).hitOrMiss(game)) {
				tl.projectiles.remove(i);
			}
		}
		
	}

	private void itemCollision(Game g, int delta) {
		// remove items that have collided with the player
		for(int i = 0; i < g.items.size(); i++) {
			Item item = g.items.get(i);
			
			if(g.player.collides(item) != null) {
				// add item to the UI
				g.UIHandler.addItem(item);
				
				// remove item from the map
				g.items.remove(i);
			}
			
		}
		
	}

	private void playerMove(Game tl, Input input) {
		tl.player.setVelocity(new Vector(0, 0));
		
		if (input.isKeyDown(Input.KEY_1)) {
			tl.currLevel = 1;
			tl.enterState(Game.SPLASHSCREEN);
		}
		if(input.isKeyDown(Input.KEY_2)) {
			tl.currLevel = 2;
			tl.enterState(Game.TRANSITIONSTATE);
		}
		if(input.isKeyDown(Input.KEY_3)) {
			tl.currLevel = 3;
			tl.enterState(Game.TRANSITIONSTATE);
		}
		// player movement
		if (input.isKeyDown(Input.KEY_W)) {
			tl.player.setVelocity(tl.player.getVelocity().add(new Vector(0, -1)));
		}
		if (input.isKeyDown(Input.KEY_S)) {
			tl.player.setVelocity(tl.player.getVelocity().add(new Vector(0, 1)));
		}
		if (input.isKeyDown(Input.KEY_A)) {
			tl.player.setVelocity(tl.player.getVelocity().add(new Vector(-1, 0)));
		}
		if (input.isKeyDown(Input.KEY_D)) {
			tl.player.setVelocity(tl.player.getVelocity().add(new Vector(1, 0)));
		}	
		
		// player direction / aim
		// wait for a slight cooldown to allow slower response times to angled facing position
		if (tl.player.getRotateDelay() <= 0) {
			if (input.isKeyDown(Input.KEY_UP)) {
				tl.player.setImageRotation(180);
				if(tl.player.getShootDelay() <= 0) {
					addProjectile(tl, tl.player, new Vector(0, -1));
					tl.player.setShootDelay();
				}
			}	
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				tl.player.setImageRotation(270);
				if(tl.player.getShootDelay() <= 0) {
					addProjectile(tl, tl.player, new Vector(1, 0));
					tl.player.setShootDelay();
				}	
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				tl.player.setImageRotation(0);
				if(tl.player.getShootDelay() <= 0) {
					addProjectile(tl, tl.player, new Vector(0, 1));
					tl.player.setShootDelay();
				}
			}
			if (input.isKeyDown(Input.KEY_LEFT)) {
				tl.player.setImageRotation(90);
				if(tl.player.getShootDelay() <= 0) {
					addProjectile(tl, tl.player, new Vector(-1, 0));
					tl.player.setShootDelay();
				}
			}
		}
		
		if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {
			tl.player.setImageRotation(225);
			tl.player.setRotateDelay(50);
			if(tl.player.getShootDelay() <= 0) {
				addProjectile(tl, tl.player, new Vector(1, -1));
				tl.player.setShootDelay();
			}
			
		}
		if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_DOWN)) {
			tl.player.setImageRotation(315);
			tl.player.setRotateDelay(50);
			if(tl.player.getShootDelay() <= 0) {
				addProjectile(tl, tl.player, new Vector(1, 1));
				tl.player.setShootDelay();
			}
			
		}
		if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {
			tl.player.setImageRotation(45);
			tl.player.setRotateDelay(50);
			if(tl.player.getShootDelay() <= 0) {
				addProjectile(tl, tl.player, new Vector(-1, 1));
				tl.player.setShootDelay();
			}
			
		}
		if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {
			tl.player.setImageRotation(135);
			tl.player.setRotateDelay(50);
			if(tl.player.getShootDelay() <= 0) {
				addProjectile(tl, tl.player, new Vector(-1, -1));
				tl.player.setShootDelay();
			}
		}
	}
	
	private void addProjectile(Game g, Entity e, Vector v) {
		Projectile p = new Projectile(e.getX(), e.getY());
		if (v == null) {
			// find the direction for the bullets to travel to
			// check if the entity is an enemy
//			p.isFromEnemy = true;
//			Vector playerPos = g.player.getPosition();
//			if (playerPos != e.getPosition()) {
//				double dir = playerPos.subtract(e.getPosition()).getRotation();
//				v = new Vector(1, 1).setRotation(dir);
//				p.setSpeed(0.2f);
//			}
		} else {
			// bullet is from the player, adjust speed and damage
			g.image_control.setImage(p, Game.PROJECTILE_DEFAULT_RSC, (int)g.player.getImageRotation() + 90, false);
			p.setDamage(g.player.getAttackDamage());
			p.setSpeed(g.player.getBulletSpeed());
			
//			g.player_shoot_cooldown = g.player.rof;
		}
		p.setDirection(e, v);
		g.projectiles.add(p);
	}
	
	
	@Override
	public int getID() {
		return Game.PLAYINGSTATE;
	}
}
