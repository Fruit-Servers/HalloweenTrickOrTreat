package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import java.util.Arrays;

public class CandyManager {
    
    private final TrickOrTreatPlugin plugin;
    private final NamespacedKey candyKey;
    
    public CandyManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.candyKey = new NamespacedKey(plugin, "halloween_candy");
    }
    
    public ItemStack createCandyItem() {
        ItemStack candy = new ItemStack(Material.ORANGE_DYE);
        ItemMeta meta = candy.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', 
                "&6&l🎃 Halloween Candy - Trick or Treat!"));
            
            meta.setLore(Arrays.asList(
                ChatColor.translateAlternateColorCodes('&', "&7Right-click to consume this candy"),
                ChatColor.translateAlternateColorCodes('&', "&7and receive either a &aTreat &7or a &cTrick&7!"),
                ChatColor.translateAlternateColorCodes('&', ""),
                ChatColor.translateAlternateColorCodes('&', "&e50% chance for each outcome")
            ));
            
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            
            meta.getPersistentDataContainer().set(candyKey, PersistentDataType.BYTE, (byte) 1);
            
            candy.setItemMeta(meta);
        }
        
        return candy;
    }
    
    public boolean isCandyItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(candyKey, PersistentDataType.BYTE);
    }
}
