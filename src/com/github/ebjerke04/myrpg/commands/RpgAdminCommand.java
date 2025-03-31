package com.github.ebjerke04.myrpg.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.creation.RpgCreationManager;
import com.github.ebjerke04.myrpg.data.QuestDataManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

public class RpgAdminCommand extends BaseCommand {
	
	private final BungeeComponentSerializer serializer = BungeeComponentSerializer.get();
	
	public RpgAdminCommand() {
		super("rpgadmin");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(commandName)) {
			if (!(sender instanceof Player)) {
				Plugin.getAdventure().console().sendMessage(Component.text("Please run this command as a player within the server!")
						.color(TextColor.color(0xFF0000)));
				return false;
			}
			Player player = (Player) sender;
			
			if (!player.hasPermission("rpg.admin")) {
				Plugin.getAdventure().player(player).sendMessage(Component.text("You do not have permission to use this command!")
						.color(TextColor.color(0xFF0000)));
				return false;
			}
			
			if (args.length == 0) {
				sendComponent(player, createTitleItem("Settings"));
				sendComponent(player, createMenuItem(1, "Classes", "modify class data", "/rpgadmin class"));
				sendComponent(player, createMenuItem(2, "Quests", "modify quest data", "/rpgadmin quest"));
				sendComponent(player, createMenuItem(3, "Weapons", "modify weapon data", "/rpgadmin weapon"));
				
				return true;
			}
			
			if (args.length >= 1) {
				// CONFIRM COMMAND //
				if (args[0].equalsIgnoreCase("confirm") && args.length == 1) {
					RpgCreationManager.get().finishCreateItem(player);
					return true;
				}
				
				// CANCEL COMMAND //
				if (args[0].equalsIgnoreCase("cancel") && args.length == 1) {
					RpgCreationManager.get().cancelCreateItem(player);
					return true;
				}
				
				if (args[0].equalsIgnoreCase("class")) {
					if (args.length == 1) {
						sendComponent(player, createTitleItem("Settings - Classes"));
						sendComponent(player, createMenuItem(1, "Create", "create a class", "/rpgadmin class create"));
						sendComponent(player, createMenuItem(2, "Edit", "modify an existing class", "/rpgadmin class edit"));
						sendComponent(player, createMenuItem(3, "List", "list all classes", "/rpgadmin class list"));
						sendComponent(player, createBackItem("/rpgadmin"));
						
						return true;
					}
					
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("create")) {
							
						} else if (args[1].equalsIgnoreCase("edit")) {
							
						} else if (args[1].equalsIgnoreCase("list")) {
							
						}
					}
				} else if (args[0].equalsIgnoreCase("quest")) {
					if (args.length == 1) {
						sendComponent(player, createTitleItem("Settings - Quests"));	
						sendComponent(player, createMenuItem(1, "Create", "create a quest", "/rpgadmin quest create"));
						sendComponent(player, createMenuItem(2, "Edit", "modify an existing quest", "/rpgadmin quest edit"));
						sendComponent(player, createMenuItem(3, "List", "list all quests", "/rpgadmin quest list"));
						sendComponent(player, createBackItem("/rpgadmin"));
						
						return true;
					}
					
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("create")) {
							RpgCreationManager.get().beginCreateQuest(player);
						} else if (args[1].equalsIgnoreCase("edit")) {
							
						} else if (args[1].equalsIgnoreCase("list")) {
							sendComponent(player, createTitleItem("Quest List"));
							
							List<String> questNames = QuestDataManager.get().getQuestNames();
							if (questNames == null || questNames.size() == 0) {
								sendComponent(player, Component.text("You haven't created any quests."));
								return true;
							}
							
							for (int i = 0; i < questNames.size(); i++) {
								String questName = questNames.get(i);
								
								boolean published = QuestDataManager.get().questPublished(questName);
								String description = (published) ? "Published" : "Not published";
								
								sendComponent(player, createMenuItem(i + 1, questName, description, "/help"));
							}
							
							sendComponent(player, createBackItem("/rpgadmin quest"));
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private void sendComponent(Player player, Component component) {
		player.spigot().sendMessage(serializer.serialize(component));
	}
	
	private Component createTitleItem(String title) {
		Component dashes = Component.text("--------").color(TextColor.color(0x00FF00));
		Component titleText = Component.text(title).color(TextColor.color(0xFF00FF));
		return dashes.append(titleText).append(dashes);
	}
	
	private Component createMenuItem(int number, String clickableText, String description, String command) {
		return Component.text(number + ") ").color(TextColor.color(0x00FFFF))
			.append(Component.text(clickableText)
				.color(TextColor.color(0xFFD700))
				.hoverEvent(HoverEvent.showText(Component.text("Click me!")))
				.clickEvent(ClickEvent.runCommand(command)))
			.append(Component.text(" - " + description).color(TextColor.color(0x00FFFF)));
	}
	
	private Component createBackItem(String command) {
		return Component.text("<< Back")
			.color(TextColor.color(0xFF0000))
			.hoverEvent(HoverEvent.showText(Component.text("Click me!")))
			.clickEvent(ClickEvent.runCommand(command));
	}

}
