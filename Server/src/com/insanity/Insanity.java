package com.insanity;

import com.insanity.io.MySQL;
import com.insanity.core.engine.TaskEngine;
import com.insanity.rs2.net.server.RS2Server;

import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 29/05/2017.
 */
public class Insanity {

    private static final Logger logger = Logger.getLogger(Insanity.class.getName());

    private static final Insanity instance = new Insanity();

    private RS2Server server = new RS2Server(RS2Server.DEFAULT_PORT);

    private TaskEngine engine = new TaskEngine();

    private MySQL database = new MySQL("localhost:3306/insanity", "insanity", "password");

    private Insanity() {

    }

    public static Insanity getInstance() {
        return instance;
    }

    public final int getVersion() {
        return 317;
    }

    public MySQL getDatabase() {
        return database;
    }

    public void start() throws InterruptedException {
        logger.info("Starting Insanity");
        server.bind();
        engine.start();
        logger.info("Ready - Listening for Connections On: " + RS2Server.DEFAULT_PORT);
        server.start();
    }

    public TaskEngine getEngine() {
        return engine;
    }
}
