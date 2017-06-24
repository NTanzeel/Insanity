package com.insanity.rs2.world.region;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.model.entity.location.Location;
import com.insanity.rs2.model.npc.NPC;
import com.insanity.rs2.model.player.Player;

import java.util.*;

public class Regions {

    private Map<Coordinates, Region> regions = new HashMap<Coordinates, Region>();

    public Regions() {

    }

    /**
     * Gets a region by location.
     * @param location The location.
     * @return The region.
     */
    public Region getRegion(Location location) {
        return getRegion(new Coordinates(location.getX() / Region.SIZE, location.getY() / Region.SIZE));
    }

    /**
     * Gets a region by its coordinate.
     * @param coordinates The coordinates.
     * @return The region.
     */
    public Region getRegion(Coordinates coordinates) {
        Region region = new Region(coordinates);
        if (regions.containsKey(coordinates)) region = regions.get(coordinates);
        else regions.put(coordinates, region);
        return region;
    }

    /**
     * Gets the local NPCs around an entity.
     * @param entity The entity.
     * @return An unmodifiable collection of all the NPCs around the entity.
     */
    public Collection<NPC> getLocalNPCs(Entity entity) {
        LinkedList<NPC> localNPCs = new LinkedList<NPC>();
        for (Region region : entity.getRegion().getOverlappingRegions()) {
            localNPCs.addAll(region.getNPCs());
        }
        return localNPCs;
    }

    /**
     * Gets the local players around an entity.
     * @param entity The entity.
     * @return An unmodifiable collection of all the players around the entity.
     */
    public Collection<Player> getLocalPlayers(Entity entity) {
        LinkedList<Player> localPlayers = new LinkedList<Player>();
        for (Region region : entity.getRegion().getOverlappingRegions()) {
            localPlayers.addAll(region.getPlayers());
        }
        return localPlayers;
    }

    /**
     * Gets the local entities around an entity.
     * @param entity The entity.
     * @return An unmodifiable collection of all the entities around the entity.
     */
    public Collection<Entity> getLocalEntities(Entity entity) {
        LinkedList<Entity> localEntities = new LinkedList<Entity>();
        for (Region region : entity.getRegion().getOverlappingRegions()) {
            localEntities.addAll(region.getEntities());
        }
        return localEntities;
    }
}