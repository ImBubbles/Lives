package me.bubbles.lives.commands.base;

import me.bubbles.lives.Lives;
import me.bubbles.lives.commands.manager.Argument;
import me.bubbles.lives.util.UtilSender;
import me.bubbles.lives.util.UtilUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class LivesArg extends Argument {

    public LivesArg(Lives plugin, int index) {
        super(plugin, "lives", "lives <player> <set/give/remove> <amount>", index);
        setPermission("lives");
        setAlias("lives");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length==relativeIndex) { // SEND NO ARGS
            if(!utilSender.isPlayer()) {
                utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
                return;
            }
            utilSender.sendMessage("%prefix% %primary%You have %secondary%"+utilSender.getUtilUser().getLives()+"%primary% lives.");
            return;
        }
        // lives <player> <set> <amt>
        List<String> ignores = Arrays.asList("set","give","remove");
        Player player = Bukkit.getPlayer(args[relativeIndex]);
        if(args.length==relativeIndex+1) { // ONLY ONE ARG
            if(player==null) {
                utilSender.sendMessage("%prefix% %primary%User not found.");
                return;
            }
            utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"%primary% has %secondary%"+new UtilUser(plugin,player).getLives()+"%primary% lives.");
            return;
        }
        if(!utilSender.hasPermission("lives.admin")) {
            utilSender.sendMessage(plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration().getString("noPerms").replace("%node%","lives.admin"));
            return;
        }
        if(args.length!=relativeIndex+3) {
            utilSender.sendMessage("%prefix% %secondary%"+getDisplay());
            return;
        }
        if(!ignores.contains(args[relativeIndex+1])) {
            utilSender.sendMessage("%prefix% %secondary%"+getDisplay());
            return;
        }
        if(player==null) {
            utilSender.sendMessage("%prefix% %primary%User not found.");
            return;
        }
        args(utilSender, player, args[relativeIndex+1], args[relativeIndex+2]);
    }

    public void args(UtilSender utilSender, Player player, String arg, String amount) {
        boolean result=false;
        switch(arg) {
            case "set":
                result=set(utilSender,player,amount);
                break;
            case "give":
                result=give(utilSender,player,amount);
                break;
            case "remove":
                result=remove(utilSender,player,amount);
                break;
        }
        if(result) {
            utilSender.sendMessage("%prefix% %primary%Player has been set to %secondary%"+new UtilUser(plugin,player).getLives()+"%primary%.");
        }
    }

    private boolean set(UtilSender utilSender, Player player, String arg) {
        int amount = Integer.parseInt(arg);
        if(amount>plugin.maxLives()||amount<0) {
            utilSender.sendMessage("%prefix% %primary%Must pick a value between %secondary%"+1+"%primary% and%secondary% "+plugin.maxLives()+"%primary%.");
            return false;
        }
        plugin.getDatabase().setLives(player.getUniqueId().toString(),amount);
        return true;
    }

    private boolean give(UtilSender utilSender, Player player, String arg) {
        int amount = Integer.parseInt(arg);
        UtilUser utilUser = new UtilUser(plugin,player);
        if(amount>=plugin.maxLives()) {
            utilSender.sendMessage("%prefix% %primary%This player cannot receive any more lives.");
            return false;
        }
        if(amount>=plugin.maxLives()-utilUser.getLives()||amount<=0) {
            utilSender.sendMessage("%prefix% %primary%Must pick a value between %secondary%"+1+"%primary% and%secondary% "+(plugin.maxLives()-utilUser.getLives())+"%primary%.");
            return false;
        }
        plugin.getDatabase().setLives(player.getUniqueId().toString(),utilUser.getLives()+amount);
        return true;
    }

    private boolean remove(UtilSender utilSender, Player player, String arg) {
        int amount = Integer.parseInt(arg);
        UtilUser utilUser = new UtilUser(plugin,player);
        if(utilUser.getLives()==0) {
            utilSender.sendMessage("%prefix% %primary%This cannot lose anymore lives.");
            return false;
        }
        if(utilUser.getLives()-amount>=0||amount<=0) {
            utilSender.sendMessage("%prefix% %primary%Must pick a value between %secondary%"+1+"%primary% and%secondary% "+(plugin.maxLives()-utilUser.getLives())+"%primary%.");
            return false;
        }
        plugin.getDatabase().setLives(player.getUniqueId().toString(),utilUser.getLives()-amount);
        return true;
    }

}
