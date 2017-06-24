package server.model.players;

import server.model.players.packets.Chat;
import server.model.players.packets.SilentPacket;
import server.model.players.packets.Walking;

public class PacketHandler {

    private static PacketType packetId[] = new PacketType[256];

    static {
        SilentPacket u = new SilentPacket();

        for (int i = 0; i < packetId.length; i++) {
            packetId[i] = u;
        }

        packetId[4] = new Chat();
        Walking w = new Walking();
        packetId[98] = w;
        packetId[164] = w;
        packetId[248] = w;
    }

    public static void processPacket(Player c, int packetType, int packetSize) {
        if (packetType == -1) {
            return;
        }
        PacketType p = packetId[packetType];
        if (p != null) {
            try {
                // System.out.println("packet: " + packetType);
                p.processPacket(c, packetType, packetSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unhandled packet type: " + packetType + " - size: " + packetSize);
        }
    }

}
