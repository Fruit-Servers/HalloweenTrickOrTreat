package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.handlers.RareTreatHandler;
import com.halloween.trickortreat.handlers.RareTrickHandler;
import com.halloween.trickortreat.rewards.RareTreat;
import com.halloween.trickortreat.rewards.RareTrick;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RareTrickOrTreatManager {
    
    private final TrickOrTreatPlugin plugin;
    private final RareTreatHandler rareTreatHandler;
    private final RareTrickHandler rareTrickHandler;
    private final Random random;
    
    public RareTrickOrTreatManager(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.rareTreatHandler = new RareTreatHandler(plugin);
        this.rareTrickHandler = new RareTrickHandler(plugin);
        this.random = new Random();
    }
    
    public void activateRareTrickOrTreat(Player player) {
        double treatChance = plugin.getConfigManager().getRareTreatChance();
        double roll = random.nextDouble() * 100.0;
        
        if (roll < treatChance) {
            applyRandomRareTreat(player);
        } else {
            applyRandomRareTrick(player);
        }
    }
    
    private void applyRandomRareTreat(Player player) {
        List<RareTreat> weightedTreats = new ArrayList<>();
        
        for (RareTreat treat : RareTreat.values()) {
            if (plugin.getConfigManager().isRareTreatEnabled(treat.getConfigKey())) {
                int weight = plugin.getConfigManager().getRareTreatWeight(treat.getConfigKey());
                for (int i = 0; i < weight; i++) {
                    weightedTreats.add(treat);
                }
            }
        }
        
        if (weightedTreats.isEmpty()) {
            player.sendMessage("§c⚠ No rare treats are available!");
            return;
        }
        
        RareTreat selectedTreat = weightedTreats.get(random.nextInt(weightedTreats.size()));
        rareTreatHandler.applyRareTreat(player, selectedTreat);
        
        String message = plugin.getConfigManager().getMessageNoPrefix("rare-treat-received")
                .replace("{treat}", selectedTreat.getDescription());
        player.sendMessage(message);
    }
    
    private void applyRandomRareTrick(Player player) {
        List<RareTrick> weightedTricks = new ArrayList<>();
        
        for (RareTrick trick : RareTrick.values()) {
            if (plugin.getConfigManager().isRareTrickEnabled(trick.getConfigKey())) {
                int weight = plugin.getConfigManager().getRareTrickWeight(trick.getConfigKey());
                for (int i = 0; i < weight; i++) {
                    weightedTricks.add(trick);
                }
            }
        }
        
        if (weightedTricks.isEmpty()) {
            player.sendMessage("§c⚠ No rare tricks are available!");
            return;
        }
        
        RareTrick selectedTrick = weightedTricks.get(random.nextInt(weightedTricks.size()));
        rareTrickHandler.applyRareTrick(player, selectedTrick);
        
        String message = plugin.getConfigManager().getMessageNoPrefix("rare-trick-received")
                .replace("{trick}", selectedTrick.getDescription());
        player.sendMessage(message);
    }
}
