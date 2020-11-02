package time_lapse;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MainGame extends StateBasedGame {

	public MainGame(String title) {
		super(title);

		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MainGame("Time Lapse v0.1"));
			app.setDisplayMode(1200, 800, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
}
