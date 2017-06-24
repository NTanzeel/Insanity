package com.insanity;

import com.insanity.io.MySQL;
import com.insanity.rs2.net.server.RS2Server;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 29/05/2017.
 */
public class Insanity {

    private static final Insanity instance = new Insanity();

    private RS2Server server = new RS2Server(RS2Server.DEFAULT_PORT);

    private MySQL database = new MySQL("http://localhost/insanity", "root", "");

    private Insanity() {

    }

    public static Insanity getInstance() {
        return instance;
    }

    public final int getVersion() {
        return 1;
    }

    public void start() throws InterruptedException {
        server.start();
    }
}
