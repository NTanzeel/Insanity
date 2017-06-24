package server.model.players.packets;

import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerHandler;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        if (packetType == 248 || packetType == 164) {
            c.faceUpdate(0);
        }
        c.getPA().removeAllWindows();

        if (packetType == 248) {
            packetSize -= 14;
        }
        c.newWalkCmdSteps = (packetSize - 5) / 2;
        if (++c.newWalkCmdSteps > c.walkingQueueSize) {
            c.newWalkCmdSteps = 0;
            return;
        }

        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;

        int firstStepX = c.getInStream().readSignedWordBigEndianA() - c.getMapRegionX() * 8;
        for (int i = 1; i < c.newWalkCmdSteps; i++) {
            c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
            c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
        }

        int firstStepY = c.getInStream().readSignedWordBigEndian() - c.getMapRegionY() * 8;
        c.setNewWalkCmdIsRunning(c.getInStream().readSignedByteC() == 1);
        for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
            c.getNewWalkCmdX()[i1] += firstStepX;
            c.getNewWalkCmdY()[i1] += firstStepY;
        }
    }

}