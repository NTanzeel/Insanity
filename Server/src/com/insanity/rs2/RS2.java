package com.insanity.rs2;

import com.insanity.rs2.net.server.RS2Server;
import com.insanity.rs2.world.World;

public class RS2 {

    private static RS2 instance = new RS2();

    public static RS2 getInstance() {
        return instance;
    }

    private World world = World.getInstance();

    private RS2Server server = new RS2Server(RS2Server.DEFAULT_PORT);

    public RS2() {
        // To-Do: Background loading
    }

    public void init() {
        this.registerGlobalEvents();
    }

    public RS2Server getServer() {
        return server;
    }

    /**
     * Registers global events such as updating.
     */
    private void registerGlobalEvents() {
    }

}
