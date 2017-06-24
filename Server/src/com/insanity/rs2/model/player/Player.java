package com.insanity.rs2.model.player;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.world.region.Region;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Player extends Entity {

    private Rights rights = Rights.PLAYER;

    public Player() {

    }

    @Override
    public void addToRegion(Region region) {
        region.addPlayer(this);
    }

    @Override
    public void removeFromRegion(Region region) {
        region.removePlayer(this);
    }
}
