package me.bubbles.lives.commands.manager;

import me.bubbles.lives.Lives;
import me.bubbles.lives.util.UtilSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Command implements CommandExecutor {

    public Lives plugin;
    public String no_perms;

    private String command;
    private String permission;
    private ArrayList<Argument> arguments = new ArrayList<>();
    public UtilSender utilSender;
    public final int index=0;

    public Command(Lives plugin, String command) {
        this.command=command;
        this.plugin=plugin;
    }

    public void run(CommandSender sender, String[] args) {
        this.utilSender=new UtilSender(plugin,sender);
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args,false);
                    return;
                }
            }
        }
        utilSender.sendMessage(getArgsMessage());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.utilSender=new UtilSender(plugin,sender);
        run(sender,args); // this is so I can use super statements for run
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean permissionCheck() {
        if(permission==null)
            return true;
        if(!utilSender.isPlayer()) {
            return true;
        }
        Player player = utilSender.getPlayer();
        if(!player.hasPermission(permission)) {
            utilSender.sendMessage(no_perms);
            return false;
        }else{
            return true;
        }
    }

    public void setPermission(String permission) {
        String node = plugin.getName().toLowerCase() + "." + permission;
        this.permission=node;
        this.no_perms=plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration().getString("noPerms").replace("%node%",node);
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " Commands:";
        stringBuilder.append(topLine);

        for(Argument arg : arguments) {
            if(arg.getPermission()!=null) {
                if(!utilSender.hasPermission(arg.getPermission())) {
                    continue;
                }
            }
            String command = "\n" + "%primary%" + "/" + getCommand() + "%secondary%" + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    public String getPermission() {
        return permission;
    }

}
