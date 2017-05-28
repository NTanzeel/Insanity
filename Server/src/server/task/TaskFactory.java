package server.task;

import server.model.players.Player;

public class TaskFactory {


	protected static class Task implements Runnable {

		private Player player;
		private boolean global;

		public Task(Player player, boolean global) {
			this.player = player;
			this.global = global;
		}

		public void run() {
		}

		public Player getPlayer() {
			return player;
		}

		public boolean isGlobal() {
			return global;
		}

	}

}
