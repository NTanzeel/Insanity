package server.model.players;

import org.apache.mina.common.IoSession;
import server.Config;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.Misc;
import server.util.Stream;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

public class Player {

    public static final int PACKET_SIZES[] = {0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
            0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
            0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
            0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
            2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
            0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
            8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
            6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
            0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
            0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
            0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
            0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
            1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
            0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
            0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
            0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
            0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
            0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
            0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
            2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
            4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
            0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
            1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
            0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
            0, 0, 6, 6, 0, 0, 0 // 250
    };
    private static final int maxPlayerListSize = Config.MAX_PLAYERS;
    private static Stream playerProps;

    static {
        playerProps = new Stream(new byte[100]);
    }

    public final int walkingQueueSize = 50;

    final int[] PRAYER = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    final int[] PRAYER_GLOW = {83, 84, 85, 601, 602, 86, 87, 88, 89, 90, 91, 603, 604, 92, 93, 94, 95, 96, 97, 605, 606, 98, 99, 100, 607, 608};
    public boolean disconnected = false;
    public boolean isActive = false;
    public boolean saveCharacter = false;
    public boolean addStarter = false;


    public int autoRet = 0;


    public String properName;

    public long logoutDelay;


    public boolean[] prayerActive = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    public int fightMode;

    public boolean isMoving;

    public boolean forcedChatUpdateRequired;
    public int animationRequest = -1, animationWaitCycles;

    public boolean isRunning2 = true;

    public int combatLevel;
    public boolean saveFile = false;
    public int playerAppearance[] = new int[13];
    public int apset;
    public int actionID;

    public int playerId = -1;
    public String playerName = null;
    public String playerName2 = null;
    public String playerPass = null;
    public int playerRights;

    public PlayerHandler handler = null;

    public int playerStandIndex = 0x328;
    public int playerTurnIndex = 0x337;
    public int playerWalkIndex = 0x333;
    public int playerTurn180Index = 0x334;
    public int playerTurn90CWIndex = 0x335;
    public int playerTurn90CCWIndex = 0x336;
    public int playerRunIndex = 0x338;
    public int playerHat = 0;
    public int playerCape = 1;
    public int playerAmulet = 2;
    public int playerWeapon = 3;
    public int playerChest = 4;
    public int playerShield = 5;
    public int playerLegs = 7;
    public int playerHands = 9;
    public int playerFeet = 10;
    public int playerRing = 12;
    public int playerArrows = 13;
    public int playerAttack = 0;
    public int playerDefence = 1;
    public int playerStrength = 2;
    public int playerHitpoints = 3;

    public int playerRanged = 4;
    public int playerPrayer = 5;
    public int playerMagic = 6;
    public int playerCooking = 7;
    public int playerWoodcutting = 8;
    public int playerFletching = 9;
    public int playerFishing = 10;
    public int playerFiremaking = 11;
    public int playerCrafting = 12;
    public int playerSmithing = 13;
    public int playerMining = 14;
    public int playerHerblore = 15;
    public int playerAgility = 16;
    public int playerThieving = 17;
    public int playerSlayer = 18;
    public int playerFarming = 19;
    public int playerRunecrafting = 20;
    public int[] playerEquipment = new int[14];
    public int[] playerEquipmentN = new int[14];
    public int[] playerLevel = new int[25];
    public int[] playerXP = new int[25];
    public Player playerList[] = new Player[maxPlayerListSize];
    public int playerListSize = 0;
    public byte playerInListBitmap[] = new byte[(Config.MAX_PLAYERS + 7) >> 3];

    public int mapRegionX, mapRegionY;
    public int absX, absY;
    public int currentX, currentY;
    public int heightLevel;

    public boolean updateRequired = true;
    public int walkingQueueX[] = new int[walkingQueueSize], walkingQueueY[] = new int[walkingQueueSize];
    public int wQueueReadPtr = 0;
    public int wQueueWritePtr = 0;
    public boolean isRunning = true;
    public int teleportToX = -1, teleportToY = -1;
    public boolean didTeleport = false;
    public boolean mapRegionDidChange = false;
    public int dir1 = -1, dir2 = -1;

