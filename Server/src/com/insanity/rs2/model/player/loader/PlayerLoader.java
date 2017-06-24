package com.insanity.rs2.model.player.loader;

import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.model.player.Player;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public interface PlayerLoader {

    /**
     * Checks if a set of login details are correct. If correct, creates but
     * does not load, the player object.
     *
     * @param details The login details.
     * @return The login result.
     */
    public LoginResult checkLogin(Details details);

    /**
     * Loads player information.
     *
     * @param player The player object.
     * @return <code>true</code> on success, <code>false</code> on failure.
     */
    public boolean loadPlayer(Player player);

    /**
     * Saves player information.
     *
     * @param player The player object.
     * @return <code>true</code> on success, <code>false</code> on failure.
     */
    public boolean savePlayer(Player player);

    /**
     * Represents the result of a login request.
     *
     * @author ntanzeel
     * @version 1.0.0
     * @since 24/06/2017.
     */
    public static class LoginResult {

        /**
         * The return code.
         */
        private int returnCode;

        /**
         * The player object, or <code>null</code> if the login failed.
         */
        private Player player;

        /**
         * Creates a login result that failed.
         *
         * @param returnCode The return code.
         */
        public LoginResult(int returnCode) {
            this(returnCode, null);
        }

        /**
         * Creates a login result that succeeded.
         *
         * @param returnCode The return code.
         * @param player     The player object.
         */
        public LoginResult(int returnCode, Player player) {
            this.returnCode = returnCode;
            this.player = player;
        }

        /**
         * Gets the return code.
         *
         * @return The return code.
         */
        public int getReturnCode() {
            return returnCode;
        }

        /**
         * Gets the player.
         *
         * @return The player.
         */
        public Player getPlayer() {
            return player;
        }
    }
}
