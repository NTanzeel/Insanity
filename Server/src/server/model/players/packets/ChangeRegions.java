package server.model.players.packets;

import server.model.players.PacketType;
import server.model.players.Player;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        c.saveFile = true;

        if (c.skullTimer > 0) {
            c.isSkulled = true;
            c.getPA().requestUpdates();
        }

    }
}
