package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EnemySpawner {
	public static ArrayList<Enemy> Spawn(ArrayList<Enemy> temp,float x, float y, int type) {
		// enemy 1: shooting
		// enemy 2: chasing
		// enemy 3: running away
		temp.add(new Enemy(x,y,type));
		return temp;
	}
	
	public static void renderEnemies(Graphics g,  StateBasedGame game) {
		MainGame tl = (MainGame)game;
		
		for(int i = 0; i < tl.enemy.size(); i++) {
			tl.enemy.get(i).render(g);
		}
	}
}
