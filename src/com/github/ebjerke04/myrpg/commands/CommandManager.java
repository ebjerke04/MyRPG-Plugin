package com.github.ebjerke04.myrpg.commands;

public class CommandManager {

	public static void registerCommands() {
		synchronized (CommandManager.class) {
			new RpgAdminCommand();
			new ClassCommand();
			new TestCommand();
		}
	}
	
}
