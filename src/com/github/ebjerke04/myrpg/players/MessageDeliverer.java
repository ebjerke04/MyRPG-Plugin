package com.github.ebjerke04.myrpg.players;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github.ebjerke04.myrpg.Plugin;

import net.kyori.adventure.text.Component;

public class MessageDeliverer {

    public static BukkitTask deliverMessageList(Player player, List<Component> messages) {
        return deliverMessageList(player, messages, null);
    }

    public static BukkitTask deliverMessageList(Player player, List<Component> messages, Runnable onComplete) {
        return new BukkitRunnable() {
            private int index = 0;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }

                if (index >= messages.size()) {
                    this.cancel();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                    return;
                }

                player.sendMessage(messages.get(index));
                index++;
            }
        }.runTaskTimer(Plugin.get(), 0L, 20L);
    }

    public static void cancelDelivery(BukkitTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}
