package client_side;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import time_lapse.Game;

/*
 * This class starts the MainGame and attempts to connect to the server side of things
 */
public class StartGame {

	public static void main(String[] args) {
		
		// connect to the server
		Client player1 = new Client();
		
		try {
			player1.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// start the game (render side)
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Time Lapse v0.1"));
			app.setDisplayMode(1200, 800, false); // 2400, 1400 is great for level design. Normal is 1200, 800
			app.setVSync(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	

	}

}
