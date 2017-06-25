package com.insanity.rs2.model.player;

import com.insanity.rs2.config.Interface;
import com.insanity.rs2.net.packets.Builder;
import com.insanity.rs2.net.packets.Type;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 25/06/2017.
 */
public class ActionSender {

    private Player player;

    public ActionSender(Player player) {
        this.player = player;
    }

    public ActionSender sendDetails() {
        player.write(new Builder(249).writeByteA(player.isMember() ? 1 : 0).writeLEShortA(player.getIndex()).toPacket());
        player.write(new Builder(107).toPacket());
        return this;
    }

    /**
     * Sends a message.
     *
     * @param message The message to send.
     * @return The action sender instance, for chaining.
     */
    public ActionSender sendMessage(String message) {
        player.write(new Builder(253, Type.VARIABLE).writeString(message).toPacket());
        return this;
    }

    /**
     * Sends the map region load command.
     *
     * @return The action sender instance, for chaining.
     */
    public ActionSender sendMapRegion() {
        player.setLastKnownLocation(player.getLocation());
        player.write(new Builder(73).writeShortA(player.getLocation().getRegionX() + 6).writeShort(player.getLocation().getRegionY() + 6).toPacket());
        return this;
    }

    /**
     * Sends all the sidebar interfaces.
     *
     * @return The action sender instance, for chaining.
     */
    public ActionSender sendSidebarInterfaces() {
        for (int i = 0; i < Interface.SIDEBAR_INTERFACES.length; i++) {
            sendSidebarInterface(Interface.SIDEBAR_ICONS[i], Interface.SIDEBAR_INTERFACES[i]);
        }
        return this;
    }

    /**
     * Sends a specific sidebar interface.
     *
     * @param icon        The sidebar icon.
     * @param interfaceId The interface id.
     * @return The action sender instance, for chaining.
     */
    public ActionSender sendSidebarInterface(int icon, int interfaceId) {
        player.write(new Builder(71).writeShort(interfaceId).writeByteA(icon).toPacket());
        return this;
    }
}
