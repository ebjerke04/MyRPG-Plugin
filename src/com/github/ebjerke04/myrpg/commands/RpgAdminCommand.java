package com.github.ebjerke04.myrpg.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.ebjerke04.myrpg.creation.RpgCreationManager;
import com.github.ebjerke04.myrpg.data.QuestDataManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

public class RpgAdminCommand extends BaseCommand {
	
	public RpgAdminCommand() {
		super("rpgadmin");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(commandName)) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Component.text("Please run this command as a player within the server!")
						.color(TextColor.color(0xFF0000)));
				return false;
			}
			Player player = (Player) sender;
			
			if (!player.hasPermission("rpg.admin")) {
				player.sendMessage(Component.text("You do not have permission to use this command!")
						.color(TextColor.color(0xFF0000)));
				return false;
			}
			
			if (args.length == 0) {
				player.sendMessage(createTitleItem("Settings"));
				player.sendMessage(createMenuItem(1, "Classes", "modify class data", "/rpgadmin class"));
				player.sendMessage(createMenuItem(2, "Quests", "modify quest data", "/rpgadmin quest"));
				player.sendMessage(createMenuItem(3, "Weapons", "modify weapon data", "/rpgadmin weapon"));
				
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
						player.sendMessage(createTitleItem("Settings - Classes"));
						player.sendMessage(createMenuItem(1, "Create", "create a class", "/rpgadmin class create"));
						player.sendMessage(createMenuItem(2, "Edit", "modify an existing class", "/rpgadmin class edit"));
						player.sendMessage(createMenuItem(3, "List", "list all classes", "/rpgadmin class list"));
						player.sendMessage(createBackItem("/rpgadmin"));
						
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
						player.sendMessage(createTitleItem("Settings - Quests"));	
						player.sendMessage(createMenuItem(1, "Create", "create a quest", "/rpgadmin quest create"));
						player.sendMessage(createMenuItem(2, "Edit", "modify an existing quest", "/rpgadmin quest edit"));
						player.sendMessage(createMenuItem(3, "List", "list all quests", "/rpgadmin quest list"));
						player.sendMessage(createBackItem("/rpgadmin"));
						
						return true;
					}
					
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("create")) {
							RpgCreationManager.get().beginCreateQuest(player);
						} else if (args[1].equalsIgnoreCase("edit")) {
							
						} else if (args[1].equalsIgnoreCase("list")) {
							player.sendMessage(createTitleItem("Quest List"));
							
							List<String> questNames = QuestDataManager.get().getQuestNames();
							if (questNames == null || questNames.size() == 0) {
								player.sendMessage(Component.text("You haven't created any quests.")
										.color(TextColor.color(0x00FFFF)));
								return true;
							}
							
							for (int i = 0; i < questNames.size(); i++) {
								String questName = questNames.get(i);
								
								boolean published = QuestDataManager.get().questPublished(questName);
								String description = (published) ? "Published" : "Not published";
								
								player.sendMessage(createMenuItem(i + 1, questName, description, "/help"));
							}
							
							player.sendMessage(createBackItem("/rpgadmin quest"));
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private Component createTitleItem(String title) {
		Component dashes = Component.text("--------").color(TextColor.color(0x00FF00));
		Component titleText = Component.text(title).color(TextColor.color(0xFF00FF));
		return dashes.append(titleText).append(dashes);
	}
	
	private Component createMenuItem(int number, String clickableText, String description, String command) {
		TextColor aquaText = TextColor.color(0x00FFFF);
		TextColor goldText = TextColor.color(0xFFD700);
		
		Component numberText, clickableOption, descriptionText;
		numberText = Component.text(Integer.toString(number) + ") ").color(aquaText);
		clickableOption = Component.text(clickableText)
				.color(goldText)
				.hoverEvent(HoverEvent.showText(Component.text("Click me!")))
				.clickEvent(ClickEvent.runCommand(command));
		descriptionText = Component.text(" - " + description).color(aquaText);
		
		return numberText.append(clickableOption).append(descriptionText);
	}
	
	private Component createBackItem(String command) {
		return Component.text("<< Back")
				.color(TextColor.color(0xFF0000))
				.hoverEvent(HoverEvent.showText(Component.text("Click me!")))
				.clickEvent(ClickEvent.runCommand(command));
	}

}
