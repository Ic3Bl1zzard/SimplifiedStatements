package dev.simplifiedstatements.listeners;

import dev.simplifiedstatements.hikari.DatabaseUUIDTool;
import dev.simplifiedstatements.hikari.SQLUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

public class ConnectionListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final SQLUtils instance = SQLUtils.getInstance();
        final String insertQuery = "INSERT INTO GOLDDATA(UUID,NAME,GOLD) VALUES(?,?,?)";
        CompletableFuture.runAsync(() -> instance.executeQuery("SELECT * FROM GOLDDATA WHERE UUID =?", ps ->
                ps.setBinaryStream(1, DatabaseUUIDTool.convertUniqueId(player.getUniqueId())), rs -> {
            if (!rs.next()) {
                instance.executeUpdate(insertQuery, statement -> {
                    statement.setBinaryStream(1, DatabaseUUIDTool.convertUniqueId(player.getUniqueId()));
                    statement.setString(2, player.getName());
                    statement.setInt(3, 0);
                    System.out.println("Player Inserted!");
                });
            }
            return rs.next();
        })).whenComplete((unused, throwable) -> System.out.println("Execution Complete!"));
    }
}
