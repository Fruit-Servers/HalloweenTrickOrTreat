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
    
    // Cached config values to avoid repeated file access
    private boolean candyDropEnabled;
    private double candyDropChance;
    private boolean canDropFromHostile;
    private boolean canDropFromPassive;
    private boolean canDropFromBoss;
    private double trickChance;
    private double rareTreatChance;
    private double rareTrickChance;
    private String messagePrefix;
    
    public ConfigManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        loadConfigValues();
    }
    
    public void loadConfigValues() {
        this.candyDropEnabled = config.getBoolean("candy-drop.enabled", true);
        this.candyDropChance = config.getDouble("candy-drop.drop-chance", 15.0);
        this.canDropFromHostile = config.getBoolean("candy-drop.drop-from-hostile", true);
        this.canDropFromPassive = config.getBoolean("candy-drop.drop-from-passive", true);
        this.canDropFromBoss = config.getBoolean("candy-drop.drop-from-boss", true);
        this.trickChance = config.getDouble("trick-or-treat.trick-chance", 50.0);
        this.rareTreatChance = config.getDouble("rare-treats.treat-chance", 50.0);
        this.rareTrickChance = config.getDouble("rare-tricks.trick-chance", 50.0);
        this.messagePrefix = config.getString("messages.prefix", "&6[&c🎃&6] &r");
    }
    
    public boolean isCandyDropEnabled() {
        return candyDropEnabled;
    }
    
    public double getCandyDropChance() {
        return candyDropChance;
    }
    
    public boolean canDropFromHostile() {
        return canDropFromHostile;
    }
    
    public boolean canDropFromPassive() {
        return canDropFromPassive;
    }
    
    public boolean canDropFromBoss() {
        return canDropFromBoss;
    }
    
    public double getTrickChance() {
        return trickChance;
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
        String message = config.getString("messages." + key, "");
        return ChatColor.translateAlternateColorCodes('&', messagePrefix + message);
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
        return rareTreatChance;
    }
    
    public double getRareTrickChance() {
        return rareTrickChance;
    }
    
    public int getRareTreatWeight(String treatKey) {
        return config.getInt("rare-treats.individual-chances." + treatKey, 10);
    }
    
    public int getRareTrickWeight(String trickKey) {
        return config.getInt("rare-tricks.individual-chances." + trickKey, 20);
    }
}
