package time_lapse;

import java.util.ArrayList;

public class ItemHandler {

	public static ArrayList<Item> Spawn(ArrayList<Item> temp, float x, float y, String type) {
		
		temp.add(new Item(x, y, type));
		return temp;
	}
	
}
