package server.model.players;

import server.util.Misc;

import java.io.*;

public class PlayerSave {

    /**
     * Loading
     **/
    public static int loadGame(Player p, String playerName, String playerPass) {
        String line;
        String token;
        String token2;
        String[] token3;
        boolean EndOfFile = false;
        int ReadMode = 0;
        BufferedReader characterFile = null;
        boolean File1 = false;

        try {
            characterFile = new BufferedReader(new FileReader("./game/players/" + playerName + ".txt"));
            File1 = true;
        } catch (FileNotFoundException ignored) {
        }

        if (!File1) {
            Misc.println(playerName + ": character file not found.");
            p.newPlayer = false;
            return 0;
        }

        try {
            line = characterFile.readLine();
        } catch (IOException ioexception) {
            Misc.println(playerName + ": error loading file.");
            return 3;
        }
        while (!EndOfFile && line != null) {
            line = line.trim();
            int spot = line.indexOf("=");
            if (spot > -1) {
                token = line.substring(0, spot);
                token = token.trim();
                token2 = line.substring(spot + 1);
                token2 = token2.trim();
                token3 = token2.split("\t");
                switch (ReadMode) {
                    case 1:
                        if (token.equals("character-password")) {
                            if (playerPass.equalsIgnoreCase(token2)) {
                                playerPass = token2;
                            } else {
                                return 3;
                            }
                        }
                        break;
                    case 2:
                        switch (token) {
                            case "character-height":
                                p.heightLevel = Integer.parseInt(token2);
                                break;
                            case "character-posx":
                                p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
                                break;
                            case "character-posy":
                                p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
                                break;
                            case "character-rights":
                                p.playerRights = Integer.parseInt(token2);
                                break;
                        }
                        break;

                    case 4:
                        if (token.equals("character-look")) {
                            p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                        }
                        break;

                    case 5:
                        if (token.equals("character-skill")) {
                            p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
                            p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
                        }
                        break;
                    case 9:
                        break;
                }
            } else {
                switch (line) {
                    case "[ACCOUNT]":
                        ReadMode = 1;
                        break;
                    case "[CHARACTER]":
                        ReadMode = 2;
                        break;
                    case "[EQUIPMENT]":
                        ReadMode = 3;
                        break;
                    case "[LOOK]":
                        ReadMode = 4;
                        break;
                    case "[SKILLS]":
                        ReadMode = 5;
                        break;
                    case "[ITEMS]":
                        ReadMode = 6;
                        break;
                    case "[BANK]":
                        ReadMode = 7;
                        break;
                    case "[FRIENDS]":
                        ReadMode = 8;
                        break;
                    case "[IGNORES]":
                        ReadMode = 9;
                        break;
                    case "[EOF]":
                        try {
                            characterFile.close();
                        } catch (IOException ignored) {
                        }
                        return 1;
                }
            }
            try {
                line = characterFile.readLine();
            } catch (IOException exception) {
                EndOfFile = true;
            }
        }
        try {
            characterFile.close();
        } catch (IOException ignored) {
        }
        return 13;
    }

    /**
     * Saving
     **/
    public static boolean saveGame(Player p) {
        if (!p.saveFile || p.newPlayer || !p.saveCharacter) {
            return false;
        }
        if (p.playerName == null || PlayerHandler.players[p.playerId] == null) {
            return false;
        }
        p.playerName = p.playerName2;

        BufferedWriter characterFile;
        try {
            characterFile = new BufferedWriter(new FileWriter("./game/players/" + p.playerName + ".txt"));

			/* ACCOUNT */
            characterFile.write("[ACCOUNT]", 0, 9);
            characterFile.newLine();
            characterFile.write("character-username = ", 0, 21);
            characterFile.write(p.playerName, 0, p.playerName.length());
            characterFile.newLine();
            characterFile.write("character-password = ", 0, 21);
            characterFile.write(p.playerPass, 0, p.playerPass.length());
            characterFile.newLine();
            characterFile.newLine();

			/* CHARACTER */
            characterFile.write("[CHARACTER]", 0, 11);
            characterFile.newLine();
            characterFile.write("character-height = ", 0, 19);
            characterFile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
            characterFile.newLine();
            characterFile.write("character-posx = ", 0, 17);
            characterFile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
            characterFile.newLine();
            characterFile.write("character-posy = ", 0, 17);
            characterFile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
            characterFile.newLine();
            characterFile.write("character-rights = ", 0, 19);
            characterFile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
            characterFile.newLine();
            characterFile.newLine();

			/* LOOK */
            characterFile.write("[LOOK]", 0, 6);
            characterFile.newLine();
            for (int i = 0; i < p.playerAppearance.length; i++) {
                characterFile.write("character-look = ", 0, 17);
                characterFile.write(Integer.toString(i), 0, Integer.toString(i).length());
                characterFile.write("	", 0, 1);
                characterFile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
                characterFile.newLine();
            }
            characterFile.newLine();

			/* SKILLS */
            characterFile.write("[SKILLS]", 0, 8);
            characterFile.newLine();
            for (int i = 0; i < p.playerLevel.length; i++) {
                characterFile.write("character-skill = ", 0, 18);
                characterFile.write(Integer.toString(i), 0, Integer.toString(i).length());
                characterFile.write("	", 0, 1);
                characterFile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
                characterFile.write("	", 0, 1);
                characterFile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
                characterFile.newLine();
            }
            characterFile.newLine();

            /* EOF */
            characterFile.write("[EOF]", 0, 5);
            characterFile.newLine();
            characterFile.newLine();
            characterFile.close();
        } catch (IOException ioexception) {
            Misc.println(p.playerName + ": error writing file.");
            return false;
        }
        return true;
    }

}