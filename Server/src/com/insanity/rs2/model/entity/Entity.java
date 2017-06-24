package com.insanity.rs2.model.entity;

import com.insanity.rs2.model.entity.flags.cooling.CoolingFlags;
import com.insanity.rs2.model.entity.flags.update.UpdateFlags;
import com.insanity.rs2.model.entity.location.Location;
import com.insanity.rs2.model.npc.NPC;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.world.World;
import com.insanity.rs2.world.region.Region;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public abstract class Entity {

    private static final Location DEFAULT_LOCATION = Location.create(3200, 3200, 0);

    private int index;

    private Location location;

    private Location lastKnownLocation;

    private Region region;

    private UpdateFlags updateFlags = new UpdateFlags();

    private CoolingFlags coolingFlags = new CoolingFlags();

    private List<Player> localPlayers = new LinkedList<>();

    private List<NPC> localNPCs = new LinkedList<>();

    public Entity() {
        this.setLocation(DEFAULT_LOCATION);
        this.lastKnownLocation = getLocation();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;

        Region region = World.getWorld().getRegions().getRegion(location);
        if (this.region != region) {
            this.setRegion(region);
        }
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public Region getRegion() {
        return region;
    }

    private void setRegion(Region region) {
        if (this.region != null) {
            removeFromRegion(this.region);
        }
        this.region = region;
        addToRegion(region);
    }

    public abstract void addToRegion(Region region);

    public abstract void removeFromRegion(Region region);

    public UpdateFlags getUpdateFlags() {
        return updateFlags;
    }

    public CoolingFlags getCoolingFlags() {
        return coolingFlags;
    }
}
