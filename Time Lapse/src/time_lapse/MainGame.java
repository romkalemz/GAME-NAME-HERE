package time_lapse;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;


public class MainGame extends StateBasedGame {
	
	// possible states
	public static final int SPLASHSCREEN = 0;
	public static final int PLAYINGSTATE = 1;
	
	public static final String PLAYER_DEFAULT_RSC = "resources/player_default.png";;
	public static final String SPLASH_SCREEN_RSC = "resources/splash_screen.png";;

	// resource strings
	
	
	// items in the game
	public Player player;
	
	
	
	public MainGame(String title) {
		super(title);
		
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// Load states
		addState(new SplashScreen());
		addState(new PlayingState());
		
		// LOAD RESOURCES
		ResourceManager.loadImage(PLAYER_DEFAULT_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_RSC);
		
		
		player = new Player(400, 300);
	}
	
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MainGame("Time Lapse v0.1"));
			app.setDisplayMode(1200, 800, false);
			app.setVSync(true);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
}
