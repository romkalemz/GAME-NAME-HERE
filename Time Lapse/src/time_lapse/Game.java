package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;


public class Game extends StateBasedGame {
	
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
	public static final String PLAYER_DEFAULT_RSC = "resources/player_default.png";
	public static final String SPLASH_SCREEN_RSC = "resources/splash_screen.jpg";
	public static final String TILE_DIRT_RSC = "resources/dirt_tile.png";	
	public static final String TREE_DIRT_RSC = "resources/tree_tile.png";	
	public static final String LEFT_TREE_DIRT_RSC = "resources/right_tree_tile.png";	
	public static final String RIGHT_TREE_DIRT_RSC = "resources/left_tree_tile.png";	

	// items in the game
	public Player player;
	public Map map;
	public Debug debug;
	public ArrayList<Enemy> enemy;
	
	
	public Game(String title) {
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
		ResourceManager.loadImage(TREE_DIRT_RSC);
		ResourceManager.loadImage(LEFT_TREE_DIRT_RSC);
		ResourceManager.loadImage(RIGHT_TREE_DIRT_RSC);

		map = new Map(NUM_OF_TILESX, NUM_OF_TILESY, TILESIZE);
		enemy = new ArrayList<Enemy>();
		enemy = EnemySpawner.Spawn(enemy, 200, 300, 1);
		enemy = EnemySpawner.Spawn(enemy, 500, 500, 2);
		enemy = EnemySpawner.Spawn(enemy, 400, 800, 3);
		player = new Player(400, 300);
		debug = new Debug(10,20,"asdfasdf");

	}
}