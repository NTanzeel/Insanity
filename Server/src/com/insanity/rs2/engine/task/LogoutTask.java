package com.insanity.rs2.engine.task;

import com.insanity.core.engine.TaskEngine;
import com.insanity.core.engine.task.Task;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.world.World;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class LogoutTask implements Task {

    private Channel channel;

    public LogoutTask(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void execute(TaskEngine context) {
        if (channel.hasAttr(AttributeKey.valueOf("player"))) {
            final Player player = (Player) channel.attr(AttributeKey.valueOf("player")).get();

            if (player != null) {
                World.getInstance().getEntities().deregister(player);
            }
        }
    }
}
