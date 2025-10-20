package com.halloween.trickortreat.commands;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class TriggerCandyDropCommand implements CommandExecutor {
    
    private final TrickOrTreatPlugin plugin;
    private final Random random;
    
    public TriggerCandyDropCommand(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /triggercandydrop <player> [force-rare]");
            return true;
        }
        
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return true;
        }
        
        boolean forceRare = args.length > 1 && args[1].equalsIgnoreCase("true");
        
        // Execute the candy drop logic
        boolean dropped = executeCandyDropLogic(target, forceRare);
        
        if (dropped) {
            sender.sendMessage("§aCandy drop triggered for " + target.getName());
        } else {
            sender.sendMessage("§eNo candy dropped for " + target.getName() + " (cooldown or chance)");
        }
        
        return true;
    }
    
    /**
     * Executes the complete candy drop logic including cooldowns and random chances
     * @param player The player to potentially give candy to
     * @param forceRare Whether to force a rare candy (ignores random chance but respects cooldown)
     * @return true if any candy was dropped, false otherwise
     */
    public boolean executeCandyDropLogic(Player player, boolean forceRare) {
        // Check if candy drops are enabled
        if (!plugin.getConfigManager().isCandyDropEnabled()) {
            return false;
        }
        
        // Check base drop chance (unless forcing rare)
        if (!forceRare) {
            double dropChance = plugin.getConfigManager().getCandyDropChance();
            double roll = random.nextDouble() * 100.0;
            
            if (roll > dropChance) {
                return false; // No drop at all
            }
        }
        
        // Determine if should give rare candy
        boolean shouldGiveRareCandy;
        if (forceRare) {
            shouldGiveRareCandy = plugin.getRareCandyManager().canReceiveRareCandy(player);
        } else {
            double rareRoll = random.nextDouble() * 100.0;
            shouldGiveRareCandy = rareRoll <= 1.0 && plugin.getRareCandyManager().canReceiveRareCandy(player);
        }
        
        // Execute drop logic
        if (shouldGiveRareCandy && !plugin.getCooldownManager().isOnRareCandyCooldown(player)) {
            // Give rare candy
            ItemStack rareCandy = plugin.getRareCandyManager().createRareCandyItem();
            player.getWorld().dropItemNaturally(player.getLocation(), rareCandy);
            plugin.getRareCandyManager().recordRareCandyDrop(player);
            plugin.getCooldownManager().setRareCandyCooldown(player);
            
            player.sendMessage(plugin.getConfigManager().getMessage("rare-candy-received"));
            return true;
            
        } else if (shouldGiveRareCandy && plugin.getCooldownManager().isOnRareCandyCooldown(player)) {
            // Rare candy was rolled but player is on cooldown - do nothing
            return false;
            
        } else if (!forceRare) {
            // Give regular candy (only if not forcing rare)
            ItemStack candy = plugin.getCandyManager().createCandyItem();
            player.getWorld().dropItemNaturally(player.getLocation(), candy);
            
            player.sendMessage(plugin.getConfigManager().getMessage("candy-received"));
            return true;
        }
        
        return false;
    }
    
    /**
     * Simple version that just checks if a player can receive a rare candy
     * @param player The player to check
     * @return true if player can receive rare candy (not on cooldown and meets requirements)
     */
    public boolean canPlayerReceiveRareCandy(Player player) {
        return plugin.getRareCandyManager().canReceiveRareCandy(player) && 
               !plugin.getCooldownManager().isOnRareCandyCooldown(player);
    }
    
    /**
     * Get remaining cooldown time for rare candy
     * @param player The player to check
     * @return remaining cooldown in milliseconds, 0 if not on cooldown
     */
    public long getRareCandyCooldownRemaining(Player player) {
        return plugin.getCooldownManager().getRareCandyCooldownRemaining(player);
    }
    
    /**
     * Force give a rare candy (bypasses random chance but respects cooldown)
     * @param player The player to give candy to
     * @return true if candy was given, false if on cooldown
     */
    public boolean forceGiveRareCandy(Player player) {
        return executeCandyDropLogic(player, true);
    }
    
    /**
     * Force give a regular candy (always succeeds unless drops are disabled)
     * @param player The player to give candy to
     * @return true if candy was given
     */
    public boolean forceGiveRegularCandy(Player player) {
        if (!plugin.getConfigManager().isCandyDropEnabled()) {
            return false;
        }
        
        ItemStack candy = plugin.getCandyManager().createCandyItem();
        player.getWorld().dropItemNaturally(player.getLocation(), candy);
        player.sendMessage(plugin.getConfigManager().getMessage("candy-received"));
        return true;
    }
}
