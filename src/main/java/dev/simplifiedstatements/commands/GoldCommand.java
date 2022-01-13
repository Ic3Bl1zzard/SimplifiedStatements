package dev.simplifiedstatements.commands;

import dev.simplifiedstatements.string.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoldCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof final Player player)) {
            System.out.println("Only players can use this command!");
            return true;
        }

        if (!player.hasPermission("gold.admin")) {
            MessageUtils.sendFormattedMessage("&cYou don't have permission to use this command!", player);
            return true;
        }

        if (args.length > 0) {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.perform(player, args);
                }
            }
        } else {
            for (SubCommand subCommand : SubCommandRegistry.getInstance().getSubCommandList()) {
                MessageUtils.sendFormattedMessage(subCommand.getSyntax() + " - " + subCommand.getDescription(), player);
            }
        }
        return true;
    }
}

