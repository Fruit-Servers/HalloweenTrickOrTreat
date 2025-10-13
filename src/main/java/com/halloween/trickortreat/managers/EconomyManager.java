package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    
    private final TrickOrTreatPlugin plugin;
    private Economy economy = null;
    
    public EconomyManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        setupEconomy();
    }
    
    private void setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager()
                .getRegistration(Economy.class);
        
        if (rsp == null) {
            return;
        }
        
        economy = rsp.getProvider();
    }
    
    public boolean hasEconomy() {
        return economy != null;
    }
    
    public void depositPlayer(Player player, double amount) {
        if (hasEconomy()) {
            economy.depositPlayer(player, amount);
        }
    }
    
    public String format(double amount) {
        if (hasEconomy()) {
            return economy.format(amount);
        }
        return "$" + amount;
    }
}
