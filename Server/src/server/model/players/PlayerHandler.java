package server.model.players;

import server.Config;
import server.util.Stream;

public class PlayerHandler {

    public static final Player[] players = new Player[Config.MAX_PLAYERS];
    public static int playerCount = 0;
    private static String playersCurrentlyOn[] = new String[Config.MAX_PLAYERS];

    static {
        for (int i = 0; i < Config.MAX_PLAYERS; i++)
            players[i] = null;
    }

    private Stream updateBlock = new Stream(new byte[Config.BUFFER_SIZE]);

    public static boolean isPlayerOn(String playerName) {
        synchronized (PlayerHandler.players) {
            for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                if (playersCurrentlyOn[i] != null) {
                    if (playersCurrentlyOn[i].equalsIgnoreCase(playerName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean newPlayerClient(Player player1) {
        int slot = -1;
        for (int i = 1; i < Config.MAX_PLAYERS; i++) {
            if ((players[i] == null) || players[i].disconnected) {
                slot = i;
                break;
            }
        }
        if (slot == -1) return false;
        player1.handler = this;
        player1.playerId = slot;
        players[slot] = player1;
        players[slot].isActive = true;

        return true;
    }

    private void updatePlayerNames() {
        playerCount = 0;
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            if (players[i] != null) {
                playersCurrentlyOn[i] = players[i].playerName;
                playerCount++;
            } else {
                playersCurrentlyOn[i] = "";
            }
        }
    }

    public void process() {
        synchronized (PlayerHandler.players) {

            updatePlayerNames();

            for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive) continue;
                try {

                    if (players[i].disconnected && (System.currentTimeMillis() - players[i].logoutDelay > 10000 || players[i].properLogout)) {
                        Player o = PlayerHandler.players[i];
                        if (PlayerSave.saveGame(o)) {
                            System.out.println("Game saved for player " + players[i].playerName);
                        } else {
                            System.out.println("Could not save for " + players[i].playerName);
                        }
                        removePlayer(players[i]);
                        players[i] = null;
                        continue;
                    }

                    players[i].preProcessing();
                    boolean packetsProcessed;
                    do {
                        packetsProcessed = !players[i].processQueuedPackets();
                    } while (!packetsProcessed);

                    players[i].process();
                    players[i].postProcessing();
                    players[i].getNextPlayerMovement();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive) continue;
                try {
                    if (players[i].disconnected && (System.currentTimeMillis() - players[i].logoutDelay > 10000 || players[i].properLogout)) {

                        Player o1 = PlayerHandler.players[i];
                        if (PlayerSave.saveGame(o1)) {
                            System.out.println("Game saved for player " + players[i].playerName);
                        } else {
                            System.out.println("Could not save for " + players[i].playerName);
                        }
                        removePlayer(players[i]);
                        players[i] = null;
                    } else {
                        if (!players[i].initialized) {
                            players[i].initialize();
                            players[i].initialized = true;
                        } else {
                            players[i].update();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive) continue;
                try {
                    players[i].clearUpdateFlags();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void updatePlayer(Player plr, Stream str) {
        updateBlock.currentOffset = 0;
        plr.updateThisPlayerMovement(str);
        boolean saveChatTextUpdate = plr.isChatTextUpdateRequired();
        plr.setChatTextUpdateRequired(false);
        plr.appendPlayerUpdateBlock(updateBlock);
        plr.setChatTextUpdateRequired(saveChatTextUpdate);
        str.writeBits(8, plr.playerListSize);
        int size = plr.playerListSize;
        if (size > 255) size = 255;
        plr.playerListSize = 0;
        for (int i = 0; i < size; i++) {
            if (!plr.didTeleport && !plr.playerList[i].didTeleport && plr.withinDistance(plr.playerList[i])) {
                plr.playerList[i].updatePlayerMovement(str);
                plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
                plr.playerList[plr.playerListSize++] = plr.playerList[i];
            } else {
                int id = plr.playerList[i].playerId;
                plr.playerInListBitmap[id >> 3] &= ~(1 << (id & 7));
                str.writeBits(1, 1);
                str.writeBits(2, 3);
            }
        }

        int j = 0;
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            if (plr.playerListSize >= 254) break;
            if (updateBlock.currentOffset + str.currentOffset >= 4900) break;
            if (players[i] == null || !players[i].isActive || players[i] == plr) continue;
            int id = players[i].playerId;
            if ((plr.playerInListBitmap[id >> 3] & (1 << (id & 7))) != 0) continue;
            if (j >= 10) break;
            if (!plr.withinDistance(players[i])) continue;
            plr.addNewPlayer(players[i], str, updateBlock);
            j++;
        }

        if (updateBlock.currentOffset > 0) {
            str.writeBits(11, 2047);
            str.finishBitAccess();
            str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
        } else str.finishBitAccess();

        str.endFrameVarSizeWord();
    }

    private void removePlayer(Player plr) {
        plr.destruct();
    }

}
