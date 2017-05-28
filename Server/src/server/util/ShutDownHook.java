package server.util;

import server.model.players.Player;
import server.model.players.PlayerHandler;

public class ShutDownHook extends Thread {

	@Override
	public void run() {
		System.out.println("Shutdown thread run.");
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Player c = (Player) PlayerHandler.players[j];
				server.model.players.PlayerSave.saveGame(c);
			}
		}
		System.out.println("Shutting down...");
	}

}