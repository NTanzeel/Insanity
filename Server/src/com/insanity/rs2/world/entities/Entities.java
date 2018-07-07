package com.insanity.rs2.world.entities;

import com.insanity.Insanity;
import com.insanity.rs2.config.Constants;
import com.insanity.rs2.engine.task.LoginTask;
import com.insanity.rs2.model.npc.NPC;
import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.model.player.loader.PlayerLoader;
import com.insanity.rs2.model.player.loader.PlayerLoader.LoginResult;
import com.insanity.rs2.model.player.loader.SQLPlayerLoader;
import com.insanity.rs2.net.packets.Builder;
import com.insanity.rs2.net.packets.Packet;
import com.insanity.rs2.util.NameUtility;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.AttributeKey;

import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Entities {

    private Logger logger = Logger.getLogger(Entities.class.getName());

    private PlayerLoader playerLoader = new SQLPlayerLoader(Insanity.getInstance().getDatabase());

    private EntityList<NPC> npcs = new EntityList<>(Constants.MAX_NPCS);

    private EntityList<Player> players = new EntityList<>(Constants.MAX_PLAYERS);

    public EntityList<NPC> getNpcs() {
        return npcs;
    }

    public EntityList<Player> getPlayers() {
        return players;
    }

    public boolean isPlayerOnline(String name) {
        name = NameUtility.formatName(name);
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void load(final Details details) {
        Insanity.getInstance().getEngine().submitWork(new Runnable() {
            @Override
            public void run() {
                LoginResult loginResult = playerLoader.checkLogin(details);
                int returnCode = loginResult.getReturnCode();

                if (returnCode == 2) {
                    if (!NameUtility.isValidName(details.getUsername())) {
                        returnCode = 11;
                    } else if (isPlayerOnline(loginResult.getPlayer().getName())) {
                        returnCode = 5;
                    } else if (players.capacity() == 0) {
                        returnCode = 7;
                    }
                }

                if (returnCode != 2) {
                    Packet packet = new Builder().writeByte((byte) returnCode).toPacket();
                    details.getChannel().writeAndFlush(packet).addListener(ChannelFutureListener.CLOSE);
                } else {
                    playerLoader.loadPlayer(loginResult.getPlayer());
                    details.getChannel().attr(AttributeKey.valueOf("player")).set(loginResult.getPlayer());
                    Insanity.getInstance().getEngine().pushTask(new LoginTask(loginResult.getPlayer()));
                }
            }
        });
    }

    public void register(final Player player) {
        final int returnCode = players.add(player) ? 2 : 7;
        Builder builder = new Builder().writeByte((byte) returnCode).writeByte((byte) player.getRights().toInteger()).writeByte((byte) 0);
        player.getChannel().writeAndFlush(builder.toPacket()).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (returnCode != 2) {
                    player.getChannel().close();
                } else {
                    player.initialize();
                    logger.info("Registered player : " + player + " [online=" + players.size() + "]");
                }
            }
        });
    }

    public void deregister(final Player player) {
        player.destroy();
        player.getChannel().close();
        players.remove(player);
        logger.info("Unregistered player : " + player + " [online=" + players.size() + "]");
    }
}
