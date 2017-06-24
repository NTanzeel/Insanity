package com.insanity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Server {

    private static final Logger logger = Logger.getLogger(Insanity.class.getName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting Insanity");
        try {
            Insanity.getInstance().start();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Starting Insanity", e);
        }
    }
}
