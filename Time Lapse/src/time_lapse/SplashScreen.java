package time_lapse;


import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


/**
 * This state is active when the Game is over. In this state, the ball is
 * neither drawn nor updated; and a gameover banner is displayed. A timer
 * automatically transitions back to the StartUp State.
 * 
 * Transitions From PlayingState
 * 
 * Transitions To StartUpState
 */
class SplashScreen extends BasicGameState {
	
	private int timer;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 3000;
		LevelManager.setLevel(game);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		g.drawImage(ResourceManager.getImage(MainGame.SPLASH_SCREEN_RSC), 40,
				40);
		container.setShowFPS(false);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();

		timer -= delta;
		if (timer <= 0)
			game.enterState(MainGame.PLAYINGSTATE, new EmptyTransition(), new HorizontalSplitTransition() );

		
		if(input.isKeyDown(Input.KEY_1)) {			
			game.enterState(MainGame.SPLASHSCREEN);
		}
		if(input.isKeyDown(Input.KEY_3)) {			
			game.enterState(MainGame.PLAYINGSTATE);
		}

	}

	@Override
	public int getID() {
		return MainGame.SPLASHSCREEN;
	}
	
}