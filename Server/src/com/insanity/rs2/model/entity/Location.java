package com.insanity.rs2.model.entity;

/**
 * Represents a single location in the game world (on the map).
 *
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Location {
    /**
     * The x coordinate.
     */
    private final int x;

    /**
     * The y coordinate.
     */
    private final int y;

    /**
     * The z coordinate.
     */
    private final int z;

    /**
     * Creates a location.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     */
    private Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a location.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The location.
     */
    public static Location create(int x, int y, int z) {
        return new Location(x, y, z);
    }

    /**
     * Gets the absolute x coordinate.
     *
     * @return The absolute x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the absolute y coordinate.
     *
     * @return The absolute y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the z coordinate, or height.
     *
     * @return The z coordinate.
     */
    public int getZ() {
        return z;
    }

    /**
     * Gets the local x coordinate relative to this region.
     *
     * @return The local x coordinate relative to this region.
     */
    public int getLocalX() {
        return getLocalX(this);
    }

    /**
     * Gets the local y coordinate relative to this region.
     *
     * @return The local y coordinate relative to this region.
     */
    public int getLocalY() {
        return getLocalY(this);
    }

    /**
     * Gets the local x coordinate relative to a specific region.
     *
     * @param l The region the coordinate will be relative to.
     * @return The local x coordinate.
     */
    public int getLocalX(Location l) {
        return x - 8 * l.getRegionX();
    }

    /**
     * Gets the local y coordinate relative to a specific region.
     *
     * @param l The region the coordinate will be relative to.
     * @return The local y coordinate.
     */
    public int getLocalY(Location l) {
        return y - 8 * l.getRegionY();
    }

    /**
     * Gets the region x coordinate.
     *
     * @return The region x coordinate.
     */
    public int getRegionX() {
        return (x >> 3) - 6;
    }

    /**
     * Gets the region y coordinate.
     *
     * @return The region y coordinate.
     */
    public int getRegionY() {
        return (y >> 3) - 6;
    }
}
