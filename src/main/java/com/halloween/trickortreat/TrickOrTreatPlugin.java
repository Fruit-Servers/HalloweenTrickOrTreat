package com.halloween.trickortreat;

import com.halloween.trickortreat.commands.TrickOrTreatCommand;
import com.halloween.trickortreat.listeners.CandyDropListener;
import com.halloween.trickortreat.listeners.CandyUseListener;
import com.halloween.trickortreat.managers.CandyManager;
import com.halloween.trickortreat.managers.ConfigManager;
import com.halloween.trickortreat.managers.CooldownManager;
import com.halloween.trickortreat.managers.EconomyManager;
import com.halloween.trickortreat.managers.RareCandyManager;
import com.halloween.trickortreat.managers.RareTrickOrTreatManager;
import com.halloween.trickortreat.managers.TrickOrTreatManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TrickOrTreatPlugin extends JavaPlugin {
    
    private static TrickOrTreatPlugin instance;
    private ConfigManager configManager;
    private CandyManager candyManager;
    private RareCandyManager rareCandyManager;
    private TrickOrTreatManager trickOrTreatManager;
    private RareTrickOrTreatManager rareTrickOrTreatManager;
    private EconomyManager economyManager;
    private CooldownManager cooldownManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        this.configManager = new ConfigManager(this);
        this.candyManager = new CandyManager(this);
        this.rareCandyManager = new RareCandyManager(this);
        this.economyManager = new EconomyManager(this);
        this.cooldownManager = new CooldownManager(this);
        this.trickOrTreatManager = new TrickOrTreatManager(this);
        this.rareTrickOrTreatManager = new RareTrickOrTreatManager(this);
        
        getServer().getPluginManager().registerEvents(new CandyDropListener(this), this);
        getServer().getPluginManager().registerEvents(new CandyUseListener(this), this);
        
        // Register Pixelmon listener if Pixelmon is present
        if (getServer().getPluginManager().getPlugin("Pixelmon") != null) {
            try {
                Class.forName("com.pixelmonmod.pixelmon.Pixelmon");
                registerPixelmonListener();
                getLogger().info("🎃 Pixelmon support enabled!");
            } catch (ClassNotFoundException e) {
                getLogger().warning("Pixelmon plugin found but Pixelmon classes not available");
            }
        }
        
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
    
    public RareCandyManager getRareCandyManager() {
        return rareCandyManager;
    }
    
    public RareTrickOrTreatManager getRareTrickOrTreatManager() {
        return rareTrickOrTreatManager;
    }
    
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
    
    public void reloadPluginConfig() {
        reloadConfig();
        configManager = new ConfigManager(this);
        getLogger().info("Configuration reloaded!");
    }
    
    private void registerPixelmonListener() {
        try {
            // Use reflection to register Pixelmon listener to avoid compile-time dependency
            Class<?> pixelmonClass = Class.forName("com.pixelmonmod.pixelmon.Pixelmon");
            Object pixelmonInstance = pixelmonClass.getField("instance").get(null);
            
            Class<?> eventBusClass = Class.forName("net.minecraftforge.common.MinecraftForge");
            Object eventBus = eventBusClass.getField("EVENT_BUS").get(null);
            
            // Create and register our listener using reflection
            Class<?> listenerClass = Class.forName("com.halloween.trickortreat.listeners.PixelmonDeathListener");
            Object listener = listenerClass.getConstructor(TrickOrTreatPlugin.class).newInstance(this);
            eventBus.getClass().getMethod("register", Object.class).invoke(eventBus, listener);
            
            getLogger().info("Successfully registered Pixelmon event listener!");
        } catch (Exception e) {
            getLogger().warning("Failed to register Pixelmon listener: " + e.getMessage());
        }
    }
}
