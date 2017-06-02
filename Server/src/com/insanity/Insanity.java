package com.insanity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 29/05/2017.
 */
public class Insanity {

    private static final Logger logger = Logger.getLogger(Insanity.class.getName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting Insanity");
        try {

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error Starting Insanity", e);
        }
    }
}
