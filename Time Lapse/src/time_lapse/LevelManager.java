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