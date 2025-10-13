package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

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
}
