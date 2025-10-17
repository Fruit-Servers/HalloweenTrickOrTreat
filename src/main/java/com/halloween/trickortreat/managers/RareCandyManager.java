package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RareCandyManager {
    
    private final TrickOrTreatPlugin plugin;
    private final NamespacedKey rareCandyKey;
    private final Map<UUID, Long> lastRareCandyDrop;
    private static final long RARE_CANDY_COOLDOWN = 60 * 60 * 1000L;
    
    public RareCandyManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.rareCandyKey = new NamespacedKey(plugin, "rare_halloween_candy");
        this.lastRareCandyDrop = new HashMap<>();
    }
    
    public ItemStack createRareCandyItem() {
        ItemStack candy = new ItemStack(Material.BLUE_DYE);
        ItemMeta meta = candy.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', 
                "&b&l🎃 Rare Halloween Candy - Trick or Treat!"));
            
            meta.setLore(Arrays.asList(
                ChatColor.translateAlternateColorCodes('&', "&7Right-click to consume this rare candy"),
                ChatColor.translateAlternateColorCodes('&', "&7and receive either a &aRare Treat &7or a &cRare Trick&7!"),
                ChatColor.translateAlternateColorCodes('&', ""),
                ChatColor.translateAlternateColorCodes('&', "&e50% chance for each outcome"),
                ChatColor.translateAlternateColorCodes('&', "&d&lRARE CANDY")
            ));
            
            meta.addEnchant(Enchantment.LUCK, 2, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            
            meta.getPersistentDataContainer().set(rareCandyKey, PersistentDataType.BYTE, (byte) 1);
            
            candy.setItemMeta(meta);
        }
        
        return candy;
    }
    
    public boolean isRareCandyItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(rareCandyKey, PersistentDataType.BYTE);
    }
    
    public boolean canReceiveRareCandy(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        
        if (!lastRareCandyDrop.containsKey(playerId)) {
            return true;
        }
        
        long lastDrop = lastRareCandyDrop.get(playerId);
        return (currentTime - lastDrop) >= RARE_CANDY_COOLDOWN;
    }
    
    public void recordRareCandyDrop(Player player) {
        lastRareCandyDrop.put(player.getUniqueId(), System.currentTimeMillis());
    }
    
    public long getRemainingCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        if (!lastRareCandyDrop.containsKey(playerId)) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long lastDrop = lastRareCandyDrop.get(playerId);
        long elapsed = currentTime - lastDrop;
        
        return Math.max(0, RARE_CANDY_COOLDOWN - elapsed);
    }
}