    public int DirectionCount = 0;
    public boolean isDead = false;
    public String forcedText = "null";
    /**
     * Graphics
     **/

    public int mask100var1 = 0;
    public int mask100var2 = 0;
    public int face = -1;
    public int FocusPointX = -1, FocusPointY = -1;
    public int newWalkCmdSteps = 0;
    public byte buffer[] = null;
    public Stream inStream = null, outStream = null;
    public int timeOutCounter = 0;
    public int packetSize = 0, packetType = -1;
    protected int hitDiff2;
    protected boolean hitUpdateRequired2;
    protected boolean mask100update = false;
    /**
     * Face Update
     **/

    protected boolean faceUpdateRequired = false;
    protected int travelBackX[] = new int[walkingQueueSize];
    protected int travelBackY[] = new int[walkingQueueSize];
    protected int numTravelBackSteps = 0;


    boolean initialized = false;
    boolean newPlayer = false;
    boolean properLogout = false;

    int newLocation;

    private byte poisonMask = 0;
    private boolean appearanceUpdateRequired = true;
    private int hitDiff = 0;
    private boolean hitUpdateRequired = false;
    private boolean chatTextUpdateRequired = false;
    private byte chatText[] = new byte[4096];
    private byte chatTextSize = 0;
    private int chatTextColor = 0;
    private int chatTextEffects = 0;
    private int newWalkCmdX[] = new int[walkingQueueSize];
    private int newWalkCmdY[] = new int[walkingQueueSize];
    private boolean newWalkCmdIsRunning = false;
    private IoSession session;
    private PlayerAssistant playerAssistant = new PlayerAssistant(this);
    private Queue<Packet> queuedPackets = new LinkedList<Packet>();
    private Future<?> currentTask;

    public Player(IoSession s, int _playerId) {
        playerId = _playerId;
        playerRights = 0;

        for (int i = 0; i < playerLevel.length; i++) {
            if (i == 3) {
                playerLevel[i] = 10;
            } else {
                playerLevel[i] = 1;
            }
        }

        for (int i = 0; i < playerXP.length; i++) {
            if (i == 3) {
                playerXP[i] = 1300;
            } else {
                playerXP[i] = 0;
            }
        }

        playerAppearance[0] = 0; // gender
        playerAppearance[1] = 7; // head
        playerAppearance[2] = 25;// Torso
        playerAppearance[3] = 29; // arms
        playerAppearance[4] = 35; // hands
        playerAppearance[5] = 39; // legs
        playerAppearance[6] = 44; // feet
        playerAppearance[7] = 14; // beard
        playerAppearance[8] = 7; // hair colour
        playerAppearance[9] = 8; // torso colour
        playerAppearance[10] = 9; // legs colour
        playerAppearance[11] = 5; // feet colour
        playerAppearance[12] = 0; // skin colour

        apset = 0;
        actionID = 0;

        playerEquipment[playerHat] = -1;
        playerEquipment[playerCape] = -1;
        playerEquipment[playerAmulet] = -1;
        playerEquipment[playerChest] = -1;
        playerEquipment[playerShield] = -1;
        playerEquipment[playerLegs] = -1;
        playerEquipment[playerHands] = -1;
        playerEquipment[playerFeet] = -1;
        playerEquipment[playerRing] = -1;
        playerEquipment[playerArrows] = -1;
        playerEquipment[playerWeapon] = -1;

        heightLevel = 0;

        teleportToX = Config.START_LOCATION_X;
        teleportToY = Config.START_LOCATION_Y;

        absX = absY = -1;
        mapRegionX = mapRegionY = -1;
        currentX = currentY = 0;
        resetWalkingQueue();

        this.session = s;
        synchronized (this) {
            outStream = new Stream(new byte[Config.BUFFER_SIZE]);
            outStream.currentOffset = 0;
        }
        inStream = new Stream(new byte[Config.BUFFER_SIZE]);
        inStream.currentOffset = 0;
        buffer = new byte[Config.BUFFER_SIZE];
    }

    public void println_debug(String str) {
        System.out.println("[player-" + playerId + "]: " + str);
    }

