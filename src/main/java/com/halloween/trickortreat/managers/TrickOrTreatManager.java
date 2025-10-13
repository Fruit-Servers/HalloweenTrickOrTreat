package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.handlers.TreatHandler;
import com.halloween.trickortreat.handlers.TrickHandler;
import com.halloween.trickortreat.rewards.Treat;
import com.halloween.trickortreat.rewards.Trick;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrickOrTreatManager {
    
    private final TrickOrTreatPlugin plugin;
    private final TreatHandler treatHandler;
    private final TrickHandler trickHandler;
    private final Random random;
    
    public TrickOrTreatManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.treatHandler = new TreatHandler(plugin);
        this.trickHandler = new TrickHandler(plugin);
        this.random = new Random();
    }
    
    public void activateTrickOrTreat(Player player) {
        double trickChance = plugin.getConfigManager().getTrickChance();
        double roll = random.nextDouble() * 100.0;
        
        if (roll < trickChance) {
            applyRandomTrick(player);
        } else {
            applyRandomTreat(player);
        }
    }
    
    private void applyRandomTreat(Player player) {
        List<Treat> enabledTreats = new ArrayList<>();
        
        for (Treat treat : Treat.values()) {
            if (plugin.getConfigManager().isTreatEnabled(treat.getConfigKey())) {
                enabledTreats.add(treat);
            }
        }
        
        if (enabledTreats.isEmpty()) {
            player.sendMessage("§c⚠ No treats are enabled!");
            return;
        }
        
        Treat selectedTreat = enabledTreats.get(random.nextInt(enabledTreats.size()));
        treatHandler.applyTreat(player, selectedTreat);
        
        String message = plugin.getConfigManager().getMessageNoPrefix("treat-received")
                .replace("{treat}", selectedTreat.getDescription());
        player.sendMessage(message);
    }
    
    private void applyRandomTrick(Player player) {
        List<Trick> enabledTricks = new ArrayList<>();
        
        for (Trick trick : Trick.values()) {
            if (plugin.getConfigManager().isTrickEnabled(trick.getConfigKey())) {
                enabledTricks.add(trick);
            }
        }
        
        if (enabledTricks.isEmpty()) {
            player.sendMessage("§c⚠ No tricks are enabled!");
            return;
        }
        
        Trick selectedTrick = enabledTricks.get(random.nextInt(enabledTricks.size()));
        trickHandler.applyTrick(player, selectedTrick);
        
        String message = plugin.getConfigManager().getMessageNoPrefix("trick-received")
                .replace("{trick}", selectedTrick.getDescription());
        player.sendMessage(message);
    }
}
