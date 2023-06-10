package me.bubbles.lives.items;

import me.bubbles.lives.Lives;
import me.bubbles.lives.items.manager.Item;
import me.bubbles.lives.util.UtilPlayerMessage;
import me.bubbles.lives.util.UtilUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LifeBook extends Item {

    public LifeBook(Lives plugin) {
        super(plugin, Material.BOOK, "lifeBook");
        ItemStack itemStack = nmsAsItemStack();
        FileConfiguration fileConfiguration = plugin.getConfigManager().getConfig("config.yml").getFileConfiguration();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                fileConfiguration.getString("lifeBook.name")
        ));
        List<String> lore = fileConfiguration.getStringList("lifeBook.lore");
        for(int i=0; i<lore.size(); i++) {
            lore.set(i,ChatColor.translateAlternateColorCodes('&',lore.get(i)));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);

    }

    @Override
    public void onRegister() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,getNBTIdentifier()),nmsAsItemStack());
        recipe.shape(
                "TGT",
                "NDN",
                "TGT"
        );
        recipe.setIngredient('T',Material.TOTEM_OF_UNDYING)
                .setIngredient('G',Material.GOLD_BLOCK)
                .setIngredient('N',Material.NETHERITE_INGOT)
                .setIngredient('D',Material.DIAMOND_BLOCK);
        setRecipe(recipe);
    }

    @Override
    public boolean onEvent(Event event) {
        if(event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if(!equals(e.getItem())) {
                return false;
            }
            if(!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR))) {
                return false;
            }
            UtilUser utilUser = new UtilUser(plugin,e.getPlayer());
            if(utilUser.getLives()>=plugin.maxLives()) {
                e.setCancelled(true);
                utilUser.sendMessage("%prefix% %primary%You cannot redeem anymore lives.");
                return true;
            }
            utilUser.setLives(utilUser.getLives()+1);
            e.getPlayer().getInventory().remove(e.getItem());
            utilUser.sendMessage("%prefix% %primary%You redeemed a Life Book.");
        }
        return false;
    }

}
