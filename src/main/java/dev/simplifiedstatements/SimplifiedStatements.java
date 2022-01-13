package dev.simplifiedstatements;

import dev.simplifiedstatements.commands.GoldCommand;
import dev.simplifiedstatements.commands.SubCommandRegistry;
import dev.simplifiedstatements.hikari.DatabaseInit;
import dev.simplifiedstatements.listeners.ConnectionListeners;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SimplifiedStatements extends JavaPlugin {

    public void onEnable() {
        registerConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
    }

    public void onDisable() {

    }

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerDatabase() {
        DatabaseInit.getInstance().initDatabase();
    }

    private void registerListeners() {
        addListener(new ConnectionListeners());
    }

    private void registerCommands() {
        getCommand("gold").setExecutor(new GoldCommand());
        SubCommandRegistry.getInstance().registerCommands();
    }

    private void addListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public static SimplifiedStatements getInstance() {
        return JavaPlugin.getPlugin(SimplifiedStatements.class);
    }
}
