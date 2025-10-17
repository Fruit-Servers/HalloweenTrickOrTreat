package com.halloween.trickortreat.handlers;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import com.halloween.trickortreat.rewards.RareTreat;
import org.bukkit.Material;
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
            case COLLECTABLE_PASS:
                applyCollectablePass(player);
                break;
            case FRUITSTERS_KEY:
                applyFruitstersKey(player);
                break;
            case SPOOKEY:
                applySpooKey(player);
                break;
            case BLOCK_OF_NETHERITE:
                applyBlockOfNetherite(player);
                break;
            case WITCH_SPAWNER:
                applyWitchSpawner(player);
                break;
            case SPIDER_SPAWNER:
                applySpiderSpawner(player);
                break;
        }
    }
    
    private void applyToken(Player player) {
        ItemStack token = plugin.getConfigManager().getCustomRareTreatItem("token");
        if (token != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), token);
            player.sendMessage("§a💰 You received a Token!");
        } else {
            player.sendMessage("§c⚠ Token reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyCollectablePass(Player player) {
        ItemStack pass = plugin.getConfigManager().getCustomRareTreatItem("collectpass");
        if (pass != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), pass);
            player.sendMessage("§d🎫 You received a Collectable Pass!");
        } else {
            player.sendMessage("§c⚠ Collectable Pass reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyFruitstersKey(Player player) {
        ItemStack key = plugin.getConfigManager().getCustomRareTreatItem("fruitkey");
        if (key != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), key);
            player.sendMessage("§e🗝️ You received a Fruitsters Key!");
        } else {
            player.sendMessage("§c⚠ Fruitsters Key reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applySpooKey(Player player) {
        ItemStack key = plugin.getConfigManager().getCustomRareTreatItem("spookey");
        if (key != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), key);
            player.sendMessage("§5🎃 You received a SpooKey!");
        } else {
            player.sendMessage("§c⚠ SpooKey reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyBlockOfNetherite(Player player) {
        ItemStack netherite = new ItemStack(Material.NETHERITE_BLOCK);
        player.getWorld().dropItemNaturally(player.getLocation(), netherite);
        player.sendMessage("§8💎 You received a Block of Netherite!");
    }
    
    private void applyWitchSpawner(Player player) {
        ItemStack spawner = plugin.getConfigManager().getCustomRareTreatItem("wspawn");
        if (spawner != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), spawner);
            player.sendMessage("§5🧙 You received a Witch Spawner!");
        } else {
            player.sendMessage("§c⚠ Witch Spawner reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applySpiderSpawner(Player player) {
        ItemStack spawner = plugin.getConfigManager().getCustomRareTreatItem("sspawn");
        if (spawner != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), spawner);
            player.sendMessage("§8🕷️ You received a Spider Spawner!");
        } else {
            player.sendMessage("§c⚠ Spider Spawner reward is not configured! Please contact an administrator.");
        }
    }
}
