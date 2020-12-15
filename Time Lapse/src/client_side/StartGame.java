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
		// threading so that you can connect to server,
		// and run game in one main function
		Thread runClient = new Thread() {
			public void run() {
				Client player1 = new Client();
				
				try {
					player1.connect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		runClient.start();
		
		// start the game (render side)
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Time Lapse v0.1"));
			app.setDisplayMode(1200, 1400, false); // 2400, 1400 is great for level design. Normal is 1200, 800
			app.setVSync(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	

	}

}