    public boolean withinDistance(Player otherPlr) {
        if (heightLevel != otherPlr.heightLevel) return false;
        int deltaX = otherPlr.absX - absX, deltaY = otherPlr.absY - absY;
        return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
    }


    public int distanceToPoint(int pointX, int pointY) {
        return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
    }

    public void resetWalkingQueue() {
        wQueueReadPtr = wQueueWritePtr = 0;

        for (int i = 0; i < walkingQueueSize; i++) {
            walkingQueueX[i] = currentX;
            walkingQueueY[i] = currentY;
        }
    }

    public void addToWalkingQueue(int x, int y) {
        // if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
        int next = (wQueueWritePtr + 1) % walkingQueueSize;
        if (next == wQueueWritePtr) return;
        walkingQueueX[wQueueWritePtr] = x;
        walkingQueueY[wQueueWritePtr] = y;
        wQueueWritePtr = next;
        // }
    }

    public int getNextWalkingDirection() {
        if (wQueueReadPtr == wQueueWritePtr) return -1;
        int dir;
        do {
            dir = Misc.direction(currentX, currentY, walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
            if (dir == -1) {
                wQueueReadPtr = (wQueueReadPtr + 1) % walkingQueueSize;
            } else if ((dir & 1) != 0) {
                println_debug("Invalid waypoint in walking queue!");
                resetWalkingQueue();
                return -1;
            }
        } while ((dir == -1) && (wQueueReadPtr != wQueueWritePtr));
        if (dir == -1) return -1;
        dir >>= 1;
        currentX += Misc.directionDeltaX[dir];
        currentY += Misc.directionDeltaY[dir];
        absX += Misc.directionDeltaX[dir];
        absY += Misc.directionDeltaY[dir];
        return dir;
    }

    public synchronized void getNextPlayerMovement() {
        mapRegionDidChange = false;
        didTeleport = false;
        dir1 = dir2 = -1;

        if (teleportToX != -1 && teleportToY != -1) {
            mapRegionDidChange = true;
            if (mapRegionX != -1 && mapRegionY != -1) {
                int relX = teleportToX - mapRegionX * 8, relY = teleportToY - mapRegionY * 8;
                if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8 && relY < 11 * 8) mapRegionDidChange = false;
            }
            if (mapRegionDidChange) {
                mapRegionX = (teleportToX >> 3) - 6;
                mapRegionY = (teleportToY >> 3) - 6;
            }
            currentX = teleportToX - 8 * mapRegionX;
            currentY = teleportToY - 8 * mapRegionY;
            absX = teleportToX;
            absY = teleportToY;
            resetWalkingQueue();

            teleportToX = teleportToY = -1;
            didTeleport = true;
        } else {
            dir1 = getNextWalkingDirection();
            if (dir1 == -1) return;
            if (isRunning) {
                dir2 = getNextWalkingDirection();
            }
            // c.sendMessage("Cycle Ended");
            int deltaX = 0, deltaY = 0;
            if (currentX < 2 * 8) {
                deltaX = 4 * 8;
                mapRegionX -= 4;
                mapRegionDidChange = true;
            } else if (currentX >= 11 * 8) {
                deltaX = -4 * 8;
                mapRegionX += 4;
                mapRegionDidChange = true;
            }
            if (currentY < 2 * 8) {
                deltaY = 4 * 8;
                mapRegionY -= 4;
                mapRegionDidChange = true;
            } else if (currentY >= 11 * 8) {
                deltaY = -4 * 8;
                mapRegionY += 4;
                mapRegionDidChange = true;
            }

            if (mapRegionDidChange/*
                                 * && VirtualWorld.I(heightLevel, currentX,
								 * currentY, currentX + deltaX, currentY +
								 * deltaY, 0)
								 */) {
                currentX += deltaX;
                currentY += deltaY;
                for (int i = 0; i < walkingQueueSize; i++) {
                    walkingQueueX[i] += deltaX;
                    walkingQueueY[i] += deltaY;
                }
            }
            // CoordAssistant.processCoords(this);

        }
    }

