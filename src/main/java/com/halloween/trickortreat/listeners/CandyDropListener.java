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
            
            if (rareRoll <= 1.0 && plugin.getRareCandyManager().canReceiveRareCandy(killer)) {
                ItemStack rareCandy = plugin.getRareCandyManager().createRareCandyItem();
                entity.getWorld().dropItemNaturally(entity.getLocation(), rareCandy);
                plugin.getRareCandyManager().recordRareCandyDrop(killer);
                
                killer.sendMessage(plugin.getConfigManager().getMessage("rare-candy-received"));
            } else {
                ItemStack candy = plugin.getCandyManager().createCandyItem();
                entity.getWorld().dropItemNaturally(entity.getLocation(), candy);
                
                killer.sendMessage(plugin.getConfigManager().getMessage("candy-received"));
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
