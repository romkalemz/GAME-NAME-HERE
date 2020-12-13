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
	public static final int TRANSITIONSTATE = 2;
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
	public static final String SPLASH_SCREEN_ON_RSC = "resources/light_on.jpg";
	public static final String SPLASH_SCREEN_DIM_RSC = "resources/light_dim.jpg";
	public static final String SPLASH_SCREEN_OFF_RSC = "resources/light_off.jpg";
	public static final String TILE_DIRT_RSC = "resources/dirt_tile.png";	
	public static final String TREE_DIRT_RSC = "resources/tree_tile.png";	
	public static final String LEFT_TREE_DIRT_RSC = "resources/right_tree_tile.png";	
	public static final String RIGHT_TREE_DIRT_RSC = "resources/left_tree_tile.png";
	public static final String BOULDER_RSC = "resources/boulder_tile.png";	
	public static final String LEVEL1_TRANSITION_RSC = "resources/level1_transition.png";
	public static final String LEVEL2_TRANSITION_RSC = "resources/level2_transition.png";
	public static final String arrow = "resources/arrow.png";
	public static final String UI_BG_RSC = "resources/gameUI.png";
	public static final String PROJECTILE_DEFAULT_RSC = "resources/bullet.png";
	public static final String ITEM_HAMMER_RSC = "resources/hammer.png";
	public static final String ITEM_FEATHER_RSC = "resources/item_temp.png";
	public static final String ITEM_SHIELD_RSC = "resources/item_temp.png";
	public static final String ITEM_ARROW_RSC = "resources/item_temp.png";
	public static final String ITEM_ACCELERATOR_RSC = "resources/item_temp.png";
	public static final String ITEM_FIERY_RSC = "resources/item_temp.png";

	// items in the game
	public Player player;
	public Map map;
	
	public int currLevel = 1;
	
	public ArrayList<Enemy> enemy;
	public ArrayList<Item> items;
	public ArrayList<Projectile> projectiles;
	
	public Debug debug;
	public UIHandler UIHandler;
	public ImageManager image_control;
	
	
	public Game(String title) {
		super(title);
		
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// Load states
		addState(new SplashScreen());
		addState(new PlayingState());
		addState(new TransitionState());
		
		// LOAD RESOURCES
		ResourceManager.loadImage(PLAYER_DEFAULT_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_ON_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_DIM_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_OFF_RSC);
		ResourceManager.loadImage(TILE_DIRT_RSC);
		ResourceManager.loadImage(BOULDER_RSC);
		ResourceManager.loadImage(TREE_DIRT_RSC);
		ResourceManager.loadImage(LEFT_TREE_DIRT_RSC);
		ResourceManager.loadImage(RIGHT_TREE_DIRT_RSC);
		ResourceManager.loadImage(ITEM_HAMMER_RSC);
		ResourceManager.loadImage(UI_BG_RSC);
		ResourceManager.loadImage(PROJECTILE_DEFAULT_RSC);
		ResourceManager.loadImage(ITEM_FEATHER_RSC);
		ResourceManager.loadImage(ITEM_SHIELD_RSC);
		ResourceManager.loadImage(LEVEL1_TRANSITION_RSC);
		ResourceManager.loadImage(LEVEL2_TRANSITION_RSC);
		
		// Initialize stuff
		map = new Map(NUM_OF_TILESX, NUM_OF_TILESY, TILESIZE);	
		player = new Player(400, 300);
		enemy = new ArrayList<Enemy>();		
		items = new ArrayList<Item>();
		projectiles = new ArrayList<Projectile>();

		debug = new Debug(10,20,"asdfasdf");
		UIHandler = new UIHandler(ResourceManager.getImage(UI_BG_RSC));
		
		// load images for all active entities / tiles
		image_control = new ImageManager();

	}
}
