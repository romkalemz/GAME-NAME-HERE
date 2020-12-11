package time_lapse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;

public class Debug extends Entity{
	private int X;
	private int Y;
	public Debug(int x, int y, String string) {
		super(x,y);
		X = x;
		Y = y;
	}
	public void renderDebug(Graphics g, Game game) {
//		String indicator = " ";
//		
//		if(isOn)
//			indicator = "Debug Mode On";
//		else
//			indicator = " ";
		
		g.drawString("Debug Mode On", X, Y);
		Tile[][] tiles = game.map.getTileMap();
		for(int x = 0; x < game.map.getMapSizeX(); x++) {
			for(int y = 0; y < game.map.getMapSizeY(); y++) {
				if(tiles[x][y].getSolid() == true)
					g.drawGradientLine(tiles[x][y].getPrev().getX(), tiles[x][y].getPrev().getY(), 255, 255, 255, .2f,
						tiles[x][y].getX(), tiles[x][y].getY(), 255, 255, 255, 0.2f);
			}
		}
		
	}
}