    public void updateThisPlayerMovement(Stream str) {
        if (mapRegionDidChange) {
            str.createFrame(73);
            str.writeWordA(mapRegionX + 6);
            str.writeWord(mapRegionY + 6);
        }

        if (didTeleport) {
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            str.writeBits(1, 1);
            str.writeBits(2, 3);
            str.writeBits(2, heightLevel);
            str.writeBits(1, 1);
            str.writeBits(1, (updateRequired) ? 1 : 0);
            str.writeBits(7, currentY);
            str.writeBits(7, currentX);
            return;
        }

        if (dir1 == -1) {
            // don't have to update the character position, because we're
            // just standing
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            isMoving = false;
            if (updateRequired) {
                // tell client there's an update block appended at the end
                str.writeBits(1, 1);
                str.writeBits(2, 0);
            } else {
                str.writeBits(1, 0);
            }
            if (DirectionCount < 50) {
                DirectionCount++;
            }
        } else {
            DirectionCount = 0;
            str.createFrameVarSizeWord(81);
            str.initBitAccess();
            str.writeBits(1, 1);

            if (dir2 == -1) {
                isMoving = true;
                str.writeBits(2, 1);
                str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
                if (updateRequired) str.writeBits(1, 1);
                else str.writeBits(1, 0);
            } else {
                isMoving = true;
                str.writeBits(2, 2);
                str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
                str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
                if (updateRequired) str.writeBits(1, 1);
                else str.writeBits(1, 0);
            }
        }
    }

    public void updatePlayerMovement(Stream str) {
        if (dir1 == -1) {
            if (updateRequired || isChatTextUpdateRequired()) {

                str.writeBits(1, 1);
                str.writeBits(2, 0);
            } else str.writeBits(1, 0);
        } else if (dir2 == -1) {

            str.writeBits(1, 1);
            str.writeBits(2, 1);
            str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
            str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1 : 0);
        } else {

            str.writeBits(1, 1);
            str.writeBits(2, 2);
            str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
            str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
            str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1 : 0);
        }
    }

    public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
        int id = plr.playerId;
        playerInListBitmap[id >> 3] |= 1 << (id & 7);
        playerList[playerListSize++] = plr;
        str.writeBits(11, id);
        str.writeBits(1, 1);
        boolean savedFlag = plr.isAppearanceUpdateRequired();
        boolean savedUpdateRequired = plr.updateRequired;
        plr.setAppearanceUpdateRequired(true);
        plr.updateRequired = true;
        plr.appendPlayerUpdateBlock(updateBlock);
        plr.setAppearanceUpdateRequired(savedFlag);
        plr.updateRequired = savedUpdateRequired;
        str.writeBits(1, 1);
        int z = plr.absY - absY;
        if (z < 0) z += 32;
        str.writeBits(5, z);
        z = plr.absX - absX;
        if (z < 0) z += 32;
        str.writeBits(5, z);
    }

    protected void appendPlayerAppearance(Stream str) {
        playerProps.currentOffset = 0;

        playerProps.writeByte(playerAppearance[0]);

        playerProps.writeByte(-1); // Head Icon
        playerProps.writeByte(-1); // Head Icon PK.

        if (playerEquipment[playerHat] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerHat]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerCape] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerCape]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerAmulet] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerWeapon] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
        } else {
            playerProps.writeByte(0);
        }

        if (playerEquipment[playerChest] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerChest]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[2]);
        }

        if (playerEquipment[playerShield] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerShield]);
        } else {
            playerProps.writeByte(0);
        }

//        if (!Item.isFullBody(playerEquipment[playerChest])) {
        playerProps.writeWord(0x100 + playerAppearance[3]);
//        } else {
//            playerProps.writeByte(0);
//        }

        if (playerEquipment[playerLegs] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[5]);
        }

//        if (!Item.isFullHelm(playerEquipment[playerHat]) && !Item.isFullMask(playerEquipment[playerHat])) {
        playerProps.writeWord(0x100 + playerAppearance[1]);
//        } else {
//            playerProps.writeByte(0);
//        }

        if (playerEquipment[playerHands] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerHands]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[4]);
        }

        if (playerEquipment[playerFeet] > 1) {
            playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
        } else {
            playerProps.writeWord(0x100 + playerAppearance[6]);
        }

//        if (playerAppearance[0] != 1 && !Item.isFullMask(playerEquipment[playerHat])) {
        playerProps.writeWord(0x100 + playerAppearance[7]);
