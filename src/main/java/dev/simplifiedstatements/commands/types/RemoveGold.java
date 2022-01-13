package dev.simplifiedstatements.commands.types;

import dev.simplifiedstatements.commands.SubCommand;
import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoveGold extends SubCommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "&7Allows you to take from the players gold amount";
    }

    @Override
    public String getSyntax() {
        return "/gold remove <player> <amount>";
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
            int checkingAmount = getGoldUtils().getGold(target) - amount.get();
            if(checkingAmount <= -1){
                MessageUtils.sendFormattedMessage("&cExecution denied! A negative value will be recorded and cannot be less than 0");
                return;
            }
            final String senderMessage = "&7You have taken &6" + amount.get() + " &7gold pieces from &f" + target.getName() + "&7!";
            final String targetMessage = "&6" + amount.get() + " &7gold pieces have been removed from your balance!";
            getGoldUtils().updateAmount(player, target, getGoldUtils().getGold(target) - amount.get(), senderMessage, targetMessage);
        });
    }
}
