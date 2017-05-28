package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

public class Commands implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        String playerCommand = c.getInStream().readString();
    }
}