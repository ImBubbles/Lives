package me.bubbles.lives.commands.base;

import me.bubbles.lives.Lives;
import me.bubbles.lives.commands.manager.Argument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ReviveArg extends Argument {

    public ReviveArg(Lives plugin, int index) {
        super(plugin, "revive", "revive <player>", index);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(args.length==relativeIndex) { // NO ARGS
            utilSender.sendMessage("%prefix% %secondary%"+getDisplay());
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[relativeIndex]);
        String uuid = offlinePlayer.getUniqueId().toString();
        if(uuid==null||plugin.getDatabase().getLives(uuid)==-1) {
            utilSender.sendMessage(
                    "%prefix% %primary%Could not find player%secondary% "+args[relativeIndex]+"%primary%."
            );
            return;
        }
        if(plugin.getDatabase().getLives(uuid)>0) {
            utilSender.sendMessage(
                    "%prefix% %secondary%Player cannot be revived."
            );
            return;
        }
        plugin.getDatabase().setLives(uuid, plugin.startingLives());
        utilSender.sendMessage("%prefix% %primary%Player%secondary% "+args[relativeIndex]+"%primary% has been revived.");
    }

}
