package com.halloween.trickortreat.handlers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.rewards.RareTreat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RareTreatHandler {
    
    private final TrickOrTreatPlugin plugin;
    
    public RareTreatHandler(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void applyRareTreat(Player player, RareTreat treat) {
        switch (treat) {
            case TOKEN:
                applyToken(player);
                break;
            case COLLECTPASS:
                applyCollectPass(player);
                break;
            case FRUITKEY:
                applyFruitKey(player);
                break;
            case SPOOKEY:
                applySpookKey(player);
                break;
            case NETHERITE:
                applyNetherite(player);
                break;
            case WSPAWN:
                applyWitherSpawn(player);
                break;
            case SSPAWN:
                applySkeletonSpawn(player);
                break;
        }
    }
    
    private void applyToken(Player player) {
        ItemStack token = plugin.getConfigManager().getCustomRareTreatItem("token");
        if (token != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), token);
            player.sendMessage("§a💰 You received a Halloween Token!");
        } else {
            player.sendMessage("§c⚠ Token reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyCollectPass(Player player) {
        ItemStack collectpass = plugin.getConfigManager().getCustomRareTreatItem("collectpass");
        if (collectpass != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), collectpass);
            player.sendMessage("§b🎫 You received a Collect Pass!");
        } else {
            player.sendMessage("§c⚠ Collect Pass reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyFruitKey(Player player) {
        ItemStack fruitkey = plugin.getConfigManager().getCustomRareTreatItem("fruitkey");
        if (fruitkey != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), fruitkey);
            player.sendMessage("§6🗝️ You received a Fruit Key!");
        } else {
            player.sendMessage("§c⚠ Fruit Key reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applySpookKey(Player player) {
        ItemStack spookey = plugin.getConfigManager().getCustomRareTreatItem("spookey");
        if (spookey != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), spookey);
            player.sendMessage("§5🗝️ You received a Spook Key!");
        } else {
            player.sendMessage("§c⚠ Spook Key reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyNetherite(Player player) {
        ItemStack netherite = plugin.getConfigManager().getCustomRareTreatItem("netherite");
        if (netherite != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), netherite);
            player.sendMessage("§8💎 You received Netherite!");
        } else {
            player.sendMessage("§c⚠ Netherite reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyWitherSpawn(Player player) {
        ItemStack wspawn = plugin.getConfigManager().getCustomRareTreatItem("wspawn");
        if (wspawn != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), wspawn);
            player.sendMessage("§0💀 You received a Wither Spawn!");
        } else {
            player.sendMessage("§c⚠ Wither Spawn reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applySkeletonSpawn(Player player) {
        ItemStack sspawn = plugin.getConfigManager().getCustomRareTreatItem("sspawn");
        if (sspawn != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), sspawn);
            player.sendMessage("§f💀 You received a Skeleton Spawn!");
        } else {
            player.sendMessage("§c⚠ Skeleton Spawn reward is not configured! Please contact an administrator.");
        }
    }
}
