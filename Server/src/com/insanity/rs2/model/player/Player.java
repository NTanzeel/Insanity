package com.insanity.rs2.model.player;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.world.region.Region;
import io.netty.channel.Channel;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Player extends Entity {

    private String name;

    private String password;

    private Channel channel;

    private Rights rights = Rights.PLAYER;

    public Player(Details details) {
        this.name = details.getUsername();
        this.password = details.getPassword();
        this.channel = details.getChannel();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Channel getChannel() {
        return channel;
    }

    public Rights getRights() {
        return rights;
    }

    @Override
    public void addToRegion(Region region) {
        region.addPlayer(this);
    }

    @Override
    public void removeFromRegion(Region region) {
        region.removePlayer(this);
    }
}
