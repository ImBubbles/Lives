package me.bubbles.lives.events.manager;

import me.bubbles.lives.Lives;
import org.bukkit.event.Listener;

public class Event implements Listener {

    public Lives plugin;
    private Class event;

    public Event(Lives plugin, Class event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void onEvent(org.bukkit.event.Event event) {

    }

    public Class getEvent() {
        return event;
    }

}
