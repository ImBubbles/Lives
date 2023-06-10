package me.bubbles.lives.events;

import me.bubbles.lives.Lives;
import me.bubbles.lives.events.manager.Event;
import me.bubbles.lives.util.UtilString;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death extends Event {

    public Death(Lives plugin) {
        super(plugin, PlayerDeathEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerDeathEvent e = (PlayerDeathEvent) event;
        int result = plugin.getDatabase().getLives(e.getEntity().getUniqueId().toString())-1;
        plugin.getDatabase().setLives(e.getEntity().getUniqueId().toString(),result);
        e.getEntity().sendMessage(new UtilString(plugin).colorFillPlaceholders("%prefix% %primary%You have lost a life..."));
    }

}
