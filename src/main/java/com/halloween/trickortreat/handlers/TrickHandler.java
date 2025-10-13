package com.halloween.trickortreat.handlers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.managers.ConfigManager;
import com.halloween.trickortreat.rewards.Trick;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TrickHandler {
    
    private final TrickOrTreatPlugin plugin;
    private final ConfigManager config;
    private final Random random;
    
    public TrickHandler(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.random = new Random();
    }
    
    public void applyTrick(Player player, Trick trick) {
        switch (trick) {
            case TEMPORARY_BLINDNESS:
                applyTemporaryBlindness(player);
                break;
            case UNLUCKY_STRIKE:
                applyUnluckyStrike(player);
                break;
            case WOBBLY_LEGS:
                applyWobblyLegs(player);
                break;
            case SLOWNESS_CURSE:
                applySlownessCurse(player);
                break;
            case RANDOM_TELEPORT:
                applyRandomTeleport(player);
                break;
            case FAKE_SCARE:
                applyFakeScare(player);
                break;
            case GRAVITY_SHIFT:
                applyGravityShift(player);
                break;
            case INVENTORY_JUMBLE:
                applyInventoryJumble(player);
                break;
            case COBWEB_CURSE:
                applyCobwebCurse(player);
                break;
        }
    }
    
    private void applyTemporaryBlindness(Player player) {
        int duration = config.getInt("tricks.temporary-blindness.duration", 10);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration * 20, 0));
    }
    
    private void applyUnluckyStrike(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
    }
    
    private void applyWobblyLegs(Player player) {
        int duration = config.getInt("tricks.wobbly-legs.duration", 15);
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration * 20, 0));
    }
    
    private void applySlownessCurse(Player player) {
        int level = config.getInt("tricks.slowness-curse.slowness-level", 4);
        int duration = config.getInt("tricks.slowness-curse.duration", 10);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration * 20, level - 1));
    }
    
    private void applyRandomTeleport(Player player) {
        int minDistance = config.getInt("tricks.random-teleport.min-distance", 5);
        int maxDistance = config.getInt("tricks.random-teleport.max-distance", 10);
        
        Location currentLoc = player.getLocation();
        double distance = minDistance + (random.nextDouble() * (maxDistance - minDistance));
        double angle = random.nextDouble() * 2 * Math.PI;
        
        double offsetX = Math.cos(angle) * distance;
        double offsetZ = Math.sin(angle) * distance;
        
        Location newLoc = currentLoc.clone().add(offsetX, 0, offsetZ);
        newLoc.setY(player.getWorld().getHighestBlockYAt(newLoc) + 1);
        
        player.teleport(newLoc);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }
    
    private void applyFakeScare(Player player) {
        String soundName = config.getString("tricks.fake-scare.sound", "ENTITY_PHANTOM_AMBIENT");
        try {
            Sound sound = Sound.valueOf(soundName);
            player.playSound(player.getLocation(), sound, 2.0f, 0.8f);
        } catch (IllegalArgumentException e) {
            player.playSound(player.getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 2.0f, 0.8f);
        }
    }
    
    private void applyGravityShift(Player player) {
        int duration = config.getInt("tricks.gravity-shift.levitation-duration", 2);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, duration * 20, 0));
        
        new BukkitRunnable() {
            @Override
            public void run() {
                player.removePotionEffect(PotionEffectType.LEVITATION);
                player.setVelocity(player.getVelocity().setY(0));
            }
        }.runTaskLater(plugin, duration * 20L);
    }
    
    private void applyInventoryJumble(Player player) {
        ItemStack[] hotbar = new ItemStack[9];
        
        for (int i = 0; i < 9; i++) {
            hotbar[i] = player.getInventory().getItem(i);
        }
        
        for (int i = hotbar.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            ItemStack temp = hotbar[i];
            hotbar[i] = hotbar[j];
            hotbar[j] = temp;
        }
        
        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, hotbar[i]);
        }
        
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 0.5f);
    }
    
    private void applyCobwebCurse(Player player) {
        int duration = config.getInt("tricks.cobweb-curse.cobweb-duration", 5);
        Location loc = player.getLocation().clone();
        loc.setY(Math.floor(loc.getY()));
        
        Material originalBlock = loc.getBlock().getType();
        loc.getBlock().setType(Material.COBWEB);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (loc.getBlock().getType() == Material.COBWEB) {
                    loc.getBlock().setType(originalBlock);
                }
            }
        }.runTaskLater(plugin, duration * 20L);
    }
}
