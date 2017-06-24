package com.insanity.rs2.model.player.loader;

import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.model.player.Player;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class XMLPlayerLoader implements PlayerLoader {

    @Override
    public LoginResult checkLogin(Details details) {
        return null;
    }

    @Override
    public boolean loadPlayer(Player player) {
        return false;
    }

    @Override
    public boolean savePlayer(Player player) {
        return false;
    }
}
