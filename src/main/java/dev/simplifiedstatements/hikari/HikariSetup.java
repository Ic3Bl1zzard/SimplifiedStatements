package dev.simplifiedstatements.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class HikariSetup {

    private HikariDataSource dataSource;

    public HikariSetup init(SQLTypes types, HikariAuthentication authentication, int timeOut, int poolSize) {
        this.dataSource = new HikariDataSource(getDataProperties(types, authentication, timeOut, poolSize));
        return this;
    }

    public HikariConfig getDataProperties(SQLTypes types, HikariAuthentication authentication, int timeOut, int poolSize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(types.getDriverName());
        config.setUsername(authentication.user());
        config.setPassword(authentication.password());
        config.setJdbcUrl(generateURL(types.getDriverURL(), authentication));
        config.setConnectionTimeout(timeOut);
        config.setMaximumPoolSize(poolSize);
        return config;
    }

    public String generateURL(String jdurl, HikariAuthentication authentication) {
        String url = jdurl.replace("{host}", authentication.host());
        url = url.replace("{port}", String.valueOf(authentication.port()));
        url = url.replace("{database}", authentication.database());
        return url;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void close() {
        if (dataSource == null) return;
        if (isClosed()) return;
        dataSource.close();
    }

    private boolean isClosed() {
        return dataSource.isClosed();
    }


    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected HikariSetup createTable(String tableOutput) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(tableOutput)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }
}
