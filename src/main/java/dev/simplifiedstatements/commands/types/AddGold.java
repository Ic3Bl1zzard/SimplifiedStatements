package dev.simplifiedstatements.commands.types;

import dev.simplifiedstatements.commands.SubCommand;
import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class AddGold extends SubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "&7Allows you to add to the players gold amount";
    }

    @Override
    public String getSyntax() {
        return "/gold add <player> <amount>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 2) return;

        final Player player = (Player) sender;
        final Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            MessageUtils.sendFormattedMessage("&cThat player doesn't exist", player);
            return;
        }
        final AtomicInteger amount = new AtomicInteger(0);
        try {
            amount.set(Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        CompletableFuture.runAsync(() -> {
            final String senderMessage = "&7You have gave &f" + target.getName() + " &6" + amount.get() + " &7gold pieces";
            final String targetMessage = "&7You were given &6" + amount.get() + " &7gold pieces!";
            getGoldUtils().updateAmount(player, target, amount.get() + getGoldUtils().getGold(target), senderMessage, targetMessage);
        });
    }
}
