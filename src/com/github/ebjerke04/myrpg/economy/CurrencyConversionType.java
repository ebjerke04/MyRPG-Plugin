package com.github.ebjerke04.myrpg.economy;

import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.ebjerke04.myrpg.util.Logging;

public enum CurrencyConversionType {
    
    COPPER_TO_IRON(new ItemStack(Material.IRON_INGOT, 1), new ItemStack(Material.COPPER_INGOT, 64)),
    IRON_TO_COPPER(new ItemStack(Material.COPPER_INGOT, 64), new ItemStack(Material.IRON_INGOT, 1)),
    IRON_TO_GOLD(new ItemStack(Material.GOLD_INGOT, 1), new ItemStack(Material.IRON_INGOT, 64)),
    GOLD_TO_IRON(new ItemStack(Material.IRON_INGOT, 64), new ItemStack(Material.GOLD_INGOT, 1));

    private final ItemStack to, from;

    CurrencyConversionType(ItemStack to, ItemStack from) {
        this.to = to;
        this.from = from;
    }

    public ItemStack getTo() {
        return to;
    }

    public ItemStack getFrom() {
        return from;
    }

    /**
     * @param itemType The type of item clicked, ie item that is being converted to.
     * @param itemCount The amount of the item clicked that will be received in the transaction.
     * @return The type of transaction, ex: converting from iron to copper is IRON_TO_COPPER
     * @throws IllegalArgumentException if clicked item is not COPPER_INGOT, IRON_INGOT, or GOLD_INGOT
     *                                  or if itemCount is not 1 or 64
     * @throws IllegalStateException if converting to copper from lower denomination
     *                               or if converting to gold from higher denomination
     */
    public static CurrencyConversionType fromItemInfo(Material itemType, int itemCount) {
        if (itemType != Material.COPPER_INGOT && itemType != Material.IRON_INGOT && itemType != Material.GOLD_INGOT)
            throw new IllegalArgumentException("fromItemInfo can only take COPPER_INGOT, IRON_INGOT, or GOLD_INGOT as an item type");

        if (itemCount != 1 && itemCount != 64)
            throw new IllegalArgumentException("fromItemInfo can only take 1 or 64 as an itemCount");

        if (itemType == Material.COPPER_INGOT && itemCount == 1)
            throw new IllegalStateException("fromItemInfo, no currency conversion from lower denomination to copper");

        if (itemType == Material.GOLD_INGOT && itemCount == 64)
            throw new IllegalStateException("fromItemInfo, no currency conversion from high denomination to gold");

        switch (itemType) {
        case COPPER_INGOT:
            if (itemCount == 64) return IRON_TO_COPPER;
        case IRON_INGOT:
            if (itemCount == 1) return COPPER_TO_IRON;
            else if (itemCount == 64) return GOLD_TO_IRON;
        case GOLD_INGOT:
            if (itemCount == 1) return IRON_TO_GOLD;
        default:
            Logging.sendConsole(Level.SEVERE, "Should not have reached here");
        }
        
        return null;
    }

}
