package com.insanity.rs2.world.entities;

import com.insanity.rs2.config.Constants;
import com.insanity.rs2.model.npc.NPC;
import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.model.player.Player;
import com.insanity.rs2.model.player.loader.PlayerLoader;
import com.insanity.rs2.model.player.loader.SQLPlayerLoader;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Entities {

    private PlayerLoader playerLoader = new SQLPlayerLoader();

    private EntityList<NPC> npcs = new EntityList<>(Constants.MAX_NPCS);

    private EntityList<Player> players = new EntityList<>(Constants.MAX_PLAYERS);

    public void load(Details details) {

    }

    public EntityList<NPC> getNpcs() {
        return npcs;
    }

    public EntityList<Player> getPlayers() {
        return players;
    }
}
