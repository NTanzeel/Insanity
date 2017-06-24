package com.insanity.rs2.model.player;

import com.insanity.rs2.net.security.ISAACCipher;
import io.netty.channel.Channel;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Details {

    private final Channel channel;
    private final int uid;
    private final String username;
    private final String password;
    private final ISAACCipher inCipher;
    private final ISAACCipher outCipher;

    public Details(Channel channel, int uid, String username, String password, ISAACCipher inCipher, ISAACCipher outCipher) {
        this.channel = channel;
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.inCipher = inCipher;
        this.outCipher = outCipher;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ISAACCipher getInCipher() {
        return inCipher;
    }

    public ISAACCipher getOutCipher() {
        return outCipher;
    }
}