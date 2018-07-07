package com.insanity.rs2.world;

import com.insanity.rs2.world.entities.Entities;
import com.insanity.rs2.world.region.Regions;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class World {

    private static final World instance = new World();

    private Entities entities = new Entities();

    private Regions regions = new Regions();

    public static World getInstance() {
        return instance;
    }

    public Entities getEntities() {
        return entities;
    }

    public Regions getRegions() {
        return regions;
    }
}
