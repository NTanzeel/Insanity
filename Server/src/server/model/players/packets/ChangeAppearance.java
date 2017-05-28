package server.model.players.packets;

import server.model.players.Player;
import server.model.players.PacketType;

/**
 * Change appearance
 **/

public class ChangeAppearance implements PacketType {

    @Override
    public void processPacket(final Player player, final int packetType, final int packetSize) {

    }
}