//        } else {
//            playerProps.writeByte(0);
//        }

        playerProps.writeByte(playerAppearance[8]);
        playerProps.writeByte(playerAppearance[9]);
        playerProps.writeByte(playerAppearance[10]);
        playerProps.writeByte(playerAppearance[11]);
        playerProps.writeByte(playerAppearance[12]);
        playerProps.writeWord(playerStandIndex); // standAnimIndex
        playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
        playerProps.writeWord(playerWalkIndex); // walkAnimIndex
        playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
        playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
        playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
        playerProps.writeWord(playerRunIndex); // runAnimIndex
        playerProps.writeQWord(Misc.playerNameToInt64(playerName));
        combatLevel = calculateCombatLevel();
        playerProps.writeByte(combatLevel); // combat level
        playerProps.writeWord(0);
        str.writeByteC(playerProps.currentOffset);
        str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
    }

    public int calculateCombatLevel() {
        int j = getLevelForXP(playerXP[playerAttack]);
        int k = getLevelForXP(playerXP[playerDefence]);
        int l = getLevelForXP(playerXP[playerStrength]);
        int i1 = getLevelForXP(playerXP[playerHitpoints]);
        int j1 = getLevelForXP(playerXP[playerPrayer]);
        int k1 = getLevelForXP(playerXP[playerRanged]);
        int l1 = getLevelForXP(playerXP[playerMagic]);
        int combatLevel = (int) (((k + i1) + Math.floor(j1 / 2)) * 0.25D) + 1;
        double d = (j + l) * 0.32500000000000001D;
        double d1 = Math.floor(k1 * 1.5D) * 0.32500000000000001D;
        double d2 = Math.floor(l1 * 1.5D) * 0.32500000000000001D;
        if (d >= d1 && d >= d2) {
            combatLevel += d;
        } else if (d1 >= d && d1 >= d2) {
            combatLevel += d1;
        } else if (d2 >= d && d2 >= d1) {
            combatLevel += d2;
        }
        return combatLevel;
    }

    public int getLevelForXP(int exp) {
        if (exp > 13034430) {
            return 99;
        } else {
            int points = 0;
            for (int lvl = 1; lvl <= 99; ++lvl) {
                points = (int) ((double) points + Math.floor((double) lvl + 300.0D * Math.pow(2.0D, (double) lvl / 7.0D)));
                int var5 = (int) Math.floor((double) (points / 4));
                if (var5 >= exp) {
                    return lvl;
                }
            }

            return 99;
        }
    }

    protected void appendPlayerChatText(Stream str) {
        str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8) + (getChatTextEffects() & 0xFF));
        str.writeByte(playerRights);
        str.writeByteC(getChatTextSize());
        str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
    }

    public void appendForcedChat(Stream str) {
        str.writeString(forcedText);
    }

    public void appendMask100Update(Stream str) {
        str.writeWordBigEndian(mask100var1);
        str.writeDWord(mask100var2);
    }


    public void appendAnimationRequest(Stream str) {
        str.writeWordBigEndian((animationRequest == -1) ? 65535 : animationRequest);
        str.writeByteC(animationWaitCycles);
    }

    public void faceUpdate(int index) {
        face = index;
        faceUpdateRequired = true;
        updateRequired = true;
    }

    public void appendFaceUpdate(Stream str) {
        str.writeWordBigEndian(face);
    }


    private void appendSetFocusDestination(Stream str) {
        str.writeWordBigEndianA(FocusPointX);
        str.writeWordBigEndian(FocusPointY);
    }

    /**
     * Hit Update
     **/

    protected void appendHitUpdate(Stream str) {
        str.writeByte(getHitDiff()); // What the perseon got 'hit' for
        if (poisonMask == 1) {
            str.writeByteA(2);
        } else if (getHitDiff() > 0) {
            str.writeByteA(1); // 0: red hitting - 1: blue hitting
        } else {
            str.writeByteA(0); // 0: red hitting - 1: blue hitting
        }
        if (playerLevel[3] <= 0) {
            playerLevel[3] = 0;
            isDead = true;
        }
        str.writeByteC(playerLevel[3]); // Their current hp, for HP bar
        str.writeByte(getLevelForXP(playerXP[3]));
    }

    protected void appendHitUpdate2(Stream str) {
        str.writeByte(hitDiff2); // What the perseon got 'hit' for
        if (poisonMask == 2) {
            str.writeByteS(2);
            poisonMask = -1;
        } else if (hitDiff2 > 0) {
            str.writeByteS(1); // 0: red hitting - 1: blue hitting
        } else {
            str.writeByteS(0); // 0: red hitting - 1: blue hitting
        }
        if (playerLevel[3] <= 0) {
            playerLevel[3] = 0;
            isDead = true;
        }
        str.writeByte(playerLevel[3]); // Their current hp, for HP bar
        str.writeByteC(getLevelForXP(playerXP[3])); // Their max hp, for HP
    }

    public void appendPlayerUpdateBlock(Stream str) {
        if (!updateRequired && !isChatTextUpdateRequired()) return; // nothing required
        int updateMask = 0;
        if (mask100update) {
            updateMask |= 0x100;
        }
        if (animationRequest != -1) {
            updateMask |= 8;
        }
        if (forcedChatUpdateRequired) {
            updateMask |= 4;
        }
        if (isChatTextUpdateRequired()) {
            updateMask |= 0x80;
        }
        if (isAppearanceUpdateRequired()) {
            updateMask |= 0x10;
        }
        if (faceUpdateRequired) {
            updateMask |= 1;
        }
        if (FocusPointX != -1) {
            updateMask |= 2;
        }
        if (isHitUpdateRequired()) {
            updateMask |= 0x20;
        }

        if (hitUpdateRequired2) {
            updateMask |= 0x200;
        }

        if (updateMask >= 0x100) {
            updateMask |= 0x40;
            str.writeByte(updateMask & 0xFF);
            str.writeByte(updateMask >> 8);
        } else {
            str.writeByte(updateMask);
        }

        // now writing the various update blocks itself - note that their
        // order crucial
        if (mask100update) {
            appendMask100Update(str);
        }
        if (animationRequest != -1) {
            appendAnimationRequest(str);
        }
        if (forcedChatUpdateRequired) {
            appendForcedChat(str);
        }
        if (isChatTextUpdateRequired()) {
            appendPlayerChatText(str);
        }
        if (faceUpdateRequired) {
            appendFaceUpdate(str);
        }
        if (isAppearanceUpdateRequired()) {
            appendPlayerAppearance(str);
        }
        if (FocusPointX != -1) {
            appendSetFocusDestination(str);
        }
        if (isHitUpdateRequired()) {
            appendHitUpdate(str);
        }
        if (hitUpdateRequired2) {
            appendHitUpdate2(str);
        }
    }

    public void clearUpdateFlags() {
        updateRequired = false;
        setChatTextUpdateRequired(false);
        setAppearanceUpdateRequired(false);
        setHitUpdateRequired(false);
        hitUpdateRequired2 = false;
        forcedChatUpdateRequired = false;
        mask100update = false;
        animationRequest = -1;
        FocusPointX = -1;
        FocusPointY = -1;
        poisonMask = -1;
        faceUpdateRequired = false;
        face = 65535;
    }

    public void preProcessing() {
        newWalkCmdSteps = 0;
    }

    public void postProcessing() {
        if (this.newWalkCmdSteps > 0) {
            int firstX = this.getNewWalkCmdX()[0];
            int firstY = this.getNewWalkCmdY()[0];
            boolean found = false;
            this.numTravelBackSteps = 0;
            int ptr = this.wQueueReadPtr;
            int dir = Misc.direction(this.currentX, this.currentY, firstX, firstY);
            if (dir != -1 && (dir & 1) != 0) {
                do {
                    int var13 = dir;
                    --ptr;
                    if (ptr < 0) {
                        ptr = 49;
                    }

                    this.travelBackX[this.numTravelBackSteps] = this.walkingQueueX[ptr];
                    this.travelBackY[this.numTravelBackSteps++] = this.walkingQueueY[ptr];
                    dir = Misc.direction(this.walkingQueueX[ptr], this.walkingQueueY[ptr], firstX, firstY);
                    if (var13 != dir) {
                        found = true;
                        break;
                    }
                } while (ptr != this.wQueueWritePtr);
            } else {
                found = true;
            }

            if (found) {
                this.wQueueWritePtr = this.wQueueReadPtr;
                this.addToWalkingQueue(this.currentX, this.currentY);
                int i;
                if (dir != -1 && (dir & 1) != 0) {
                    for (i = 0; i < this.numTravelBackSteps - 1; ++i) {
                        this.addToWalkingQueue(this.travelBackX[i], this.travelBackY[i]);
                    }

                    i = this.travelBackX[this.numTravelBackSteps - 1];
                    int wayPointY2 = this.travelBackY[this.numTravelBackSteps - 1];
                    int wayPointX1;
                    int wayPointY1;
                    if (this.numTravelBackSteps == 1) {
                        wayPointX1 = this.currentX;
                        wayPointY1 = this.currentY;
                    } else {
                        wayPointX1 = this.travelBackX[this.numTravelBackSteps - 2];
                        wayPointY1 = this.travelBackY[this.numTravelBackSteps - 2];
                    }

                    dir = Misc.direction(wayPointX1, wayPointY1, i, wayPointY2);
                    if (dir != -1 && (dir & 1) == 0) {
                        dir >>= 1;
                        found = false;
                        int x = wayPointX1;
                        int y = wayPointY1;

                        while (x != i || y != wayPointY2) {
                            x += Misc.directionDeltaX[dir];
                            y += Misc.directionDeltaY[dir];
                            if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            this.addToWalkingQueue(wayPointX1, wayPointY1);
                        }
                    }
                } else {
                    for (i = 0; i < this.numTravelBackSteps; ++i) {
                        this.addToWalkingQueue(this.travelBackX[i], this.travelBackY[i]);
                    }
                }

                for (i = 0; i < this.newWalkCmdSteps; ++i) {
                    this.addToWalkingQueue(this.getNewWalkCmdX()[i], this.getNewWalkCmdY()[i]);
                }
            }

            this.isRunning = this.isNewWalkCmdIsRunning() || this.isRunning2;
        }
    }

    public int getMapRegionX() {
        return mapRegionX;
    }

    public int getMapRegionY() {
        return mapRegionY;
    }

    public int getX() {
        return absX;
    }

    public int getY() {
        return absY;
    }

    public int getId() {
        return playerId;
    }


    public int getHitDiff() {
        return hitDiff;
    }

    public boolean isHitUpdateRequired() {
        return hitUpdateRequired;
    }


    public void setHitUpdateRequired(boolean hitUpdateRequired) {
        this.hitUpdateRequired = hitUpdateRequired;
    }


    public boolean isAppearanceUpdateRequired() {
        return appearanceUpdateRequired;
    }

    public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
        this.appearanceUpdateRequired = appearanceUpdateRequired;
    }

    public int getChatTextEffects() {
        return chatTextEffects;
    }

    public void setChatTextEffects(int chatTextEffects) {
        this.chatTextEffects = chatTextEffects;
    }

    public byte getChatTextSize() {
        return chatTextSize;
    }

    public void setChatTextSize(byte chatTextSize) {
        this.chatTextSize = chatTextSize;
    }

    public boolean isChatTextUpdateRequired() {
        return chatTextUpdateRequired;
    }

    public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
        this.chatTextUpdateRequired = chatTextUpdateRequired;
    }

    public byte[] getChatText() {
        return chatText;
    }


    public int getChatTextColor() {
        return chatTextColor;
    }

    public void setChatTextColor(int chatTextColor) {
        this.chatTextColor = chatTextColor;
    }

    public int[] getNewWalkCmdX() {
        return newWalkCmdX;
    }


    public int[] getNewWalkCmdY() {
        return newWalkCmdY;
    }


    public boolean isNewWalkCmdIsRunning() {
        return newWalkCmdIsRunning;
    }

    public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
        this.newWalkCmdIsRunning = newWalkCmdIsRunning;
    }


    public void flushOutStream() {
        if (disconnected || outStream.currentOffset == 0) return;
        synchronized (this) {
            StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
            byte[] temp = new byte[outStream.currentOffset];
            System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
            out.addBytes(temp);
            session.write(out.toPacket());
            outStream.currentOffset = 0;
        }
    }


    public void destruct() {
        if (session == null) return;
        Misc.println("[DEREGISTERED]: " + playerName + "");
        disconnected = true;
        session.close();
        session = null;
        inStream = null;
        outStream = null;
        isActive = false;
        buffer = null;
        playerListSize = 0;
        for (int i = 0; i < maxPlayerListSize; i++)
            playerList[i] = null;
        absX = absY = -1;
        mapRegionX = mapRegionY = -1;
        currentX = currentY = 0;
        resetWalkingQueue();
    }

    public void sendMessage(String s) {
        synchronized (this) {
            if (getOutStream() != null) {
                outStream.createFrameVarSize(253);
                outStream.writeString(s);
                outStream.endFrameVarSize();
            }
        }
    }

    public void setSidebarInterface(int menuId, int form) {
        synchronized (this) {
            if (getOutStream() != null) {
                outStream.createFrame(71);
                outStream.writeWord(form);
                outStream.writeByteA(menuId);
            }
        }
    }

    public void initialize() {
        synchronized (this) {
            outStream.createFrame(249);
            outStream.writeByteA(1); // 1 for members, zero for free
            outStream.writeWordBigEndianA(playerId);

            for (int i = 0; i < 25; i++) {
                getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
                getPA().refreshSkill(i);
            }

            for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
                prayerActive[p] = false;
                getPA().sendFrame36(PRAYER_GLOW[p], 0);
            }

            getPA().sendCrashFrame();

//            getPA().handleWeaponStyle();
            getPA().handleLoginText();

            getPA().sendFrame36(108, 0);// resets autocast button
            getPA().sendFrame36(172, 1);

            getPA().sendFrame107(); // reset screen

            getPA().setChatOptions(0, 0, 0); // reset private messaging options

            setSidebarInterface(0, 2423);
            setSidebarInterface(1, 3917);
            setSidebarInterface(2, 638);
            setSidebarInterface(3, 3213);
            setSidebarInterface(4, 1644);
            setSidebarInterface(5, 5608);
            setSidebarInterface(6, 1151); // modern
            setSidebarInterface(7, 18128);
            setSidebarInterface(8, 5065);
            setSidebarInterface(9, 5715);
            setSidebarInterface(10, 2449);
            setSidebarInterface(11, 904); // wrench tab
            setSidebarInterface(12, 147); // run tab
            setSidebarInterface(13, -1);

            sendMessage("@red@Welcome to " + Config.SERVER_NAME);

            getPA().showOption(4, 0, "Trade With", 3);
            getPA().showOption(5, 0, "Follow", 4);

            Misc.println("[REGISTERED]: " + playerName + "");

            handler.updatePlayer(this, outStream);
            flushOutStream();

            getPA().clearClanChat();

            if (autoRet == 1) getPA().sendFrame36(172, 1);

            else getPA().sendFrame36(172, 0);
        }
    }

    public void update() {
        synchronized (this) {
            handler.updatePlayer(this, outStream);
            flushOutStream();
        }
    }


    public void process() {
        getPA().sendFrame99(0);
        getPA().walkableInterface(-1);
        getPA().showOption(3, 0, "Null", 1);
    }


    public synchronized Stream getInStream() {
        return inStream;
    }


    public synchronized Stream getOutStream() {
        return outStream;
    }

    public PlayerAssistant getPA() {
        return playerAssistant;
    }


    /**
     * End of Skill Constructors
     */

    public void queueMessage(Packet arg1) {
        queuedPackets.add(arg1);
    }

    public synchronized boolean processQueuedPackets() {
        Packet p = null;
        synchronized (queuedPackets) {
            p = queuedPackets.poll();
        }
        if (p == null) {
            return false;
        }
        inStream.currentOffset = 0;
        packetType = p.getId();
        packetSize = p.getLength();
        inStream.buffer = p.getData();
        if (packetType > 0) {
            PacketHandler.processPacket(this, packetType, packetSize);
        }
        timeOutCounter = 0;
        return true;
    }

}
