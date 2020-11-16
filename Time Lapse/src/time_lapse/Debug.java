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
	public void renderDebug(Graphics g, boolean isOn) {
		String indicator = " ";
		
		if(isOn)
			indicator = "Debug Mode On";
		else
			indicator = " ";
		
		g.drawString(indicator, X, Y);
	}
}
