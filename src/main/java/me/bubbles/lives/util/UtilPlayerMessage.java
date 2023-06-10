package me.bubbles.lives.util;

import me.bubbles.lives.Lives;
import org.bukkit.entity.Player;

public class UtilPlayerMessage {

    private Player player;
    private Lives plugin;

    public UtilPlayerMessage(Lives plugin, Player player) {
        this.player=player;
        this.plugin=plugin;
    }

    public void sendMessage(String string) {
        player.sendMessage(new UtilString(plugin).colorFillPlaceholders(string));
    }


}
