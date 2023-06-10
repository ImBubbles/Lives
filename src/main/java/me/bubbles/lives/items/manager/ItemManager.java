package me.bubbles.lives.items.manager;

import me.bubbles.lives.Lives;
import me.bubbles.lives.items.LifeBook;
import me.bubbles.lives.items.ReviveBook;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.HashSet;

public class ItemManager {

    private Lives plugin;
    private HashSet<Item> items;

    public ItemManager(Lives plugin) {
        this.plugin = plugin;
        items=new HashSet<>();
        registerItem(
                new LifeBook(plugin),
                new ReviveBook(plugin,this)
        );
    }

    private void registerItem(Item... items) {
        for(Item item : items) {
            this.items.add(item);
            item.onRegister();
            if(item.getRecipe()!=null) {
                Bukkit.addRecipe(item.getRecipe());
            }
        }
    }

    public Item getItemByName(String string) {
        for(Item item : items) {
            if(item.getNBTIdentifier().equalsIgnoreCase(string)) {
                return item;
            }
        }
        return null;
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public boolean onEvent(Event event) {
        boolean result=false;
        for(Item item : items) {
            if(item.onEvent(event)) {
                result=true;
            }
        }
        return result;
    }

}
