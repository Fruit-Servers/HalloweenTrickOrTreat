package com.halloween.trickortreat.handlers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.managers.ConfigManager;
import com.halloween.trickortreat.rewards.Treat;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class TreatHandler {
    
    private final TrickOrTreatPlugin plugin;
    private final ConfigManager config;
    private final Random random;
    
    public TreatHandler(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.random = new Random();
    }
    
    public void applyTreat(Player player, Treat treat) {
        switch (treat) {
            case SPEEDY_TREAT:
                applySpeedyTreat(player);
                break;
            case HIGH_JUMPS:
                applyHighJumps(player);
                break;
            case CANDY_CASH:
                applyCandyCash(player);
                break;
            case XP_RUSH:
                applyXpRush(player);
                break;
            case NOURISHING_NIBBLE:
                applyNourishingNibble(player);
                break;
            case SPECTRAL_SIGHT:
                applySpectralSight(player);
                break;
            case MIDAS_TOUCH:
                applyMidasTouch(player);
                break;
            case INSTANT_REPAIR:
                applyInstantRepair(player);
                break;
            case HEALTH_SURGE:
                applyHealthSurge(player);
                break;
        }
    }
    
    private void applySpeedyTreat(Player player) {
        int level = config.getInt("treats.speedy-treat.speed-level", 2);
        int duration = config.getInt("treats.speedy-treat.duration", 30);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 20, level - 1));
    }
    
    private void applyHighJumps(Player player) {
        int level = config.getInt("treats.high-jumps.jump-level", 2);
        int duration = config.getInt("treats.high-jumps.duration", 30);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration * 20, level - 1));
    }
    
    private void applyCandyCash(Player player) {
        double minAmount = config.getDouble("treats.candy-cash.min-amount", 50.0);
        double maxAmount = config.getDouble("treats.candy-cash.max-amount", 200.0);
        double amount = minAmount + (random.nextDouble() * (maxAmount - minAmount));
        
        if (plugin.getEconomyManager().hasEconomy()) {
            plugin.getEconomyManager().depositPlayer(player, amount);
            player.sendMessage("§a💰 You received " + plugin.getEconomyManager().format(amount) + "!");
        } else {
            player.sendMessage("§c⚠ Economy system not available!");
        }
    }
    
    private void applyXpRush(Player player) {
        int minXp = config.getInt("treats.xp-rush.min-xp", 50);
        int maxXp = config.getInt("treats.xp-rush.max-xp", 100);
        int xp = minXp + random.nextInt(maxXp - minXp + 1);
        player.giveExp(xp);
        player.sendMessage("§a✨ You gained " + xp + " experience points!");
    }
    
    private void applyNourishingNibble(Player player) {
        int hunger = config.getInt("treats.nourishing-nibble.hunger-restored", 4);
        double saturation = config.getDouble("treats.nourishing-nibble.saturation-restored", 8.0);
        
        int currentFood = player.getFoodLevel();
        player.setFoodLevel(Math.min(20, currentFood + hunger));
        player.setSaturation((float) Math.min(20.0, player.getSaturation() + saturation));
    }
    
    private void applySpectralSight(Player player) {
        int radius = config.getInt("treats.spectral-sight.radius", 20);
        int duration = config.getInt("treats.spectral-sight.duration", 20);
        
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffect(
                    new PotionEffect(PotionEffectType.GLOWING, duration * 20, 0)
                );
            }
        }
    }
    
    private void applyMidasTouch(Player player) {
        int minItems = config.getInt("treats.midas-touch.min-items", 1);
        int maxItems = config.getInt("treats.midas-touch.max-items", 3);
        int itemCount = minItems + random.nextInt(maxItems - minItems + 1);
        
        List<String> possibleItems = config.getConfig().getStringList("treats.midas-touch.possible-items");
        
        for (int i = 0; i < itemCount; i++) {
            String materialName = possibleItems.get(random.nextInt(possibleItems.size()));
            Material material = Material.valueOf(materialName);
            ItemStack item = new ItemStack(material, 1);
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }
    
    private void applyInstantRepair(Player player) {
        int minPercent = config.getInt("treats.instant-repair.min-percent", 5);
        int maxPercent = config.getInt("treats.instant-repair.max-percent", 10);
        int percent = minPercent + random.nextInt(maxPercent - minPercent + 1);
        
        int repairedCount = 0;
        
        for (int slot = 0; slot < 9; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            
            ItemMeta meta = item.getItemMeta();
            if (meta == null || !(meta instanceof org.bukkit.inventory.meta.Damageable)) {
                continue;
            }
            
            org.bukkit.inventory.meta.Damageable damageable = (org.bukkit.inventory.meta.Damageable) meta;
            
            if (!damageable.hasDamage() || damageable.getDamage() == 0) {
                continue;
            }
            
            int maxDurability = item.getType().getMaxDurability();
            int repairAmount = (int) (maxDurability * (percent / 100.0));
            int newDamage = Math.max(0, damageable.getDamage() - repairAmount);
            
            damageable.setDamage(newDamage);
            item.setItemMeta(meta);
            repairedCount++;
        }
        
        if (repairedCount == 0) {
            player.sendMessage("§c⚠ No items in your hotbar need repairs!");
        } else {
            player.sendMessage("§a🔧 Repaired " + repairedCount + " item(s) in your hotbar by " + percent + "%!");
        }
    }
    
    private void applyHealthSurge(Player player) {
        int level = config.getInt("treats.health-surge.absorption-level", 2);
        int duration = config.getInt("treats.health-surge.duration", 45);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, duration * 20, level - 1));
    }
}
