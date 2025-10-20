package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    
    private final TrickOrTreatPlugin plugin;
    private final Map<UUID, Long> rareCandyCooldowns;
    private final Map<UUID, Long> candyUseCooldowns;
    private static final long RARE_CANDY_COOLDOWN = 60 * 60 * 1000L; // 1 hour in milliseconds
    private static final long CANDY_USE_COOLDOWN = 3 * 1000L; // 3 seconds in milliseconds
    
    public CooldownManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.rareCandyCooldowns = new HashMap<>();
        this.candyUseCooldowns = new HashMap<>();
    }
    
    public boolean isOnRareCandyCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        Long cooldownEnd = rareCandyCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        if (currentTime >= cooldownEnd) {
            rareCandyCooldowns.remove(playerId);
            return false;
        }
        
        return true;
    }
    
    public void setRareCandyCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        long cooldownEnd = System.currentTimeMillis() + RARE_CANDY_COOLDOWN;
        rareCandyCooldowns.put(playerId, cooldownEnd);
    }
    
    public long getRareCandyCooldownRemaining(Player player) {
        UUID playerId = player.getUniqueId();
        Long cooldownEnd = rareCandyCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long remaining = cooldownEnd - currentTime;
        
        return Math.max(0, remaining);
    }
    
    public String formatCooldownTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            minutes = minutes % 60;
            return String.format("%dh %dm", hours, minutes);
        } else if (minutes > 0) {
            seconds = seconds % 60;
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }
    
    public void clearCooldown(Player player) {
        rareCandyCooldowns.remove(player.getUniqueId());
    }
    
    public void clearAllCooldowns() {
        rareCandyCooldowns.clear();
        candyUseCooldowns.clear();
    }
    
    // Candy use cooldown methods
    public boolean isOnCandyUseCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        Long cooldownEnd = candyUseCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        if (currentTime >= cooldownEnd) {
            candyUseCooldowns.remove(playerId);
            return false;
        }
        
        return true;
    }
    
    public void setCandyUseCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        long cooldownEnd = System.currentTimeMillis() + CANDY_USE_COOLDOWN;
        candyUseCooldowns.put(playerId, cooldownEnd);
    }
    
    public long getCandyUseCooldownRemaining(Player player) {
        UUID playerId = player.getUniqueId();
        Long cooldownEnd = candyUseCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long remaining = cooldownEnd - currentTime;
        
        return Math.max(0, remaining);
    }
}
