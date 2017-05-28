package server.model.players.packets;

import server.Server;
import server.model.players.Player;
import server.model.players.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        Server.objectManager.loadObjects(c);

        c.saveFile = true;

        if (c.skullTimer > 0) {
            c.isSkulled = true;
            c.getPA().requestUpdates();
        }

    }
}
