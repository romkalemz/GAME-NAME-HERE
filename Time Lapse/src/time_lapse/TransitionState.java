package time_lapse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import jig.ResourceManager;

public class TransitionState extends BasicGameState {

	private int timer;
	private int story;
	private boolean next;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		story = 0;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Game tl = (Game)game;
		if(tl.currLevel != 4) {
		LevelManager.setLevel(game, tl.currLevel);
		if(tl.currLevel == 3) {
			tl.player.setPosition(200, 300);
		}else {
		tl.player.setPosition(400, 300);
			}
		}
		timer = 5000;
		//story = 0;
		next = false;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		Game tl = (Game)game;
		if(story == 0) {
			g.drawImage(ResourceManager.getImage(Game.story1), 300, 300);
		}
		if(story == 1) {
			g.drawImage(ResourceManager.getImage(Game.story2), 250, 300);
		}
		if(story == 2) {
			g.drawImage(ResourceManager.getImage(Game.story3), 0, 0);
		}
		if(story == 3) {
			g.drawImage(ResourceManager.getImage(Game.story4), 300, 300);
		}
		if(story == 4) {
			g.drawImage(ResourceManager.getImage(Game.story5), 300, 265);
			next = true;
		}
		if(story == 5) {
			next = true;
		}
		if(tl.currLevel == 2) {
			g.drawImage(ResourceManager.getImage(Game.story6), 300, 265);
		} else if(tl.currLevel == 3) {
			g.drawImage(ResourceManager.getImage(Game.story7), 300, 265);
		}else if(tl.currLevel == 4) {
			g.drawImage(ResourceManager.getImage(Game.story8), 350, 265);
		}
		if(timer <= 0 && !next) {
			story++;
			timer = 8000;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		Game tl = (Game)game;
		timer -= delta;
		if(input.isKeyDown(Input.KEY_SPACE)) {
				if(tl.currLevel != 4) {
					story = 6;
					next = false;
					game.enterState(Game.PLAYINGSTATE);
				}
				else {
					tl.currLevel = 1;
					tl.map.clearAndReset(tl);
					game.enterState(Game.SPLASHSCREEN);
					
				}	
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.TRANSITIONSTATE;
	}

}
