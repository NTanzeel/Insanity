package com.insanity;

import com.insanity.io.MySQL;
import com.insanity.rs2.engine.Engine;
import com.insanity.rs2.net.server.RS2Server;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 29/05/2017.
 */
public class Insanity {

    private static final Insanity instance = new Insanity();

    private RS2Server server = new RS2Server(RS2Server.DEFAULT_PORT);

    private Engine engine = new Engine();

    private MySQL database = new MySQL("localhost:3306/insanity", "root", "SQLRoot098!");

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
        engine.start();
        server.start();
    }

    public Engine getEngine() {
        return engine;
    }

}
