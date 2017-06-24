package com.insanity.rs2.world.region;

/**
 * Represents the coordinates of a region by storing the x and y values.
 *
 * @author Naqash Tanzeel
 * @version 1.0
 */
public class Coordinates {

    /**
     * The regions x coordinate.
     */
    private final int x;

    /**
     * The regions y coordinate.
     */
    private final int y;

    /**
     * @param x The regions x coordinate.
     * @param y The regions y coordinate.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the regions x coordinate.
     *
     * @return The regions x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the regions y coordinate.
     *
     * @return The regions y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets a region coordinate in relation to this coordinate.
     *
     * @param x The amount to increase or decrease the x coordinate by.
     * @param y The amount to increase or decrease the y coordinates by.
     * @return A region coordinate in relation to this coordinate, offset by the given parameters.
     */
    public Coordinates getRelativeCoordinates(int x, int y) {
        return new Coordinates(this.x + x, this.y + y);
    }

    /**
     * Checks whether this region overlaps with the given region.
     *
     * @param other The region to check overlap with.
     * @return Whether this region overlaps with the given region.
     */
    public boolean overlaps(Coordinates other) {
        boolean overlapsX = (x == other.x || x == other.x - 1 || x == other.x + 1);
        boolean overlapsY = (y == other.y || y == other.y - 1 || y == other.y + 1);
        return (overlapsX && overlapsY);
    }

    /**
     * Compares this coordinate with a given object to check if they are the same.
     *
     * @param obj The object to compare with.
     * @return Whether the two objects are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            Coordinates other = (Coordinates) obj;
            return (x == other.x && y == other.x);
        }
        return false;
    }

    /**
     * Converts the coordinates to a human readable string.
     *
     * @return A textual representation of the coordinates.
     */
    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}