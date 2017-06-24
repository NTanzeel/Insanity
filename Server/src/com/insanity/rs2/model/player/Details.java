package com.insanity.rs2.model.player;

import io.netty.channel.Channel;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Details {

    private Channel channel;

    private String username;

    private String password;

    public Details(Channel channel, String username, String password) {
        this.channel = channel;
        this.username = username;
        this.password = password;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}