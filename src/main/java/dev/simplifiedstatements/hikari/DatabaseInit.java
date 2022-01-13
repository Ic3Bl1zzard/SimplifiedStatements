package dev.simplifiedstatements.hikari;

import dev.simplifiedstatements.SimplifiedStatements;
import org.bukkit.configuration.Configuration;

public class DatabaseInit extends HikariSetup {

    private static DatabaseInit instance = null;

    public static DatabaseInit getInstance() {
        if (instance == null) {
            instance = new DatabaseInit();
        }

        return instance;
    }

    public void initDatabase() {
        final Configuration configuration = SimplifiedStatements.getInstance().getConfig();
        final String host = configuration.getString("host");
        final int port = configuration.getInt("port");
        final String database = configuration.getString("database");
        final String user = configuration.getString("user");
        final String password = configuration.getString("password");
        final HikariAuthentication authentication = new HikariAuthentication(host, port, database, user, password);
        init(SQLTypes.MYSQL, authentication, 5000, 10);
        initTables();
    }

    private void initTables() {
        String playerDataTable = "CREATE TABLE IF NOT EXISTS GOLDDATA(UUID BINARY(16) NOT NULL, " +
                "NAME VARCHAR(16) NOT NULL, GOLD INT(8) NOT NULL, PRIMARY KEY (UUID))";
        createTable(playerDataTable);
    }
}
