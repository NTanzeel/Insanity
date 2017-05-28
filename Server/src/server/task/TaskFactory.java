package server.task;

import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.util.ScriptManager;

public class TaskFactory {

	public static Task getDelayedTask(final String callbackFunction,
                                      final Player player, final int actionX, final int actionY) {
		Task task = new Task(player, false) {
			@Override
			public void run() {
				synchronized (PlayerHandler.players) {
					ScriptManager.callFunc(callbackFunction, player, actionX,
							actionY);
				}
			}
		};
		return task;
	}

	public static Task getDelayedTask(final String callbackFunction,
                                      final Player player, final int actionID, final int actionX,
                                      final int actionY) {
		Task task = new Task(player, false) {
			@Override
			public void run() {
				synchronized (PlayerHandler.players) {
					ScriptManager.callFunc(callbackFunction, player, actionID,
							actionX, actionY);
				}
			}
		};
		return task;
	}

	public static Task getDelayedGlobalTask(final String callbackFunction,
			final int actionID, final int actionX, final int actionY) {
		Task task = new Task(null, true) {
			@Override
			public void run() {
				synchronized (PlayerHandler.players) {
					ScriptManager.callFunc(callbackFunction, actionID, actionX,
							actionY);
				}
			}
		};
		return task;
	}

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
