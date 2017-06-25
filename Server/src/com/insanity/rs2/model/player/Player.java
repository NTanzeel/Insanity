package com.insanity.rs2.model.player;

import com.insanity.rs2.model.entity.Entity;
import com.insanity.rs2.net.packets.Packet;
import com.insanity.rs2.net.security.ISAACCipher;
import com.insanity.rs2.world.region.Region;
import io.netty.channel.Channel;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
@SuppressWarnings({"all"})
public class Player extends Entity {

    /*
     * Login Details
     */

    private int uid;

    private String name;

    private String password;

    private Rights rights = Rights.PLAYER;

    private boolean member = true;


    /*
     * Session Attributes
     */

    private Channel channel;

    private boolean active = false;

    private ISAACCipher inCipher;

    private ISAACCipher outCipher;

    private ActionSender actionSender = new ActionSender(this);

    private Queue<Packet> queuedPackets = new LinkedList<>();

    public Player(Details details) {
        this.name = details.getUsername();
        this.password = details.getPassword();
        this.uid = details.getUID();
        this.channel = details.getChannel();
        this.inCipher = details.getInCipher();
        this.outCipher = details.getOutCipher();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getUID() {
        return uid;
    }

    public Channel getChannel() {
        return channel;
    }

    public Rights getRights() {
        return rights;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean isActive() {
        synchronized (this) {
            return active;
        }
    }

    public void setActive(boolean active) {
        synchronized (this) {
            this.active = active;
        }
    }

    public ISAACCipher getInCipher() {
        return inCipher;
    }

    public ISAACCipher getOutCipher() {
        return outCipher;
    }

    public ActionSender getActionSender() {
        return actionSender;
    }

    public void initialize() {
        this.setActive(true);
        actionSender.sendDetails();

        actionSender.sendMapRegion();
        actionSender.sendSidebarInterfaces();

        actionSender.sendMessage("Welcome to RuneScape");
    }

    @Override
    public void addToRegion(Region region) {
        region.addPlayer(this);
    }

    @Override
    public void removeFromRegion(Region region) {
        region.removePlayer(this);
    }

    public void write(Packet packet) {
        synchronized (this) {
            queuedPackets.add(packet);
            if (active) {
                for (Packet p : queuedPackets) {
                    channel.writeAndFlush(packet);
                }
                queuedPackets.clear();
            }
        }
    }
}
