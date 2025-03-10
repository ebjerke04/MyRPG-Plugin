package com.github.ebjerke04.myrpg.commands;

public class CommandManager {

	public static void registerCommands() {
		new RpgAdminCommand();
		new ClassCommand();
	}
	
}
