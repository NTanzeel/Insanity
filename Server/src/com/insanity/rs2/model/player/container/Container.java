package com.insanity.rs2.model.player.container;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 25/06/2017.
 */
public class Container {


    private Type type;

    private int capacity;

    public Container(int capacity, Type type) {

    }

    public enum Type {
        STANDARD,

        STACKING
    }
}
