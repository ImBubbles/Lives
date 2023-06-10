package me.bubbles.lives.util;

import me.bubbles.lives.Lives;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilSender {

    private CommandSender sender;
    private Lives plugin;

    public UtilSender(Lives plugin, CommandSender sender) {
        this.plugin=plugin;
        this.sender=sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void sendMessage(String message) {
        if(isPlayer()) {
            getPlayer().sendMessage(new UtilString(plugin).colorFillPlaceholders(message));
            return;
        }
        plugin.getLogger().info("\n"+new UtilString(plugin).colorFillPlaceholders(message));
    }

    public boolean hasPermission(String permission) {
        if(!isPlayer()) {
            return true;
        }
        return getPlayer().hasPermission(permission);
    }

    public Player getPlayer() {
        if(!isPlayer()){
            return null;
        }
        return (Player) sender;
    }

    public UtilUser getUtilUser() {
        return new UtilUser(plugin,getPlayer());
    }

}
