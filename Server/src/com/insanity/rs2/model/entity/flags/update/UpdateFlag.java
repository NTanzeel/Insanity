package com.insanity.rs2.model.entity.flags.update;

/**
 * Represents a single type of update flag.
 *
 * @author Graham Edgecombe
 */
public enum UpdateFlag {

    /**
     * Appearance update.
     */
    APPEARANCE,

    /**
     * Chat update.
     */
    CHAT,

    /**
     * Graphics update.
     */
    GRAPHICS,

    /**
     * Animation update.
     */
    ANIMATION,

    /**
     * Forced chat update.
     */
    FORCED_CHAT,

    /**
     * Interacting entity update.
     */
    FACE_ENTITY,

    /**
     * Face coordinate entity update.
     */
    FACE_COORDINATE,

    /**
     * Hit update.
     */
    HIT,

    /**
     * Hit 2 update/
     */
    HIT_2,

    /**
     * Update flag used to transform npc to another.
     */
    TRANSFORM,
}