package dev.simplifiedstatements.commands.types;

import dev.simplifiedstatements.commands.SubCommand;
import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class GetGold extends SubCommand {

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getDescription() {
        return "&7Allows you to get your balance!";
    }

    @Override
    public String getSyntax() {
        return "/gold get";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        CompletableFuture.runAsync(() -> MessageUtils.sendFormattedMessage("&7Current Gold Balance: &6" + getGoldUtils().getGold(player), player));
    }
}
