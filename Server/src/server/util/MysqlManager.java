package server.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.model.players.Player;

/**
 * MySQL Class
 * 
 * @author Ryan / Lmctruck30
 * 
 */

public class MysqlManager {

	/** MySQL Connection */
	public static Connection conn = null;
	public static Statement statement = null;
	public static ResultSet results = null;

	public static String MySQLDataBase = "game";
	public static String MySQLURL = "localhost";
	public static String MySQLUser = "root";
	public static String MySQLPassword = "tsm123";

	/**
	 * Creates a Connection to the MySQL Database
	 */
	public synchronized static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://riotscape.com/riot_forum",
			// "riot_forum", "ryan16");
			// Misc.println("MySQL Connected");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public synchronized static void destroyConnection() {
		try {
			statement.close();
			conn.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public synchronized static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = statement.executeQuery(s);
				return rs;
			} else {
				statement.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			createConnection();
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * Save Sessions HighScores
	 * 
	 * @param playerToSave
	 *            The session that saves their stats
	 * @return The flag true if successful
	 */
	public synchronized static boolean saveHighScore(Player playerToSave) {
		try {
			query("DELETE FROM `skills` WHERE playerName = '"
					+ playerToSave.playerName + "';");
			query("DELETE FROM `skillsoverall` WHERE playerName = '"
					+ playerToSave.playerName + "';");
			query("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`) VALUES ('"
					+ playerToSave.playerName
					+ "',"
					+ playerToSave.playerLevel[0]
					+ ","
					+ playerToSave.playerXP[0]
					+ ","
					+ playerToSave.playerLevel[1]
					+ ","
					+ playerToSave.playerXP[1]
					+ ","
					+ playerToSave.playerLevel[2]
					+ ","
					+ playerToSave.playerXP[2]
					+ ","
					+ playerToSave.playerLevel[3]
					+ ","
					+ playerToSave.playerXP[3]
					+ ","
					+ playerToSave.playerLevel[4]
					+ ","
					+ playerToSave.playerXP[4]
					+ ","
					+ playerToSave.playerLevel[5]
					+ ","
					+ playerToSave.playerXP[5]
					+ ","
					+ playerToSave.playerLevel[6]
					+ ","
					+ playerToSave.playerXP[6]
					+ ","
					+ playerToSave.playerLevel[7]
					+ ","
					+ playerToSave.playerXP[7]
					+ ","
					+ playerToSave.playerLevel[8]
					+ ","
					+ playerToSave.playerXP[8]
					+ ","
					+ playerToSave.playerLevel[9]
					+ ","
					+ playerToSave.playerXP[9]
					+ ","
					+ playerToSave.playerLevel[10]
					+ ","
					+ playerToSave.playerXP[10]
					+ ","
					+ playerToSave.playerLevel[11]
					+ ","
					+ playerToSave.playerXP[11]
					+ ","
					+ playerToSave.playerLevel[12]
					+ ","
					+ playerToSave.playerXP[12]
					+ ","
					+ playerToSave.playerLevel[13]
					+ ","
					+ playerToSave.playerXP[13]
					+ ","
					+ playerToSave.playerLevel[14]
					+ ","
					+ playerToSave.playerXP[14]
					+ ","
					+ playerToSave.playerLevel[15]
					+ ","
					+ playerToSave.playerXP[15]
					+ ","
					+ playerToSave.playerLevel[16]
					+ ","
					+ playerToSave.playerXP[16]
					+ ","
					+ playerToSave.playerLevel[17]
					+ ","
					+ playerToSave.playerXP[17]
					+ ","
					+ playerToSave.playerLevel[18]
					+ ","
					+ playerToSave.playerXP[18]
					+ ","
					+ playerToSave.playerLevel[19]
					+ ","
					+ playerToSave.playerXP[19]
					+ ","
					+ playerToSave.playerLevel[20]
					+ ","
					+ playerToSave.playerXP[20] + ");");
			query("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) VALUES ('"
					+ playerToSave.playerName
					+ "',"
					+ (playerToSave.getLevelForXP(playerToSave.playerXP[0])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[1])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[2])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[3])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[4])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[5])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[6])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[7])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[8])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[9])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[10])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[11])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[12])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[13])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[14])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[15])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[16])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[17])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[18])
							+ playerToSave
									.getLevelForXP(playerToSave.playerXP[19]) + playerToSave
								.getLevelForXP(playerToSave.playerXP[20]))
					+ ","
					+ ((playerToSave.playerXP[0]) + (playerToSave.playerXP[1])
							+ (playerToSave.playerXP[2])
							+ (playerToSave.playerXP[3])
							+ (playerToSave.playerXP[4])
							+ (playerToSave.playerXP[5])
							+ (playerToSave.playerXP[6])
							+ (playerToSave.playerXP[7])
							+ (playerToSave.playerXP[8])
							+ (playerToSave.playerXP[9])
							+ (playerToSave.playerXP[10])
							+ (playerToSave.playerXP[11])
							+ (playerToSave.playerXP[12])
							+ (playerToSave.playerXP[13])
							+ (playerToSave.playerXP[14])
							+ (playerToSave.playerXP[15])
							+ (playerToSave.playerXP[16])
							+ (playerToSave.playerXP[17])
							+ (playerToSave.playerXP[18])
							+ (playerToSave.playerXP[19]) + (playerToSave.playerXP[20]))
					+ ");");
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Save Voting Point Info
	 * 
	 * @param c
	 *            The session's client
	 * @return The flag if true was successful
	 */
	public static boolean saveVotingInfo(Player c) {
		try {
			query("INSERT INTO `skills` (`playerName`,`playerPass') VALUES ('"
					+ c.playerName + "'," + c.playerPass + ");");
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	public static int loadVotingPoints(Player c) {
		try {
			ResultSet group = statement
					.executeQuery("SELECT * FROM user WHERE username = '"
							+ c.playerName + "'");
			while (group.next()) {
				String groupp = group.getString("usergroupid");
				int mgroup = Integer.parseInt(groupp);
				if (mgroup > 0) {
					return mgroup;
				}
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

	public static int loadDonationPoints(Player c) {
		try {
			ResultSet group = statement
					.executeQuery("SELECT * FROM user WHERE username = '"
							+ c.playerName + "'");
			while (group.next()) {
				String groupp = group.getString("usergroupid");
				int mgroup = Integer.parseInt(groupp);
				if (mgroup > 0) {
					return mgroup;
				}
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}

}
