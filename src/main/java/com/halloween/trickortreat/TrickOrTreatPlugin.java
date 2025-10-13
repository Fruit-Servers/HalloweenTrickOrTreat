package com.halloween.trickortreat;

import com.halloween.trickortreat.commands.TrickOrTreatCommand;
import com.halloween.trickortreat.listeners.CandyDropListener;
import com.halloween.trickortreat.listeners.CandyUseListener;
import com.halloween.trickortreat.managers.CandyManager;
import com.halloween.trickortreat.managers.ConfigManager;
import com.halloween.trickortreat.managers.EconomyManager;
import com.halloween.trickortreat.managers.TrickOrTreatManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TrickOrTreatPlugin extends JavaPlugin {
    
    private static TrickOrTreatPlugin instance;
    private ConfigManager configManager;
    private CandyManager candyManager;
    private TrickOrTreatManager trickOrTreatManager;
    private EconomyManager economyManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        this.configManager = new ConfigManager(this);
        this.candyManager = new CandyManager(this);
        this.economyManager = new EconomyManager(this);
        this.trickOrTreatManager = new TrickOrTreatManager(this);
        
        getServer().getPluginManager().registerEvents(new CandyDropListener(this), this);
        getServer().getPluginManager().registerEvents(new CandyUseListener(this), this);
        
        TrickOrTreatCommand commandHandler = new TrickOrTreatCommand(this);
        getCommand("trickortreat").setExecutor(commandHandler);
        getCommand("trickortreat").setTabCompleter(commandHandler);
        
        getLogger().info("🎃 Halloween Trick or Treat plugin has been enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("🎃 Halloween Trick or Treat plugin has been disabled!");
    }
    
    public static TrickOrTreatPlugin getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public CandyManager getCandyManager() {
        return candyManager;
    }
    
    public TrickOrTreatManager getTrickOrTreatManager() {
        return trickOrTreatManager;
    }
    
    public EconomyManager getEconomyManager() {
        return economyManager;
    }
    
    public void reloadPluginConfig() {
        reloadConfig();
        configManager = new ConfigManager(this);
        getLogger().info("Configuration reloaded!");
    }
}
