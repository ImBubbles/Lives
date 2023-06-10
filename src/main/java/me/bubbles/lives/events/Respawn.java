package me.bubbles.lives.events;

import me.bubbles.lives.Lives;
import me.bubbles.lives.events.manager.Event;
import me.bubbles.lives.util.UtilString;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn extends Event {

    public Respawn(Lives plugin) {
        super(plugin, PlayerRespawnEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerRespawnEvent e = (PlayerRespawnEvent) event;
        int result = plugin.getDatabase().getLives(e.getPlayer().getUniqueId().toString());
        if(result<=0) {
            e.getPlayer().kickPlayer(new UtilString(plugin).colorFillPlaceholders("%prefix%\n%primary%You do not have anymore lives.\n%secondary%Wait until someone revives you!"));
        }
    }

}
