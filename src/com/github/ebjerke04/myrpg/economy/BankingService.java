package com.github.ebjerke04.myrpg.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class BankingService {
    
    public BankingService() {

    }

    public boolean handleCurrencyConversion(Player player, CurrencyConversionType type) {
        PlayerInventory inventory = player.getInventory();
        int itemCount = 0;
        for (int slotNumber = 0; slotNumber < inventory.getSize(); slotNumber++) {
            ItemStack item = inventory.getItem(slotNumber);
            if (item.getType().equals(type.getFrom())) itemCount += item.getAmount();
        }

        if (itemCount >= type.getFrom().getAmount()) {
            int requiredAmount = type.getFrom().getAmount();
            for (int slotNumber = 0; slotNumber < inventory.getSize(); slotNumber++) {
                ItemStack item = inventory.getItem(slotNumber);
                // TODO: Finish logic for handling currency conversion.
            }
            return true;
        } else {
            player.sendMessage(Component.text("You do not have enough of the required items for this transaction")
                .color(TextColor.color(0xFF0000)));
            return false;
        }
    }

}
