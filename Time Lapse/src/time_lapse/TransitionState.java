package time_lapse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import jig.ResourceManager;

public class TransitionState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Game tl = (Game)game;
		LevelManager.setLevel(game, tl.currLevel);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		Game tl = (Game)game;
		if(tl.currLevel == 2) {
			g.drawImage(ResourceManager.getImage(Game.LEVEL1_TRANSITION_RSC), 450, 300);
		} else if(tl.currLevel == 3) {
			g.drawImage(ResourceManager.getImage(Game.LEVEL2_TRANSITION_RSC), 450, 300);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_SPACE)) {
			game.enterState(Game.PLAYINGSTATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.TRANSITIONSTATE;
	}

}
