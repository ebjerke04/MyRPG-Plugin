package com.github.ebjerke04.myrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.players.RpgPlayer;

import net.kyori.adventure.text.Component;

public class RpgAddExperienceCommand extends BaseCommand {
 
    public RpgAddExperienceCommand() {
		super("rpgexp");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals(commandName)) {
            if (sender instanceof Player) {
               if (args.length == 2) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("add")) {
                    try {
                        int expToAdd = Integer.parseInt(args[1]);
                        RpgPlayer rpgPlayer = Plugin.getPlayerManager().getRpgPlayer(player.getUniqueId());
                        rpgPlayer.rewardExperience(expToAdd);
                    } catch (NumberFormatException e) {
                        player.sendMessage(Component.text("Experience amount must be an integer!"));
                    }
                }
               }
            }
        }

        return false;
    }

}
