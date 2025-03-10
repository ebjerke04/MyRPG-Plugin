package com.github.ebjerke04.myrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.ebjerke04.myrpg.Plugin;

public abstract class BaseCommand implements CommandExecutor {
	
	protected String commandName;
	
	public BaseCommand(String commandName) {
		this.commandName = commandName;
		Plugin.get().getCommand(commandName).setExecutor(this);
	}
	
	@Override
	public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

}
