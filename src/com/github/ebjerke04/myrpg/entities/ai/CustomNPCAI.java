package com.github.ebjerke04.myrpg.entities.ai;

import java.util.logging.Level;

import org.bukkit.craftbukkit.v1_21_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.ebjerke04.myrpg.Plugin;
import com.github.ebjerke04.myrpg.util.Logging;
import com.github.ebjerke04.myrpg.world.NPC;

import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class CustomNPCAI extends BukkitRunnable {

    private NPC npc;

    // NMS test
    private final Villager nmsVillager;

    public CustomNPCAI(NPC npc) {
        this.npc = npc;

        CraftEntity craftEntity = (CraftEntity) npc.getEntity();
        if (!(craftEntity.getHandle() instanceof Villager)) {
            Logging.sendConsole(Level.SEVERE, "Shouldn't have hit this branch...");
            throw new IllegalStateException();
        }
        nmsVillager = (Villager) craftEntity.getHandle();

        setupAIGoals();

        this.runTaskTimer(Plugin.get(), 0L, 5L);
    }

    private void setupAIGoals() {
        nmsVillager.goalSelector.removeAllGoals(null);
        nmsVillager.targetSelector.removeAllGoals(null);

        nmsVillager.goalSelector.addGoal(1, new LookAtPlayerGoal(nmsVillager, Player.class, 6.0F));
        nmsVillager.goalSelector.addGoal(2, new RandomLookAroundGoal(nmsVillager));

        nmsVillager.setNoGravity(true);
        nmsVillager.setInvulnerable(true);
        nmsVillager.setBoundingBox(new AABB(0, 0, 0, 0, 0, 0));
        nmsVillager.moveTo(nmsVillager.getX(), nmsVillager.getY(), nmsVillager.getZ());
        nmsVillager.getNavigation().stop();
    }

    @Override
    public void run() {
        if (npc == null) {
            this.cancel();
            return;
        }

        if (npc.getEntity().isDead()) {
            this.cancel();
            return;
        }
    }

    public void cleanup() {
        this.cancel();
        npc = null;
    }
    
}
