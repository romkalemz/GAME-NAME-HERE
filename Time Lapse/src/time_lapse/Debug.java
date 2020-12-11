package time_lapse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.Vector;

public class Debug extends Entity{
	private int X;
	private int Y;
	public Debug(int x, int y, String string) {
		super(x,y);
		X = x;
		Y = y;
	}
	public void renderDebug(Graphics g, Game game) {
		Game tl = (Game)game;
		
		g.drawString("Debug Mode On", X, Y);
		
		// rendering the pathing for each enemy
		for(int i = 0; i < tl.enemy.size(); i++)
			renderPath(g, tl.enemy.get(i));
	}
	
	private void renderPath(Graphics g, Enemy e) {
		for(int i = 0; i < e.getPath().size()-1; i++) {
			Vector first = e.getPath().get(i);
			Vector next = e.getPath().get(i+1);
			g.drawGradientLine(first.getX(), first.getY(), new Color(255, 255, 255),
							   next.getX(), next.getY(), new Color(255, 255, 255));
		}
		
	}
}
