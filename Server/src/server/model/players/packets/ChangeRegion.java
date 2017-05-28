package server.model.players.packets;

import server.model.players.Player;
import server.model.players.PacketType;

public class ChangeRegion implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		c.getPA().removeObjects();
		// Server.objectManager.loadObjects(c);
	}

}
