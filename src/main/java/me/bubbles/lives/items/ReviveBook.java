package me.bubbles.lives.items;

import me.bubbles.lives.Lives;
import me.bubbles.lives.items.manager.Item;
import me.bubbles.lives.items.manager.ItemManager;
import me.bubbles.lives.util.UtilUser;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ReviveBook extends Item {

    private ItemManager itemManager;

    public ReviveBook(Lives plugin, ItemManager itemManager) {
        super(plugin, Material.WRITABLE_BOOK, "reviveBook");
        ItemStack itemStack = nmsAsItemStack();
        FileConfiguration fileConfiguration = plugin.getConfigManager().getConfig("config.yml").getFileConfiguration();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                fileConfiguration.getString("reviveBook.name")
        ));
        List<String> lore = fileConfiguration.getStringList("reviveBook.lore");
        for(int i=0; i<lore.size(); i++) {
            lore.set(i,ChatColor.translateAlternateColorCodes('&',lore.get(i)));
        }
        itemMeta.setLore(lore);
        BookMeta bookMeta = (BookMeta) itemMeta;
        bookMeta.setPages("To revive a player, sign this book and title it the player's name.");
        itemStack.setItemMeta(bookMeta);
        setNMSStack(itemStack);
        this.itemManager=itemManager;
    }

    @Override
    public void onRegister() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,getNBTIdentifier()),nmsAsItemStack());
        recipe.shape(
                " E ",
                "RRR"
        );
        recipe.setIngredient('E',Material.ELYTRA)
                .setIngredient('R',new RecipeChoice.ExactChoice(itemManager.getItemByName("lifeBook").nmsAsItemStack()));
        setRecipe(recipe);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onEvent(Event event) {
        if(event instanceof PlayerEditBookEvent) {
            PlayerEditBookEvent e = (PlayerEditBookEvent) event;
            UtilUser utilUser = new UtilUser(plugin,e.getPlayer());
            ItemStack itemStack = e.getPlayer().getInventory().getItem(e.getSlot());
            if(!equals(itemStack)) {
                return false;
            }
            if(!e.isSigning()) {
                itemStack.setItemMeta(e.getPreviousBookMeta());
                e.getPlayer().getInventory().setItem(e.getSlot(),itemStack);
                e.setCancelled(true);
                return false;
            }
            if(e.getNewBookMeta().getTitle()==null) {
                e.setCancelled(true);
                utilUser.sendMessage("%prefix% %primary% Please enter a player's name.");
                return true;
            }
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(e.getNewBookMeta().getTitle());
            String uuid = offlinePlayer.getUniqueId().toString();
            if(uuid==null||plugin.getDatabase().getLives(uuid)==-1) {
                e.setCancelled(true);
                utilUser.sendMessage("%prefix% %secondary%"+e.getNewBookMeta().getTitle()+"%primary% not found.");
                return true;
            }
            if(plugin.getDatabase().getLives(uuid)>0) {
                e.setCancelled(true);
                utilUser.sendMessage("%prefix% %secondary%This player cannot be revived!");
                return true;
            }
            plugin.getDatabase().setLives(uuid, plugin.startingLives());
            utilUser.sendMessage("%prefix% %secondary%"+e.getNewBookMeta().getTitle()+"%primary% has been revived!");
            e.getPlayer().getInventory().remove(itemStack);
            return true;
        }
        return false;
    }

}
