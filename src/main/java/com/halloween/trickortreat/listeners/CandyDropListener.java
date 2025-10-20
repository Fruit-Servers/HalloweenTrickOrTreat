package com.halloween.trickortreat.listeners;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CandyDropListener implements Listener {
    
    private final TrickOrTreatPlugin plugin;
    private final Random random;
    
    public CandyDropListener(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.getConfigManager().isCandyDropEnabled()) {
            return;
        }
        
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        
        if (killer == null) {
            return;
        }
        
        if (!shouldDropCandy(entity)) {
            return;
        }
        
        double dropChance = plugin.getConfigManager().getCandyDropChance();
        double roll = random.nextDouble() * 100.0;
        
        if (roll <= dropChance) {
            double rareRoll = random.nextDouble() * 100.0;
            boolean shouldGiveRareCandy = rareRoll <= 1.0 && plugin.getRareCandyManager().canReceiveRareCandy(killer);
            
            if (shouldGiveRareCandy && !plugin.getCooldownManager().isOnRareCandyCooldown(killer)) {
                // Give rare candy
                ItemStack rareCandy = plugin.getRareCandyManager().createRareCandyItem();
                entity.getWorld().dropItemNaturally(entity.getLocation(), rareCandy);
                plugin.getRareCandyManager().recordRareCandyDrop(killer);
                plugin.getCooldownManager().setRareCandyCooldown(killer);
                
                killer.sendMessage(plugin.getConfigManager().getMessage("rare-candy-received"));
            } else {
                // Give regular candy (either rare candy was on cooldown or regular candy was rolled)
                ItemStack candy = plugin.getCandyManager().createCandyItem();
                entity.getWorld().dropItemNaturally(entity.getLocation(), candy);
                
                killer.sendMessage(plugin.getConfigManager().getMessage("candy-received"));
                
                // If rare candy was on cooldown, show cooldown message as well
                if (shouldGiveRareCandy && plugin.getCooldownManager().isOnRareCandyCooldown(killer)) {
                    long remaining = plugin.getCooldownManager().getRareCandyCooldownRemaining(killer);
                    String timeLeft = plugin.getCooldownManager().formatCooldownTime(remaining);
                    killer.sendMessage("§c⏰ Rare candy on cooldown (" + timeLeft + " remaining), gave regular candy instead!");
                }
            }
        }
    }
    
    private boolean shouldDropCandy(LivingEntity entity) {
        if (entity instanceof Player) {
            return false;
        }
        
        if (entity instanceof Boss) {
            return plugin.getConfigManager().canDropFromBoss();
        }
        
        if (isHostileMob(entity)) {
            return plugin.getConfigManager().canDropFromHostile();
        }
        
        if (isPassiveMob(entity)) {
            return plugin.getConfigManager().canDropFromPassive();
        }
        
        return false;
    }
    
    private boolean isHostileMob(LivingEntity entity) {
        return entity instanceof Monster || 
               entity instanceof Slime || 
               entity instanceof Ghast || 
               entity instanceof Phantom ||
               entity instanceof Shulker;
    }
    
    private boolean isPassiveMob(LivingEntity entity) {
        return entity instanceof Animals || 
               entity instanceof WaterMob || 
               entity instanceof Ambient ||
               entity instanceof Villager ||
               entity instanceof IronGolem ||
               entity instanceof Snowman;
    }
}
