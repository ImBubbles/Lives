package me.bubbles.lives.commands.base;

import me.bubbles.lives.Lives;
import me.bubbles.lives.commands.manager.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand extends Command {

    public BaseCommand(Lives plugin) {
        super(plugin,"lively");
        addArguments(
            new ItemArg(plugin,index),
            new LivesArg(plugin,index),
            new ReviveArg(plugin,index),
            new ReloadArg(plugin,index)
        );
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
    }

}
