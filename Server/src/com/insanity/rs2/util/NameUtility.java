package com.insanity.rs2.util;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class NameUtility {

    /**
     * Formats a name for display.
     *
     * @param s The name.
     * @return The formatted name.
     */
    public static String formatName(String s) {
        return fixName(s.replace(" ", "_"));
    }

    /**
     * Method that fixes capitalization in a name.
     *
     * @param s The name.
     * @return The formatted name.
     */
    private static String fixName(final String s) {
        if (s.length() > 0) {
            final char ac[] = s.toCharArray();
            for (int j = 0; j < ac.length; j++)
                if (ac[j] == '_') {
                    ac[j] = ' ';
                    if ((j + 1 < ac.length) && (ac[j + 1] >= 'a') && (ac[j + 1] <= 'z')) {
                        ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
                    }
                }

            if ((ac[0] >= 'a') && (ac[0] <= 'z')) {
                ac[0] = (char) ((ac[0] + 65) - 97);
            }
            return new String(ac);
        } else {
            return s;
        }
    }

    /**
     * Checks if a name is valid.
     *
     * @param s The name.
     * @return <code>true</code> if so, <code>false</code> if not.
     */
    public static boolean isValidName(String s) {
        return formatNameForProtocol(s).matches("[a-z0-9_]+");
    }

    /**
     * Formats a name for use in the protocol.
     *
     * @param s The name.
     * @return The formatted name.
     */
    public static String formatNameForProtocol(String s) {
        return s.toLowerCase().replace(" ", "_");
    }
}
