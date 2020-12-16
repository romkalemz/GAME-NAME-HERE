package time_lapse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
				if(Character.getNumericValue(mapData[y].charAt(i)) ==  7) {
					type[i] = 7;
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
				if(type[x] == 7) {
					tl.map.setTile(x, y, new Tile(viewStart + (x*tileSize), viewStart + (y*tileSize), type[x], 0));
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
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 700, 600, 3);
		
		tl.machine = new TimeMachine(20, 1420);
		
		tl.items = ItemHandler.Spawn(tl.items, 200, 400, "hammer");
		tl.items = ItemHandler.Spawn(tl.items, 270, 450, "hammer");
		tl.items = ItemHandler.Spawn(tl.items, 300, 550, "feather");
		tl.items = ItemHandler.Spawn(tl.items, 100, 200, "shield");
		tl.items = ItemHandler.Spawn(tl.items, 240, 200, "arrow");
		tl.items = ItemHandler.Spawn(tl.items, 250, 400, "accelerator");
		tl.items = ItemHandler.Spawn(tl.items, 500, 400, "fiery");
		
		tl.items = ItemHandler.Spawn(tl.items, 900, 100, "machine_piece1");
		tl.items = ItemHandler.Spawn(tl.items, 900, 1180, "machine_piece2");
		tl.items = ItemHandler.Spawn(tl.items, 2780, 540, "machine_piece3");
		tl.items = ItemHandler.Spawn(tl.items, 2583, 1020, "machine_piece4");
		tl.items = ItemHandler.Spawn(tl.items, 360, 1483, "machine_piece5");


		//doors for sublevels of level1
		ArrayList<Door> door1 = new ArrayList<Door>();
		door1.add(new Door(24*tl.map.getTileSize() + tl.map.getTileSize()/2, 8.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 90, 0));
		door1.add(new Door(17.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 16f*tl.map.getTileSize() + tl.map.getTileSize()/2, 180, 1));
		ArrayList<Door> door2 = new ArrayList<Door>();
		door2.add(new Door(54*tl.map.getTileSize() + tl.map.getTileSize()/2, 4.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 90, 2));
		ArrayList<Door> door3 = new ArrayList<Door>();
		door3.add(new Door(58.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 17f*tl.map.getTileSize() + tl.map.getTileSize()/2, 180, 3));
		ArrayList<Door> door4 = new ArrayList<Door>();
		door4.add(new Door(44.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 29f*tl.map.getTileSize() + tl.map.getTileSize()/2, 180, 4));
		ArrayList<Door> door5 = new ArrayList<Door>();
		door5.add(new Door(27f*tl.map.getTileSize() + tl.map.getTileSize()/2, 34.5f*tl.map.getTileSize() + tl.map.getTileSize()/2, 90, 5));
		
		//adding switches for corresponding doors
		tl.doorSwitch.add(new DoorSwitch(10*tl.map.getTileSize() + tl.map.getTileSize()/2, 8f*tl.map.getTileSize() + tl.map.getTileSize()/2, 0, door1));
		tl.doorSwitch.add(new DoorSwitch(2*tl.map.getTileSize() + tl.map.getTileSize()/2, 29f*tl.map.getTileSize() + tl.map.getTileSize()/2, 0, door2));
		tl.doorSwitch.add(new DoorSwitch(54*tl.map.getTileSize() + tl.map.getTileSize()/2, 14f*tl.map.getTileSize() + tl.map.getTileSize()/2, 0, door3));
		tl.doorSwitch.add(new DoorSwitch(35*tl.map.getTileSize() + tl.map.getTileSize()/2, 18f*tl.map.getTileSize() + tl.map.getTileSize()/2, 0, door4));
		tl.doorSwitch.add(new DoorSwitch(55*tl.map.getTileSize() + tl.map.getTileSize()/2, 34f*tl.map.getTileSize() + tl.map.getTileSize()/2, 0, door5));
		//adding all doors for entire level1
		tl.doors.add(door1.get(0));
		tl.doors.add(door1.get(1));
		tl.doors.add(door2.get(0));
		tl.doors.add(door3.get(0));
		tl.doors.add(door4.get(0));
		tl.doors.add(door5.get(0));
		// load images for all active entities / tiles
		tl.image_control.setImage(tl.items.get(0), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(1), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(2), Game.ITEM_FEATHER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(3), Game.ITEM_SHIELD_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(4), Game.ITEM_ARROW_RSC, 0, true);
		//tl.image_control.setImage(tl.items.get(5), Game.ITEM_ACCELERATOR_RSC, 0, true);
		//tl.image_control.setImage(tl.items.get(6), Game.ITEM_FIERY_RSC, 0, true);
		tl.image_control.setImage(tl.machine, Game.ITEM_HAMMER_RSC, 0, true);
		// add all rooms in lvl one 
		// NOTE: the roomNum in Door.java must match the index of the room it opens!
		tl.rooms.add(new Room(0, 1, tl));
		tl.rooms.add(new Room(1, 1, tl));
		tl.rooms.add(new Room(2, 1, tl));
		tl.rooms.add(new Room(3, 1, tl));
		tl.rooms.add(new Room(4, 1, tl));
		tl.rooms.add(new Room(5, 1, tl));
	}
	private static void setLevel2(Game tl) {
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 200, 300, 1);
		
		tl.machine = new TimeMachine(20, 1460);
		
		tl.items = ItemHandler.Spawn(tl.items, 1700, 180, "machine_piece1");
		tl.items = ItemHandler.Spawn(tl.items, 2700, 180, "machine_piece2");
		tl.items = ItemHandler.Spawn(tl.items, 2340, 500, "machine_piece3");
		tl.items = ItemHandler.Spawn(tl.items, 2860, 540, "machine_piece4");
		tl.items = ItemHandler.Spawn(tl.items, 860, 1460, "machine_piece5");
		tl.items = ItemHandler.Spawn(tl.items, 2060, 1500, "machine_piece6");
		tl.items = ItemHandler.Spawn(tl.items, 300, 1380, "machine_piece7");
		tl.items = ItemHandler.Spawn(tl.items, 840, 540, "machine_piece8");
		
		tl.image_control.setImage(tl.items.get(0), Game.ITEM_FEATHER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(1), Game.ITEM_FEATHER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(2), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(3), Game.ITEM_SHIELD_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(4), Game.ITEM_ARROW_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(5), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(6), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(7), Game.ITEM_FEATHER_RSC, 0, true);

	}
	private static void setLevel3(Game tl) {
		tl.enemy = EnemySpawner.Spawn(tl.enemy, 200, 300, 1);
		
		tl.machine = new TimeMachine(20, 740);
		
		tl.items = ItemHandler.Spawn(tl.items, 2780, 180, "machine_piece1");
		tl.items = ItemHandler.Spawn(tl.items, 2740, 1340, "machine_piece2");
		tl.items = ItemHandler.Spawn(tl.items, 900, 1220, "machine_piece3");
		tl.items = ItemHandler.Spawn(tl.items, 260, 940, "machine_piece4");
		
		tl.image_control.setImage(tl.items.get(0), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(1), Game.ITEM_HAMMER_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(2), Game.TRANSPARENT_RSC, 0, true);
		tl.image_control.setImage(tl.items.get(3), Game.ITEM_SHIELD_RSC, 0, true);
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