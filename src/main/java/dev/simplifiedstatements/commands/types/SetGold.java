package dev.simplifiedstatements.commands.types;

import dev.simplifiedstatements.commands.SubCommand;
import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class SetGold extends SubCommand {

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "&7Allows you to set the players gold amount";
    }

    @Override
    public String getSyntax() {
        return "/gold set <player> <amount>";
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
            final String senderMessage = "&7You have successfully set &f" + target.getName() + "'s &7gold to &6" + amount.get() + "&7!";
            final String targetMessage = "&7Your gold was set to &6" + amount.get() + " &7gold pieces!";
            getGoldUtils().updateAmount(player, target, amount.get(), senderMessage, targetMessage);
        });
    }
}
