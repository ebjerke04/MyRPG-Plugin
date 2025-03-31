package com.github.ebjerke04.myrpg.creation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import com.github.ebjerke04.myrpg.data.QuestDataManager;
import com.github.ebjerke04.myrpg.quests.RpgCreationStep;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

public class RpgCreationManager {
	
	private static RpgCreationManager instance;
	
	// UUID - player UUID
	private Map<UUID, RpgCreationStep> itemsInCreation = new HashMap<>();
	private Map<UUID, String> rpgItemNameBuffer = new HashMap<>();
	
	private RpgCreationManager() {
		
	}
	
	public static void init() {
		instance = new RpgCreationManager();
	}
	
	public void beginCreateQuest(Player player) {
		if (getCreationStatus(player) == RpgCreationStep.NOT_CREATING) {			
			itemsInCreation.put(player.getUniqueId(), RpgCreationStep.ENTER_QUEST_NAME);
			
			player.sendMessage(Component.text("Quest creation process started. Please enter in chat a name for this quest.")
					.color(TextColor.color(0x00FFFF)));
			Component cancelButton = Component.text("here")
					.color(TextColor.color(0xFF0000))
					.hoverEvent(HoverEvent.showText(Component.text("or type /rpgadmin cancel")))
					.clickEvent(ClickEvent.runCommand("/rpgadmin cancel"));
			player.sendMessage(Component.text("To abort click ")
					.color(TextColor.color(0x00FFFF)).append(cancelButton));
		} else {
			player.sendMessage(Component.text("You are already in the process of creating a quest.")
					.color(TextColor.color(0xFF0000)));
		}
	}
	
	public void submitQuestName(Player player, String name) {
		if (getCreationStatus(player) == RpgCreationStep.ENTER_QUEST_NAME) {
			Component questName = Component.text(name)
					.color(TextColor.color(0xFFD700));
			
			if (QuestDataManager.get().questExists(name)) {
				player.sendMessage(Component.text("Quest, ").color(TextColor.color(0xFF0000)).append(questName)
						.append(Component.text(", already exists. Please enter a different name.").color(TextColor.color(0xFF0000))));
				Component cancelButton = Component.text("here")
						.color(TextColor.color(0xFF0000))
						.hoverEvent(HoverEvent.showText(Component.text("or type /rpgadmin cancel")))
						.clickEvent(ClickEvent.runCommand("/rpgadmin cancel"));
				player.sendMessage(Component.text("To abort click ")
						.color(TextColor.color(0x00FFFF)).append(cancelButton));
				
				return;
			}
			
			itemsInCreation.replace(player.getUniqueId(), RpgCreationStep.CONFIRM_QUEST_NAME);
			rpgItemNameBuffer.put(player.getUniqueId(), name);
			
			player.sendMessage(Component.text("Quest name of ")
						.color(TextColor.color(0x00FFFF))
					.append(questName)
					.append(Component.text(" submitted. Are you sure you want to create this quest?")
						.color(TextColor.color(0x00FFFF))));
			
			Component confirmButton = Component.text("Confirm")
					.color(TextColor.color(0x00FF00))
					.hoverEvent(HoverEvent.showText(Component.text("or type /rpgadmin confirm")))
					.clickEvent(ClickEvent.runCommand("/rpgadmin confirm"));
			Component cancelButton = Component.text("Abort")
					.color(TextColor.color(0xFF0000))
					.hoverEvent(HoverEvent.showText(Component.text("or type /rpgadmin cancel")))
					.clickEvent(ClickEvent.runCommand("/rpgadmin cancel"));
			player.sendMessage(confirmButton.append(Component.text(" | ")
					.color(TextColor.color(0x00FFFF)).append(cancelButton)));
		}
	}
	
	public RpgCreationStep getCreationStatus(Player player) {
		if (itemsInCreation.containsKey(player.getUniqueId())) return itemsInCreation.get(player.getUniqueId());
		
		return RpgCreationStep.NOT_CREATING;
	}
	
	public void cancelCreateItem(Player player) {
		RpgCreationStep creationStep = getCreationStatus(player);
		
		if (creationStep == RpgCreationStep.NOT_CREATING) {
			player.sendMessage(Component.text("You are currently not creating anything!")
					.color(TextColor.color(0xFF0000)));
			return;
		}
		
		if (creationStep == RpgCreationStep.ENTER_QUEST_NAME || creationStep == RpgCreationStep.CONFIRM_QUEST_NAME) {
			player.sendMessage(Component.text("Cancelled quest creation!")
					.color(TextColor.color(0x00FFFF)));
		}
		
		if (creationStep == RpgCreationStep.ENTER_CLASS_NAME || creationStep == RpgCreationStep.CONFIRM_CLASS_NAME) {
			player.sendMessage(Component.text("Cancelled class creation!")
					.color(TextColor.color(0x00FFFF)));
		}
		
		itemsInCreation.remove(player.getUniqueId());
		if (rpgItemNameBuffer.containsKey(player.getUniqueId()))
			rpgItemNameBuffer.remove(player.getUniqueId());
	}
	
	public void finishCreateItem(Player player) {
		RpgCreationStep creationStep = getCreationStatus(player);
		
		if (creationStep == RpgCreationStep.CONFIRM_QUEST_NAME) {
			QuestDataManager.get().addQuestToConfig(rpgItemNameBuffer.get(player.getUniqueId()));
			
			itemsInCreation.remove(player.getUniqueId());
			rpgItemNameBuffer.remove(player.getUniqueId());
			
			player.sendMessage(Component.text("Quest has successfully been created!")
					.color(TextColor.color(0x00FFFF)));
		}
	}
	
	public static RpgCreationManager get() {
		return instance;
	}
	
	public void shutdown() {

	}

}
