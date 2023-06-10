package me.bubbles.lives.events.manager;

import me.bubbles.lives.Lives;
import me.bubbles.lives.events.Death;
import me.bubbles.lives.events.Login;
import me.bubbles.lives.events.Respawn;

import java.util.Collections;
import java.util.HashSet;

public class EventManager {

    private Lives plugin;
    private HashSet<Event> events = new HashSet<>();

    public EventManager(Lives plugin) {
        this.plugin=plugin;
        registerListener(); // REGISTER EVENT LISTENERS
        Collections.addAll(
                this.events, // REGISTER EVENT HANDLERS
                new Login(plugin),
                new Death(plugin),
                new Respawn(plugin)
        );
    }

    public void onEvent(org.bukkit.event.Event event) {
        if(plugin.getItemManager().onEvent(event)) {
            return;
        }
        for(Event e : events) {
            if(e.getEvent().equals(event.getClass())) {
                e.onEvent(event);
            }
        }
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(new Listeners(this),plugin);
    }

}
