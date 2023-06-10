package me.bubbles.lives.events;

import me.bubbles.lives.Lives;
import me.bubbles.lives.events.manager.Event;
import me.bubbles.lives.util.UtilString;
import me.bubbles.lives.util.UtilUser;
import org.bukkit.event.player.PlayerLoginEvent;

public class Login extends Event {

    public Login(Lives plugin) {
        super(plugin, PlayerLoginEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {

        PlayerLoginEvent e = (PlayerLoginEvent) event;

        if(new UtilUser(plugin,e.getPlayer()).getLives()==-1) {
            plugin.getDatabase().setLives(e.getPlayer().getUniqueId().toString(), plugin.startingLives());
            return;
        }

        if(plugin.getDatabase().getLives(e.getPlayer().getUniqueId().toString())<=0) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED,new UtilString(plugin).colorFillPlaceholders("%prefix%\n%primary%You do not have anymore lives.\n%secondary%Wait until someone revives you!"));
            return;
        }

        e.allow();

    }

}