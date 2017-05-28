package server.model.players.packets;

import server.model.players.Player;
import server.model.players.PacketType;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {

    }
}
