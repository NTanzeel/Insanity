package com.insanity.rs2.engine.task.impl;

import com.insanity.rs2.engine.Engine;
import com.insanity.rs2.engine.task.Task;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.world.World;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 25/06/2017.
 */
public class LoginTask implements Task {

    private Player player;

    public LoginTask(Player player) {
        this.player = player;
    }

    @Override
    public void execute(Engine context) {
        World.getWorld().getEntities().register(player);
    }
}
