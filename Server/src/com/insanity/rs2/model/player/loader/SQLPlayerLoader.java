package com.insanity.rs2.model.player.loader;

import com.insanity.io.MySQL;
import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.model.player.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class SQLPlayerLoader implements PlayerLoader {

    private MySQL database;

    public SQLPlayerLoader(MySQL database) {
        this.database = database;
    }

    @Override
    public LoginResult checkLogin(Details details) {
        int code = 2;
        Player player = null;

        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";
        PreparedStatement statement = this.database.prepareStatement(query);

        try {
            statement.setString(1, details.getUsername());
            ResultSet resultSet = this.database.query(statement);

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                if (!resultSet.getString("password").equals(details.getPassword())) {
                    code = 3;
                }
            }

            statement.close();
        } catch (Exception e) {
            code = 11;
        }

        if (code == 2) {
            player = new Player(details);
        }

        return new LoginResult(code, player);
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
