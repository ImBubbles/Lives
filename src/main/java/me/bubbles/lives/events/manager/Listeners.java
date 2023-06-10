package me.bubbles.lives.events.manager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;

public class Listeners implements Listener {

    private EventManager eventManager;

    public Listeners(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        eventManager.onEvent(e);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        eventManager.onEvent(e);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        eventManager.onEvent(e);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        eventManager.onEvent(e);
    }

    @EventHandler
    public void onEditBook(PlayerEditBookEvent e) {
        eventManager.onEvent(e);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        eventManager.onEvent(e);
    }

}
