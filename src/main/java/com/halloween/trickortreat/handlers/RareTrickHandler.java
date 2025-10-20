package com.halloween.trickortreat.handlers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.rewards.RareTrick;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RareTrickHandler {
    
    private final TrickOrTreatPlugin plugin;
    private final Random random;
    
    public RareTrickHandler(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }
    
    public void applyRareTrick(Player player, RareTrick trick) {
        switch (trick) {
            case FAKE_CREEPER_BLAST:
                applyFakeCreeperBlast(player);
                break;
            case FAKE_WITHER:
                applyFakeWither(player);
                break;
            case MULTI_POTION_EFFECT:
                applyMultiPotionEffect(player);
                break;
            case UPGRADED_COBWEB:
                applyUpgradedCobweb(player);
                break;
        }
    }
    
    private void applyFakeCreeperBlast(Player player) {
        Location loc = player.getLocation();
        
        Creeper creeper = (Creeper) player.getWorld().spawnEntity(loc, EntityType.CREEPER);
        creeper.setAI(false);
        creeper.setExplosionRadius(0);
        creeper.setPowered(false);
        creeper.ignite();
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (creeper.isValid()) {
                    player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
                    player.getWorld().spawnParticle(org.bukkit.Particle.EXPLOSION_LARGE, loc, 5);
                    creeper.remove();
                }
                player.sendMessage("§c💥 BOOM! Just kidding!");
            }
        }.runTaskLater(plugin, 30L);
    }
    
    private void applyFakeWither(Player player) {
        Location loc = player.getLocation().add(0, 2, 0);
        
        player.getWorld().playSound(loc, Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
        
        Wither wither = (Wither) player.getWorld().spawnEntity(loc, EntityType.WITHER);
        wither.setAI(false);
        wither.setInvulnerable(true);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (wither.isValid()) {
                    wither.remove();
                    player.sendMessage("§5👻 The wither vanished into thin air!");
                }
            }
        }.runTaskLater(plugin, 100L);
    }
    
    private void applyMultiPotionEffect(Player player) {
        int duration = 90 * 20;
        
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, 1));
        
        player.sendMessage("§c🧪 You've been cursed with multiple effects!");
    }
    
    private void applyUpgradedCobweb(Player player) {
        Location center = player.getLocation();
        int radius = 3;
        
        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location loc = center.clone().add(x, y, z);
                    
                    if (loc.distance(center) <= radius && isSafeToPlaceCobweb(loc)) {
                        loc.getBlock().setType(Material.COBWEB);
                        
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (loc.getBlock().getType() == Material.COBWEB) {
                                    loc.getBlock().setType(Material.AIR);
                                }
                            }
                        }.runTaskLater(plugin, 100L + random.nextInt(100));
                    }
                }
            }
        }
        
        player.sendMessage("§8🕸️ You're trapped in a massive cobweb!");
    }
    
    private boolean isSafeToPlaceCobweb(Location loc) {
        Material blockType = loc.getBlock().getType();
        
        // Only place cobwebs in air blocks
        if (blockType != Material.AIR) {
            return false;
        }
        
        // Check if any adjacent blocks are containers to be extra safe
        Location[] adjacentLocations = {
            loc.clone().add(1, 0, 0),
            loc.clone().add(-1, 0, 0),
            loc.clone().add(0, 1, 0),
            loc.clone().add(0, -1, 0),
            loc.clone().add(0, 0, 1),
            loc.clone().add(0, 0, -1)
        };
        
        for (Location adjLoc : adjacentLocations) {
            if (isContainerBlock(adjLoc.getBlock().getType())) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isContainerBlock(Material material) {
        switch (material) {
            // Storage containers
            case CHEST:
            case TRAPPED_CHEST:
            case ENDER_CHEST:
            case SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BARREL:
            
            // Functional blocks with inventories
            case FURNACE:
            case BLAST_FURNACE:
            case SMOKER:
            case DROPPER:
            case DISPENSER:
            case HOPPER:
            
            // Brewing and enchanting
            case BREWING_STAND:
            case ENCHANTING_TABLE:
            
            // Workstations
            case CRAFTING_TABLE:
            case CARTOGRAPHY_TABLE:
            case FLETCHING_TABLE:
            case SMITHING_TABLE:
            case STONECUTTER:
            case LOOM:
            case GRINDSTONE:
            case ANVIL:
            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:
            
            // Other important blocks
            case BEACON:
            case JUKEBOX:
            case LECTERN:
            case COMPOSTER:
                return true;
                
            default:
                return false;
        }
    }
}
