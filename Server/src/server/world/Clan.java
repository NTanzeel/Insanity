package server.world;

import server.model.players.Player;

/**
 * @author Sanity
 */

public class Clan {

	public Clan(Player c, String name) {
		this.owner = c.playerName;
		this.name = name;
	}

	public int[] members = new int[50];
	public String name;
	public String owner;
	public boolean lootshare;
}