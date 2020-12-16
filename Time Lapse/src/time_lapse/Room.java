package time_lapse;

import java.util.ArrayList;

/*
 * Room class holds tiles that make it up. 
 * The roomNum corresponds to what position the
 * room needs to be in the Arraylist in Game.java. The Door.java class holds roomNum it opens
 *  which corresponds to the index the room is in the rooms array as well. 
 */
public class Room {
	private int roomNum;
	public ArrayList<Tile> roomTiles;

	public Room(int roomNum, int lvl, Game tl) {
		this.roomNum = roomNum;
		roomTiles = new ArrayList<Tile>();
		if (lvl == 1) {
			setFogLvl1(tl);
		} else if (lvl == 2) {
			setFogLvl2(tl);
		} else if (lvl == 3) {
			setFogLvl3(tl);
		}
	}

	private void setFogLvl1(Game tl) {
		if (roomNum == 0) {
			for (int x = 25; x < 55; x++) {
				for (int y = 2; y < 10; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						if (!(y >= 6 && x >= 50 && x <= 54)) {
							tl.map.getTile(x, y).addTileFog();
							roomTiles.add(tl.map.getTile(x, y));
						}
					}
				}
			}
		} else if (roomNum == 1) {
			for (int x = 0; x < 33; x++) {
				for (int y = 17; y < 30; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						tl.map.getTile(x, y).addTileFog();
						roomTiles.add(tl.map.getTile(x, y));
					}
				}
			}
		} else if (roomNum == 2) {
			for (int x = 50; x < 72; x++) {
				for (int y = 2; y < 18; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						if (!(y <= 6 && x >= 50 && x <= 54)) {
							tl.map.getTile(x, y).addTileFog();
							roomTiles.add(tl.map.getTile(x, y));
						}
					}
				}
			}
		} else if (roomNum == 3) {
			for (int x = 35; x < 72; x++) {
				for (int y = 18; y < 30; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						tl.map.getTile(x, y).addTileFog();
						roomTiles.add(tl.map.getTile(x, y));
					}
				}
			}
		} else if (roomNum == 4) {
			for (int x = 27; x < 72; x++) {
				for (int y = 30; y < 38; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						tl.map.getTile(x, y).addTileFog();
						roomTiles.add(tl.map.getTile(x, y));
					}
				}
			}
		} else if (roomNum == 5) {
			for (int x = 0; x < 27; x++) {
				for (int y = 31; y < 38; y++) {
					if (tl.map.getTile(x, y).getType() != 3) {
						tl.map.getTile(x, y).addTileFog();
						roomTiles.add(tl.map.getTile(x, y));
					}
				}
			}
		}
	}

	private void setFogLvl2(Game tl) {

	}

	private void setFogLvl3(Game tl) {

	}

	public void removeRoomFog() {
		System.out.println("yo");
		for (int i = 0; i < roomTiles.size(); i++) {
			roomTiles.get(i).removeImage(roomTiles.get(i).fogImg);
		}
	}

}
