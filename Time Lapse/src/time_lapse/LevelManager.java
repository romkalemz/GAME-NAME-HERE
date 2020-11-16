package time_lapse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.newdawn.slick.state.StateBasedGame;

/*
 * LevelManager is static class used for setting up different levels
 */

public class LevelManager {

	public static void setLevel(StateBasedGame game) {
		MainGame tl = (MainGame) game;

		// Store array of strings from txt file of level
		String[] mapData = getLevelText(1);
		
		int tileSize = tl.map.getTileSize();
		int viewStart = tileSize / 2;
		
		for(int y = 0; y < tl.map.getMapSizeY(); y++) {
			int [] type = new int[mapData[y].length()];
			for (int i = 0; i < mapData[y].length(); i ++) {
				//Convert each char to int
				type[i] = Character.getNumericValue(mapData[y].charAt(i));
			}
			for(int x = 0; x < tl.map.getMapSizeX(); x++) {
				if(type[x] == 0) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x]));
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
