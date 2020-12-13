package time_lapse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.newdawn.slick.state.StateBasedGame;

/*
 * LevelManager is static class used for setting up different levels
 */

public class LevelManager {

	public static void setLevel(StateBasedGame game, int lvlNum) {
		Game tl = (Game) game;
		
		tl.map.clearAndReset(tl);
		
		// Store array of strings from txt file of level
		String[] mapData = getLevelText(lvlNum);
		
		int tileSize = tl.map.getTileSize();
		int viewStart = tileSize / 2;
		
		/* This code is for the random tree spawning */
		Random randomNum = new Random();
		int lowerBound = 3; // In Tile class we have 3 different kinds of trees with types 3-5.
		int upperBound = 6; // This will give us a random number between that range.
		int random = 0;
		
		for(int y = 0; y < tl.map.getNumOfTilesY(); y++) {
			int [] type = new int[mapData[y].length()];
			for (int i = 0; i < mapData[y].length(); i ++) {
				//Convert each char to int
				if(Character.getNumericValue(mapData[y].charAt(i)) ==  0) {	// Normal dirt tile
					type[i] = 0;
				}
				if(Character.getNumericValue(mapData[y].charAt(i)) ==  6) {	// Boulder tiles
					type[i] = 6;
				}
				if(Character.getNumericValue(mapData[y].charAt(i)) ==  3) {	// Get random tree tiles
					random = randomNum.nextInt(upperBound - lowerBound) + lowerBound;
					type[i] = random;
					//System.out.println(random);
				}
				
			}
			for(int x = 0; x < tl.map.getNumOfTilesX(); x++) {
				/* This is how we will spawn certain kinds of tiles */
				/* Each tile has a value assigned to it which can be connected with an image in the Tile class */
				if(type[x] == 0) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x], 0));
				}
				if(type[x] == 3) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x], 1));
				}
				if(type[x] == 4) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x], 1));
				}
				if(type[x] == 5) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x], 1));
				}
				if(type[x] == 6) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), 6, 6));
				}
			}
		}
		
		// set entities corresponding to lvl
		if(lvlNum == 1) {
			setLevel1(tl);
		} else if (lvlNum == 2) {
			setLevel2(tl);
		} else if (lvlNum == 3) {
			setLevel3(tl);
		}
		
	}
	private static void setLevel1(Game tl) {
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 200, 300, 1);
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 500, 500, 2);
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 400, 800, 3);
		
		tl.items = ItemHandler.Spawn(tl.items, 200, 400, "hammer");
		tl.items = ItemHandler.Spawn(tl.items, 270, 450, "hammer");
		tl.items = ItemHandler.Spawn(tl.items, 300, 500, "feather");
		tl.items = ItemHandler.Spawn(tl.items, 100, 200, "shield");
		tl.items = ItemHandler.Spawn(tl.items, 240, 200, "arrow");
		tl.items = ItemHandler.Spawn(tl.items, 250, 400, "accelerator");
		tl.items = ItemHandler.Spawn(tl.items, 500, 400, "fiery");

		
		// load images for all active entities / tiles
		tl.image_control.setImage(tl.items.get(0), Game.ITEM_HAMMER_RSC);
		tl.image_control.setImage(tl.items.get(1), Game.ITEM_HAMMER_RSC);
		tl.image_control.setImage(tl.items.get(2), Game.ITEM_FEATHER_RSC);
		tl.image_control.setImage(tl.items.get(3), Game.ITEM_SHIELD_RSC);
		tl.image_control.setImage(tl.items.get(4), Game.ITEM_ARROW_RSC);
		tl.image_control.setImage(tl.items.get(5), Game.ITEM_ACCELERATOR_RSC);
		tl.image_control.setImage(tl.items.get(6), Game.ITEM_FIERY_RSC);
		
		
		
	}
	private static void setLevel2(Game tl) {
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 200, 300, 1);
	}
	private static void setLevel3(Game tl) {
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 200, 300, 1);
	}
	// Reads level text file and converts into string array
	private static String[] getLevelText(int level) {
		URL u = ClassLoader.getSystemResource("./resources/Level" + level);
		Path filePath = null;
		String content = "";
		try {
			filePath = Paths.get(u.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			content = Files.readString(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = content.replace("\n", "").replace("\r", "");
		return content.split("-");
	}

}