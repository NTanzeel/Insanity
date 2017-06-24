package com.insanity.rs2.model.npc;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.world.region.Region;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class NPC extends Entity {

    @Override
    public void addToRegion(Region region) {
        region.addNPC(this);
    }

    @Override
    public void removeFromRegion(Region region) {
        region.removeNPC(this);
    }
}
