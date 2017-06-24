package server.model.players;

import server.Config;

public class PlayerAssistant {

    private int mapStatus = 0;
    /**
     * Show option, attack, trade, follow etc
     **/
    private String optionType = "null";
    private Player c;

    PlayerAssistant(Player Player) {
        this.c = Player;
    }


    void clearClanChat() {
        c.getPA().sendFrame126("Talking in: ", 18139);
        c.getPA().sendFrame126("Owner: ", 18140);
        for (int j = 18144; j < 18244; j++)
            c.getPA().sendFrame126("", j);
    }

    private void sendFrame126(String s, int id) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrameVarSizeWord(126);
            c.getOutStream().writeString(s);
            c.getOutStream().writeWordA(id);
            c.getOutStream().endFrameVarSizeWord();
            c.flushOutStream();
        }
    }

    void setSkillLevel(int skillNum, int currentLevel, int XP) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(134);
            c.getOutStream().writeByte(skillNum);
            c.getOutStream().writeDWord_v1(XP);
            c.getOutStream().writeByte(currentLevel);
            c.flushOutStream();
        }
    }

    void sendFrame107() {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(107);
            c.flushOutStream();
        }
    }

    void sendFrame36(int id, int state) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(36);
            c.getOutStream().writeWordBigEndian(id);
            c.getOutStream().writeByte(state);
            c.flushOutStream();
        }
    }

    void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(206);
            c.getOutStream().writeByte(publicChat);
            c.getOutStream().writeByte(privateChat);
            c.getOutStream().writeByte(tradeBlock);
            c.flushOutStream();
        }
    }

    public void removeAllWindows() {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(219);
            c.flushOutStream();
        }
    }

    void walkableInterface(int id) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(208);
            c.getOutStream().writeWordBigEndian_dup(id);
            c.flushOutStream();
        }
    }

    void sendFrame99(int state) { // used for disabling map
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            if (mapStatus != state) {
                mapStatus = state;
                c.getOutStream().createFrame(99);
                c.getOutStream().writeByte(state);
                c.flushOutStream();
            }
        }
    }

    void sendCrashFrame() { // used for crashing cheat clients
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(123);
            c.flushOutStream();
        }
    }

    /**
     * Reseting animations for everyone
     **/

    public void frame1() {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            if (PlayerHandler.players[i] != null) {
                Player person = (Player) PlayerHandler.players[i];
                if (person != null) {
                    if (person.getOutStream() != null && !person.disconnected) {
                        if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
                            person.getOutStream().createFrame(1);
                            person.flushOutStream();
                            person.getPA().requestUpdates();
                        }
                    }
                }
            }
        }
    }


    public void showOption(int i, int l, String s, int a) {
        if (c.getOutStream() != null && c != null) {
            if (!optionType.equalsIgnoreCase(s)) {
                optionType = s;
                c.getOutStream().createFrameVarSize(104);
                c.getOutStream().writeByteC(i);
                c.getOutStream().writeByteA(l);
                c.getOutStream().writeString(s);
                c.getOutStream().endFrameVarSize();
                c.flushOutStream();
            }
        }
    }

    /**
     * Location change for digging, levers etc
     **/

    public void changeLocation() {
        switch (c.newLocation) {
            case 1:
                sendFrame99(2);
                movePlayer(3578, 9706, -1);
                break;
            case 2:
                sendFrame99(2);
                movePlayer(3568, 9683, -1);
                break;
            case 3:
                sendFrame99(2);
                movePlayer(3557, 9703, -1);
                break;
            case 4:
                sendFrame99(2);
                movePlayer(3556, 9718, -1);
                break;
            case 5:
                sendFrame99(2);
                movePlayer(3534, 9704, -1);
                break;
            case 6:
                sendFrame99(2);
                movePlayer(3546, 9684, -1);
                break;
        }
        c.newLocation = 0;
    }


    public void movePlayer(int x, int y, int h) {
        c.resetWalkingQueue();
        c.teleportToX = x;
        c.teleportToY = y;
        c.heightLevel = h;
        requestUpdates();
    }

    public void walkTo(int i, int j) {
        c.newWalkCmdSteps = 0;
        if (++c.newWalkCmdSteps > 50) c.newWalkCmdSteps = 0;
        int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for (int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

    public void requestUpdates() {
        c.updateRequired = true;
        c.setAppearanceUpdateRequired(true);
    }


    public void refreshSkill(int i) {
        switch (i) {
            case 0:
                sendFrame126("" + c.playerLevel[0] + "", 4004);
                sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
                sendFrame126("" + c.playerXP[0] + "", 4044);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) + "", 4045);
                break;

            case 1:
                sendFrame126("" + c.playerLevel[1] + "", 4008);
                sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
                sendFrame126("" + c.playerXP[1] + "", 4056);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) + "", 4057);
                break;

            case 2:
                sendFrame126("" + c.playerLevel[2] + "", 4006);
                sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
                sendFrame126("" + c.playerXP[2] + "", 4050);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) + "", 4051);
                break;

            case 3:
                sendFrame126("" + c.playerLevel[3] + "", 4016);
                sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
                sendFrame126("" + c.playerXP[3] + "", 4080);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1) + "", 4081);
                break;

            case 4:
                sendFrame126("" + c.playerLevel[4] + "", 4010);
                sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
                sendFrame126("" + c.playerXP[4] + "", 4062);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) + "", 4063);
                break;

            case 5:
                sendFrame126("" + c.playerLevel[5] + "", 4012);
                sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
                sendFrame126("" + c.playerXP[5] + "", 4068);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) + "", 4069);
                sendFrame126("" + c.playerLevel[5] + "/" + getLevelForXP(c.playerXP[5]) + "", 687);// Prayer frame
                break;

            case 6:
                sendFrame126("" + c.playerLevel[6] + "", 4014);
                sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
                sendFrame126("" + c.playerXP[6] + "", 4074);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) + "", 4075);
                break;

            case 7:
                sendFrame126("" + c.playerLevel[7] + "", 4034);
                sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
                sendFrame126("" + c.playerXP[7] + "", 4134);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) + "", 4135);
                break;

            case 8:
                sendFrame126("" + c.playerLevel[8] + "", 4038);
                sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
                sendFrame126("" + c.playerXP[8] + "", 4146);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) + "", 4147);
                break;

            case 9:
                sendFrame126("" + c.playerLevel[9] + "", 4026);
                sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
                sendFrame126("" + c.playerXP[9] + "", 4110);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) + "", 4111);
                break;

            case 10:
                sendFrame126("" + c.playerLevel[10] + "", 4032);
                sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
                sendFrame126("" + c.playerXP[10] + "", 4128);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) + "", 4129);
                break;

            case 11:
                sendFrame126("" + c.playerLevel[11] + "", 4036);
                sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
                sendFrame126("" + c.playerXP[11] + "", 4140);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) + "", 4141);
                break;

            case 12:
                sendFrame126("" + c.playerLevel[12] + "", 4024);
                sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
                sendFrame126("" + c.playerXP[12] + "", 4104);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) + "", 4105);
                break;

            case 13:
                sendFrame126("" + c.playerLevel[13] + "", 4030);
                sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
                sendFrame126("" + c.playerXP[13] + "", 4122);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) + "", 4123);
                break;

            case 14:
                sendFrame126("" + c.playerLevel[14] + "", 4028);
                sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
                sendFrame126("" + c.playerXP[14] + "", 4116);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1) + "", 4117);
                break;

            case 15:
                sendFrame126("" + c.playerLevel[15] + "", 4020);
                sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
                sendFrame126("" + c.playerXP[15] + "", 4092);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) + "", 4093);
                break;

            case 16:
                sendFrame126("" + c.playerLevel[16] + "", 4018);
                sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
                sendFrame126("" + c.playerXP[16] + "", 4086);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) + "", 4087);
                break;

            case 17:
                sendFrame126("" + c.playerLevel[17] + "", 4022);
                sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
                sendFrame126("" + c.playerXP[17] + "", 4098);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) + "", 4099);
                break;

            case 18:
                sendFrame126("" + c.playerLevel[18] + "", 12166);
                sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 12167);
                sendFrame126("" + c.playerXP[18] + "", 12171);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) + "", 12172);
                break;

            case 19:
                sendFrame126("" + c.playerLevel[19] + "", 13926);
                sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 13927);
                sendFrame126("" + c.playerXP[19] + "", 13921);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) + "", 13922);
                break;

            case 20:
                sendFrame126("" + c.playerLevel[20] + "", 4152);
                sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 4153);
                sendFrame126("" + c.playerXP[20] + "", 4157);
                sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) + "", 4158);
                break;
        }
    }

    public int getXPForLevel(int level) {
        int points = 0;
        int output = 0;

        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            if (lvl >= level) return output;
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    public int getLevelForXP(int exp) {
        int points = 0;
        int output = 0;
        if (exp > 13034430) return 99;
        for (int lvl = 1; lvl <= 99; lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            output = (int) Math.floor(points / 4);
            if (output >= exp) {
                return lvl;
            }
        }
        return 0;
    }


    public void handleLoginText() {
        c.getPA().sendFrame126("Monster Teleport", 13037);
        c.getPA().sendFrame126("Minigame Teleport", 13047);
        c.getPA().sendFrame126("Boss Teleport", 13055);
        c.getPA().sendFrame126("Pking Teleport", 13063);
        c.getPA().sendFrame126("Skill Teleport", 13071);
        c.getPA().sendFrame126("Monster Teleport", 1300);
        c.getPA().sendFrame126("Minigame Teleport", 1325);
        c.getPA().sendFrame126("Boss Teleport", 1350);
        c.getPA().sendFrame126("Pking Teleport", 1382);
        c.getPA().sendFrame126("Skill Teleport", 1415);
    }

    public void handleWeaponStyle() {
        if (c.fightMode == 0) {
            c.getPA().sendFrame36(43, c.fightMode);
        } else if (c.fightMode == 1) {
            c.getPA().sendFrame36(43, 3);
        } else if (c.fightMode == 2) {
            c.getPA().sendFrame36(43, 1);
        } else if (c.fightMode == 3) {
            c.getPA().sendFrame36(43, 2);
        }
    }

}
