package me.bubbles.lives;

import me.bubbles.lives.commands.manager.CommandManager;
import me.bubbles.lives.config.ConfigManager;
import me.bubbles.lives.events.manager.EventManager;
import me.bubbles.lives.items.manager.ItemManager;
import me.bubbles.lives.sqilite.Database;
import me.bubbles.lives.sqilite.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lives extends JavaPlugin {

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private ItemManager itemManager;
    private Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager=new ConfigManager(this);
        configManager.addConfig(
                "config.yml",
                "messages.yml"
        );
        itemManager=new ItemManager(this);
        eventManager=new EventManager(this);
        commandManager=new CommandManager(this);
        database=new SQLite(this);
        database.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public int maxLives() {
        return configManager.getConfig("config.yml").getFileConfiguration().getInt("maxLives");
    }

    public int startingLives() {
        return configManager.getConfig("config.yml").getFileConfiguration().getInt("lives");
    }

    public void reload() { // Reload config
        configManager.reloadAll();
        reloadConfig();
    }

    // GETTERS
    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public Database getDatabase() {
        return database;
    }

}
