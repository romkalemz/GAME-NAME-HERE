package time_lapse;


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
		tl.debug.renderDebug(g, debugMode);
		tl.player.render(g);
		
		if(!tl.enemy.isEmpty()) {
			for(int i = 0; i < tl.enemy.size(); i++) {
				tl.enemy.get(i).render(g);
			}
		}
		//tl.enemy.render(g);
		
		if(!tl.items.isEmpty()) {
			for(int i = 0; i < tl.items.size(); i++) {
				tl.items.get(i).render(g);
			}
		}
		
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
			tl.enterState(Game.SPLASHSCREEN);
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
				tl.player.setRotation(180);
				
			}
			
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				tl.player.setRotation(270);
				
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				tl.player.setRotation(0);
				

			}
			if (input.isKeyDown(Input.KEY_LEFT)) {
				tl.player.setRotation(90);
				
			}
		}
		
		if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {
			tl.player.setRotation(225);
			tl.player.setRotateDelay(50);
			
		}
		if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_DOWN)) {
			tl.player.setRotation(315);
			tl.player.setRotateDelay(50);
			
		}
		if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {
			tl.player.setRotation(45);
			tl.player.setRotateDelay(50);
			
		}
		if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {
			tl.player.setRotation(135);
			tl.player.setRotateDelay(50);
		}
	}
	
	
	@Override
	public int getID() {
		return Game.PLAYINGSTATE;
	}
}
