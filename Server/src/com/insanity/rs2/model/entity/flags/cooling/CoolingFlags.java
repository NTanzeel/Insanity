package com.insanity.rs2.model.entity.flags.cooling;

import com.insanity.rs2.model.entity.Entity;

import java.util.BitSet;

/**
 * Represents an entity's individual cooldowns.
 *
 * @author Naqash Tanzeel
 */
public class CoolingFlags {

    /**
     * The bitset (flag data).
     */
    private BitSet cooldowns = new BitSet();

    /**
     * Checks if an update required.
     *
     * @return <code>true</code> if 1 or more flags are set,
     * <code>false</code> if not.
     */
    public boolean areCooldownsPending() {
        return !cooldowns.isEmpty();
    }

    /**
     * Flags (sets to true) a flag.
     *
     * @param flag The flag to flag.
     */
    public void flag(CoolingFlag flag, int duration, Entity entity) {
        cooldowns.set(flag.ordinal(), true);
//		World.getWorld().submit(new CooldownEvent(entity, cooldown, duration));
    }

    /**
     * Sets a cooldown.
     *
     * @param flag  The flag.
     * @param value The value.
     */

    public void set(CoolingFlag flag, boolean value) {
        cooldowns.set(flag.ordinal(), value);
    }

    /**
     * Gets the value of a flag.
     *
     * @param cooldownFlags The flag to get the value of.
     * @return The flag value.
     */
    public boolean get(CoolingFlag cooldownFlags) {
        return cooldowns.get(cooldownFlags.ordinal());
    }

    /**
     * Resest all update flags.
     */
    public void reset() {
        cooldowns.clear();
    }
}