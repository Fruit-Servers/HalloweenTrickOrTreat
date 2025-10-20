package com.halloween.trickortreat.listeners;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CandyUseListener implements Listener {
    
    private final TrickOrTreatPlugin plugin;
    
    public CandyUseListener(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && 
            event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        boolean isRegularCandy = plugin.getCandyManager().isCandyItem(item);
        boolean isRareCandy = plugin.getRareCandyManager().isRareCandyItem(item);
        
        if (item == null || (!isRegularCandy && !isRareCandy)) {
            return;
        }
        
        event.setCancelled(true);
        
        if (!player.hasPermission("trickortreat.use")) {
            player.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return;
        }
        
        // Check for use cooldown
        if (plugin.getCooldownManager().isOnCandyUseCooldown(player)) {
            long remaining = plugin.getCooldownManager().getCandyUseCooldownRemaining(player);
            double seconds = remaining / 1000.0;
            player.sendMessage(String.format("§c⏰ You must wait %.1f seconds before using another candy!", seconds));
            return;
        }
        
        item.setAmount(item.getAmount() - 1);
        plugin.getCooldownManager().setCandyUseCooldown(player);
        
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
        
        if (isRareCandy) {
            plugin.getRareTrickOrTreatManager().activateRareTrickOrTreat(player);
        } else {
            plugin.getTrickOrTreatManager().activateTrickOrTreat(player);
        }
    }
}
