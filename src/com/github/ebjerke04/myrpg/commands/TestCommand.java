package com.github.ebjerke04.myrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.Plugin;

public class TestCommand extends BaseCommand {

    public TestCommand() {
		super("testcmd");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals(commandName)) {
            if (sender instanceof Player) {
                if (args.length != 1) return false;

                Player player = (Player) sender;
                Plugin.getWorldManager().testCustomMob(player, args[0]);
            }
        }

        return false;
    }

}
