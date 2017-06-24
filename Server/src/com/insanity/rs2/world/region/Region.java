package com.insanity.rs2.world.region;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.model.npc.NPC;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Region {

    public static final int SIZE = 32;

    /**
     * The regions coordinates.
     */
    private final Coordinates coordinates;

    /**
     * A list of the NPCs in this region.
     */
    private List<NPC> npcs = new LinkedList<NPC>();

    /**
     * A list of the players in this region.
     */
    private List<Player> players = new LinkedList<Player>();

    /**
     * A list o the entities in this region.
     */
    private List<Entity> entities = new LinkedList<Entity>();

    /**
     * Creates an instance to represent a region.
     *
     * @param coordinates The regions coordinates.
     */
    public Region(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets the regions coordinates.
     *
     * @return The regions coordiantes.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets a list of all the NPCs in the region.
     *
     * @return An unmodifiable collection of all the NPCs in the region.
     */
    public Collection<NPC> getNPCs() {
        synchronized (this) {
            return Collections.unmodifiableCollection(new LinkedList<NPC>(npcs));
        }
    }

    /**
     * Adds an NPC to the list of NPCs and entities in the region.
     *
     * @param npc The NPC to add to the region.
     */
    public void addNPC(NPC npc) {
        synchronized (this) {
            this.npcs.add(npc);
            this.entities.add(npc);
        }
    }

    /**
     * Removes an NPC from the list of NPCs and entities in the region.
     *
     * @param npc The NPC to be removed from the region.
     */
    public void removeNPC(NPC npc) {
        synchronized (this) {
            this.npcs.remove(npc);
            this.entities.remove(npc);
        }
    }

    /**
     * Gets a list of all the players in the region.
     *
     * @return An unmodifiable collection of all the players in the region.
     */
    public Collection<Player> getPlayers() {
        synchronized (this) {
            return Collections.unmodifiableCollection(new LinkedList<Player>(players));
        }
    }

    /**
     * Adds a player to the list of players and entities in the region.
     *
     * @param player The player to add to the region.
     */
    public void addPlayer(Player player) {
        synchronized (this) {
            this.players.add(player);
            this.entities.add(player);
        }
    }

    /**
     * Removes a player from the list of players and entities in the region.
     *
     * @param player The player to be removed from the region.
     */
    public void removePlayer(Player player) {
        synchronized (this) {
            this.players.remove(player);
            this.entities.remove(player);
        }
    }

    /**
     * Gets a list of all the entities in the region.
     *
     * @return An unmodifiable collection of all the entities in the region.
     */
    public Collection<Entity> getEntities() {
        synchronized (this) {
            return Collections.unmodifiableCollection(new LinkedList<Entity>(entities));
        }
    }

    /**
     * Adds an entity to the list of entities in the region.
     *
     * @param entity The entity to add to the region.
     */
    public void addEntity(Entity entity) {
        synchronized (this) {
            this.entities.add(entity);
        }
    }

    /**
     * Gets all the regions which overlap with this region.
     *
     * @return An unmodifiable collection of all the regions which overlap with this region.
     */
    public Collection<Region> getOverlappingRegions() {
        List<Region> overlappingRegions = new LinkedList<Region>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                overlappingRegions.add(World.getWorld().getRegions().getRegion(coordinates.getRelativeCoordinates(x, y)));
            }
        }
        return Collections.unmodifiableCollection(overlappingRegions);
    }
}