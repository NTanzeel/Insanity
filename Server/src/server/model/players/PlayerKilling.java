package server.model.players;

/**
 * Handles adding and removing hosts to the players array list.
 */
public class PlayerKilling {

	/**
	 * Adds the host of the killed player.
	 * 
	 * @param player
	 *            Player that saves the host.
	 * @param host
	 *            Host address of the killed player.
	 * @return True if the host is added to the players array.
	 */

	public static boolean addHostToList(Player player, String host) {
		if (player != null) {
			return player.lastKilledPlayers.add(host);
		}
		return false;
	}

	/**
	 * Checks if the host is already on the players array.
	 * 
	 * @param player
	 *            Player that is adding the killed players host.
	 * @param host
	 *            Host address of the killed player.
	 * @return True if the host is on the players array.
	 */

	public static boolean hostOnList(Player player, String host) {
		if (player != null) {
			if (player.lastKilledPlayers.lastIndexOf(host) >= KILL_WAIT_MAX) {
				removeHostFromList(player, host);
				return false;
			}
			return player.lastKilledPlayers.contains(host);
		}
		return false;
	}

	/**
	 * Removes the host from the players array.
	 * 
	 * @param player
	 *            Player that is removing the host.
	 * @param host
	 *            Host that is being removed.
	 * @return True if host is successfully removed.
	 */

	public static boolean removeHostFromList(Player player, String host) {
		if (player != null) {
			return player.lastKilledPlayers.remove(host);
		}
		return false;
	}

	/*
	 * Amount of kills you have to wait before the host is deleted.
	 */

	public static final int KILL_WAIT_MAX = 3;

}