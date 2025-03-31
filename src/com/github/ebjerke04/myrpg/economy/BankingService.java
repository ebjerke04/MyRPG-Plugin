package com.github.ebjerke04.myrpg.economy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.ebjerke04.myrpg.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class BankingService {
    
    public BankingService() {

    }

    // TODO: Add checks to make sure player has space in their inventory...

    /**
     * @param player the player that initiated the currency conversion.
     * @param type contains data of [from]->[to] of the transaction Ex: copper->iron
     * @return true if currency conversion was successful.
     */
    public boolean handleCurrencyConversion(Player player, CurrencyConversionType type) {
        PlayerInventory inventory = player.getInventory();
        int itemCount = 0;
        for (int slotNumber = 0; slotNumber < inventory.getSize(); slotNumber++) {
            ItemStack item = inventory.getItem(slotNumber);
            if (item == null) continue;
            if (item.getType().equals(type.getFrom().getType())) itemCount += item.getAmount();
        }

        int requiredAmount = type.getFrom().getAmount();
        if (itemCount >= requiredAmount) {
            for (int slotNumber = 0; slotNumber < inventory.getSize(); slotNumber++) {
                ItemStack item = inventory.getItem(slotNumber);
                if (item == null) continue;
                if (!item.getType().equals(type.getFrom().getType())) continue;
                // TODO: Finish logic for handling currency conversion.
                if (item.getAmount() >= requiredAmount) {
                    item.setAmount(item.getAmount() - requiredAmount);
                    break;
                } else {
                    requiredAmount -= item.getAmount();
                    inventory.clear(slotNumber);
                }
            }

            // TODO: Has some weird issues I dont really understand in terms of
            // why the wrong amount of ingots get added to player's inventory.
            ItemStack toAdd = type.getTo().clone();
            inventory.addItem(toAdd);
            return true;
        } else {
            Plugin.getAdventure().player(player).sendMessage(Component.text("You do not have enough of the required items for this transaction")
                .color(TextColor.color(0xFF0000)));
            return false;
        }
    }

}
