package com.insanity;

import com.insanity.net.server.RS2Server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 29/05/2017.
 */
public class Insanity {

    private static final Insanity instance = new Insanity();
    private static final Logger logger = Logger.getLogger(Insanity.class.getName());
    private RS2Server server = new RS2Server(RS2Server.DEFAULT_PORT);

    private Insanity() {

    }

    public static Insanity getInstance() {
        return instance;
    }

    public static void main(String[] args) throws Exception {
        logger.info("Starting Insanity");
        try {
            instance.initialize();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Starting Insanity", e);
        }
    }

    private void initialize() throws InterruptedException {
        server.start();
    }
}
