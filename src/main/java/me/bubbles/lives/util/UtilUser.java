package me.bubbles.lives.util;

import me.bubbles.lives.Lives;
import org.bukkit.entity.Player;

public class UtilUser {

    private Player player;
    private Lives plugin;

    public UtilUser(Lives plugin, Player player) {
        this.plugin=plugin;
        this.player=player;
    }

    public int getLives() {
        return plugin.getDatabase().getLives(player.getUniqueId().toString());
    }

    public void setLives(int lives) {
        plugin.getDatabase().setLives(player.getUniqueId().toString(),lives);
    }

    public void sendMessage(String string) {
        new UtilPlayerMessage(plugin,player).sendMessage(string);
    }

}
