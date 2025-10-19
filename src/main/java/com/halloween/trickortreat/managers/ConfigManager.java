package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.utils.ItemSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ConfigManager {
    
    private final TrickOrTreatPlugin plugin;
    private final FileConfiguration config;
    
    public ConfigManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }
    
    public boolean isCandyDropEnabled() {
        return config.getBoolean("candy-drop.enabled", true);
    }
    
    public double getCandyDropChance() {
        return config.getDouble("candy-drop.drop-chance", 15.0);
    }
    
    public boolean canDropFromHostile() {
        return config.getBoolean("candy-drop.drop-from-hostile", true);
    }
    
    public boolean canDropFromPassive() {
        return config.getBoolean("candy-drop.drop-from-passive", true);
    }
    
    public boolean canDropFromBoss() {
        return config.getBoolean("candy-drop.drop-from-boss", true);
    }
    
    public double getTrickChance() {
        return config.getDouble("trick-or-treat.trick-chance", 50.0);
    }
    
    public boolean isTreatEnabled(String treatName) {
        return config.getBoolean("treats." + treatName + ".enabled", true);
    }
    
    public boolean isTrickEnabled(String trickName) {
        return config.getBoolean("tricks." + trickName + ".enabled", true);
    }
    
    public int getInt(String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }
    
    public double getDouble(String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }
    
    public String getString(String path, String defaultValue) {
        return config.getString(path, defaultValue);
    }
    
    public String getMessage(String key) {
        String prefix = config.getString("messages.prefix", "&6[&c🎃&6] &r");
        String message = config.getString("messages." + key, "");
        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }
    
    public String getMessageNoPrefix(String key) {
        String message = config.getString("messages." + key, "");
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public FileConfiguration getConfig() {
        return config;
    }
    
    public ItemStack getCustomRareTreatItem(String itemKey) {
        ConfigurationSection section = config.getConfigurationSection("rare-treats.items." + itemKey);
        return ItemSerializer.loadItemFromConfig(section);
    }
    
    public void setCustomRareTreatItem(String itemKey, ItemStack item) {
        ConfigurationSection section = config.createSection("rare-treats.items." + itemKey);
        ItemSerializer.saveItemToConfig(section, item);
        plugin.saveConfig();
    }
    
    public boolean isRareTreatEnabled(String itemKey) {
        return config.getBoolean("rare-treats.items." + itemKey + ".enabled", true);
    }
    
    public boolean isRareTrickEnabled(String trickKey) {
        return config.getBoolean("rare-tricks.enabled." + trickKey, true);
    }
    
    public double getRareTreatChance() {
        return config.getDouble("rare-treats.treat-chance", 50.0);
    }
    
    public double getRareTrickChance() {
        return config.getDouble("rare-tricks.trick-chance", 50.0);
    }
    
    public int getRareTreatWeight(String treatKey) {
        return config.getInt("rare-treats.individual-chances." + treatKey, 10);
    }
    
    public int getRareTrickWeight(String trickKey) {
        return config.getInt("rare-tricks.individual-chances." + trickKey, 20);
    }
    
    // Pixelmon-specific configuration methods
    public boolean isPixelmonDropEnabled() {
        return config.getBoolean("pixelmon-drops.enabled", true);
    }
    
    public double getPixelmonDropChance() {
        return config.getDouble("pixelmon-drops.drop-chance", 15.0);
    }
    
    public double getPixelmonRareChance() {
        return config.getDouble("pixelmon-drops.rare-chance", 2.0);
    }
    
    public boolean canDropFromCapture() {
        return config.getBoolean("pixelmon-drops.from-capture", true);
    }
    
    public boolean canDropFromLegendary() {
        return config.getBoolean("pixelmon-drops.from-legendary", true);
    }
    
    public boolean canDropFromUltraBeast() {
        return config.getBoolean("pixelmon-drops.from-ultra-beast", true);
    }
    
    public boolean hasShinyBonus() {
        return config.getBoolean("pixelmon-drops.shiny-bonus", true);
    }
    
    public double getShinyDropChance() {
        return config.getDouble("pixelmon-drops.shiny-drop-chance", 30.0);
    }
}
