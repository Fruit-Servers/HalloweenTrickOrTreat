package com.halloween.trickortreat.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemSerializer {
    
    public static void saveItemToConfig(ConfigurationSection section, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        
        section.set("material", item.getType().name());
        section.set("amount", item.getAmount());
        
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasDisplayName()) {
                section.set("name", meta.getDisplayName());
            }
            
            if (meta.hasLore()) {
                section.set("lore", meta.getLore());
            }
            
            if (!meta.getEnchants().isEmpty()) {
                List<String> enchants = new ArrayList<>();
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchants.add(entry.getKey().getKey().getKey() + ":" + entry.getValue());
                }
                section.set("enchantments", enchants);
            }
            
            if (!meta.getItemFlags().isEmpty()) {
                List<String> flags = new ArrayList<>();
                for (ItemFlag flag : meta.getItemFlags()) {
                    flags.add(flag.name());
                }
                section.set("item-flags", flags);
            }
        }
        
        section.set("enabled", true);
    }
    
    public static ItemStack loadItemFromConfig(ConfigurationSection section) {
        if (section == null || !section.getBoolean("enabled", false)) {
            return null;
        }
        
        String materialName = section.getString("material");
        if (materialName == null) {
            return null;
        }
        
        Material material;
        try {
            material = Material.valueOf(materialName);
        } catch (IllegalArgumentException e) {
            return null;
        }
        
        int amount = section.getInt("amount", 1);
        ItemStack item = new ItemStack(material, amount);
        
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String name = section.getString("name");
            if (name != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }
            
            List<String> lore = section.getStringList("lore");
            if (!lore.isEmpty()) {
                List<String> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                meta.setLore(coloredLore);
            }
            
            List<String> enchants = section.getStringList("enchantments");
            for (String enchantString : enchants) {
                String[] parts = enchantString.split(":");
                if (parts.length == 2) {
                    try {
                        Enchantment enchant = Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(parts[0]));
                        int level = Integer.parseInt(parts[1]);
                        if (enchant != null) {
                            meta.addEnchant(enchant, level, true);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            
            List<String> flags = section.getStringList("item-flags");
            for (String flagString : flags) {
                try {
                    ItemFlag flag = ItemFlag.valueOf(flagString);
                    meta.addItemFlags(flag);
                } catch (IllegalArgumentException ignored) {
                }
            }
            
            item.setItemMeta(meta);
        }
        
        return item;
    }
}
