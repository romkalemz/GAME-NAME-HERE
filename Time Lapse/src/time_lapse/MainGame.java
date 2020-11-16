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
	
	/*
	 * TODO: Will need to adjust MapSize later
	 * Map will be larger with scrolling
	 * May need to adjust tilesize as needed
	 */
	public static final int TILESIZE = 32;
	public static final int NUM_OF_TILESX = 74;
	public static final int NUM_OF_TILESY = 40;
	
	// resource strings
	public static final String PLAYER_DEFAULT_RSC = "resources/player_default.png";;
	public static final String SPLASH_SCREEN_RSC = "resources/splash_screen.png";;
	public static final String TILE_DIRT_RSC = "resources/dirt_tile.png";	
	
	// items in the game
	public Player player;
	public Map map;
	public Debug debug;
	public Enemy enemy;
	public Server GameHandler;
	public Client PlayerHandler;
	
	
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
		ResourceManager.loadImage(TILE_DIRT_RSC);
		
		map = new Map(NUM_OF_TILESX, NUM_OF_TILESY, TILESIZE);
		player = new Player(400, 300);
		enemy = new Enemy(200, 400, 1);
		debug = new Debug(10,20,"asdfasdf");
		
		//Start threads for server/client
		Thread serverThread = new Server();
		serverThread.start();
		
		Thread clientThread = new Client();
		clientThread.start();

	}
	
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MainGame("Time Lapse v0.1"));
			app.setDisplayMode(1200, 800, false);
			app.setVSync(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
}
