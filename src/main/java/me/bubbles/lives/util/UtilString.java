package me.bubbles.lives.util;

import me.bubbles.lives.Lives;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class UtilString {

    private Lives plugin;

    public UtilString(Lives plugin) {
        this.plugin=plugin;
    }

    public String colorFillPlaceholders(String message) {
        FileConfiguration config = plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration();
        for(String string : config.getKeys(false)) {
            String regex = "%"+string+"%";
            String replacement = config.getString(string);
            if(replacement!=null) {
                message = message.replace(regex,replacement);
            }
        }
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
