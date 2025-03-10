package com.github.ebjerke04.myrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.classes.ClassSelectionMenu;

public class ClassCommand extends BaseCommand {

    public ClassCommand() {
        super("class");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ClassSelectionMenu.openClassSelectionMenu(player);
            }
        }

        return true;
    }

}
