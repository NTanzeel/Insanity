package server.model.players.packets;

import server.model.players.PacketType;
import server.model.players.Player;

/**
 * Private messaging, friends etc
 **/
public class PrivateMessaging implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {

    }
}
