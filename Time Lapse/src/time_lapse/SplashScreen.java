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
	private int spacetimer;
	private int storycount;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Game tl = (Game)game;
		LevelManager.setLevel(game, tl.currLevel);
		tl.player.reset(false);
		tl.UIHandler.activatable = null;
		spacetimer = 300;
		storycount = 0;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

			if(timer < 501 ) {
				g.drawImage(ResourceManager.getImage(Game.SPLASH_SCREEN_ON_RSC), 0,
						0);
				if(timer <= 0) {
					timer = 1500;
				}
			}else if (timer > 500 && timer <= 1500) {
				g.drawImage(ResourceManager.getImage(Game.SPLASH_SCREEN_OFF_RSC), 0,
						0);
				if(timer <= 0) {
					timer = 1500;
				}
				
			}
		container.setShowFPS(true);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();

		timer -= delta;
		if(spacetimer <= 0) {
			if(input.isKeyDown(Input.KEY_SPACE)) {
				game.enterState(Game.TRANSITIONSTATE, new EmptyTransition(), new HorizontalSplitTransition());
			}
		}
		if(input.isKeyDown(Input.KEY_1)) {			
			game.enterState(Game.SPLASHSCREEN);
		}
		if(input.isKeyDown(Input.KEY_2)) {			
			game.enterState(Game.PLAYINGSTATE);
		}
		spacetimer -= delta;
	}

	@Override
	public int getID() {
		return Game.SPLASHSCREEN;
	}
	
}