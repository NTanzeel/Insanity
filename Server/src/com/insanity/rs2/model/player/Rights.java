package com.insanity.rs2.model.player;

/**
 * Represents the rights of a player.
 *
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */

public enum Rights {

    /**
     * A standard account.
     */
    PLAYER(0),

    /**
     * A player-moderator account.
     */
    MODERATOR(1),

    /**
     * An administrator account.
     */
    ADMINISTRATOR(2);

    /**
     * The integer representing this rights level.
     */
    private int value;

    /**
     * Creates a rights level.
     * @param value The integer representing this rights level.
     */
    private Rights(int value) {
        this.value = value;
    }

    /**
     * Gets an integer representing this rights level.
     * @return An integer representing this rights level.
     */
    public int toInteger() {
        return value;
    }

    /**
     * Gets rights by a specific integer.
     * @param value The integer returned by {@link #toInteger()}.
     * @return The rights level.
     */
    public static Rights getRights(int value) {
        if(value == 1) {
            return MODERATOR;
        } else if(value == 2) {
            return ADMINISTRATOR;
        } else {
            return PLAYER;
        }
    }
}
