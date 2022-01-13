package dev.simplifiedstatements.hikari;

import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GoldUtils {

    private static GoldUtils instance = null;
    private final SQLUtils sqlUtils = SQLUtils.getInstance();

    public static GoldUtils getInstance() {
        if (instance == null) instance = new GoldUtils();
        return instance;
    }

    public boolean check(Player player) {
        AtomicBoolean fetchedValue = new AtomicBoolean();
        sqlUtils.executeQuery("SELECT * FROM GOLDDATA WHERE UUID =?", ps ->
                ps.setBinaryStream(1, DatabaseUUIDTool.convertUniqueId(player.getUniqueId())), rs -> {
            if (rs.next()) {
                fetchedValue.set(true);
            }
            return rs;
        });

        return fetchedValue.get();
    }


    public int getGold(Player player) {
        final AtomicInteger amount = new AtomicInteger();

        sqlUtils.executeQuery("SELECT * FROM GOLDDATA WHERE UUID =?", ps ->
                ps.setBinaryStream(1, DatabaseUUIDTool.convertUniqueId(player.getUniqueId())), rs -> {
            if (rs.next()) {
                amount.set(rs.getInt("GOLD"));
            }

            return rs;
        });

        return amount.get();
    }

    public void updateAmount(Player sender, Player target, int amount, String senderMessage, String targetMessage) {
        if (check(target)) {
            sqlUtils.executeUpdate("UPDATE GOLDDATA SET GOLD=? WHERE UUID=?", ps -> {
                ps.setInt(1, amount);
                ps.setBinaryStream(2, DatabaseUUIDTool.convertUniqueId(target.getUniqueId()));
                MessageUtils.sendFormattedMessage(senderMessage, sender);
                MessageUtils.sendFormattedMessage(targetMessage, target);
            });
        }
    }
}
