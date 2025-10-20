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
            case EVENT_MEDAL:
                applyEventMedal(player);
                break;
            case TOKEN:
                applyToken(player);
                break;
            case TRADING_CARD:
                applyTradingCard(player);
                break;
            case ANCIENT_DEBRIS:
                applyAncientDebris(player);
                break;
            case MONEY:
                applyMoney(player);
                break;
            case VOTE_SHARD:
                applyVoteShard(player);
                break;
            case END_VOUCHER:
                applyEndVoucher(player);
                break;
            case NETHER_VOUCHER:
                applyNetherVoucher(player);
                break;
            case DEEP_DARK_VOUCHER:
                applyDeepDarkVoucher(player);
                break;
            case GEODE_FORTUNE:
                applyGeodeFortune(player);
                break;
            case GOD_VOUCHER:
                applyGodVoucher(player);
                break;
        }
    }
    
    private void applyEventMedal(Player player) {
        ItemStack medal = plugin.getConfigManager().getCustomRareTreatItem("event-medal");
        if (medal != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), medal);
            player.sendMessage("§6🏅 You received a Skyblock Event Medal!");
        } else {
            player.sendMessage("§c⚠ Event Medal reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyToken(Player player) {
        ItemStack token = plugin.getConfigManager().getCustomRareTreatItem("token");
        if (token != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), token);
            player.sendMessage("§a💰 You received a Skyblock Token!");
        } else {
            player.sendMessage("§c⚠ Token reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyTradingCard(Player player) {
        // Execute the trading card command
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
            "cards giverandomcard RARE " + player.getName() + " 1");
        player.sendMessage("§d🃏 You've received a trading card!");
    }
    
    private void applyAncientDebris(Player player) {
        ItemStack ancientDebris = new ItemStack(Material.ANCIENT_DEBRIS, 2);
        player.getWorld().dropItemNaturally(player.getLocation(), ancientDebris);
        player.sendMessage("§8💎 You received 2 Ancient Debris!");
    }
    
    private void applyMoney(Player player) {
        // Execute the economy command
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
            "eco give " + player.getName() + " 5000");
        player.sendMessage("§2💵 You received $5000!");
    }
    
    private void applyVoteShard(Player player) {
        ItemStack shard = plugin.getConfigManager().getCustomRareTreatItem("vote-shard");
        if (shard != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), shard);
            player.sendMessage("§b💎 You received 1 Vote Shard!");
        } else {
            player.sendMessage("§c⚠ Vote Shard reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyEndVoucher(Player player) {
        ItemStack voucher = plugin.getConfigManager().getCustomRareTreatItem("end-voucher");
        if (voucher != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), voucher);
            player.sendMessage("§5🎫 You received a 5 minute End Voucher!");
        } else {
            player.sendMessage("§c⚠ End Voucher reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyNetherVoucher(Player player) {
        ItemStack voucher = plugin.getConfigManager().getCustomRareTreatItem("nether-voucher");
        if (voucher != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), voucher);
            player.sendMessage("§c🎫 You received a 5 minute Nether Voucher!");
        } else {
            player.sendMessage("§c⚠ Nether Voucher reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyDeepDarkVoucher(Player player) {
        ItemStack voucher = plugin.getConfigManager().getCustomRareTreatItem("deep-dark-voucher");
        if (voucher != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), voucher);
            player.sendMessage("§0🎫 You received a 5 minute Deep Dark Voucher!");
        } else {
            player.sendMessage("§c⚠ Deep Dark Voucher reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyGeodeFortune(Player player) {
        ItemStack geode = plugin.getConfigManager().getCustomRareTreatItem("geode-fortune");
        if (geode != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), geode);
            player.sendMessage("§e📖 You received a Geode of Fortune Enchanted Book!");
        } else {
            player.sendMessage("§c⚠ Geode of Fortune reward is not configured! Please contact an administrator.");
        }
    }
    
    private void applyGodVoucher(Player player) {
        ItemStack voucher = plugin.getConfigManager().getCustomRareTreatItem("god-voucher");
        if (voucher != null) {
            player.getWorld().dropItemNaturally(player.getLocation(), voucher);
            player.sendMessage("§6🎫 You received a 30 minute God Voucher!");
        } else {
            player.sendMessage("§c⚠ God Voucher reward is not configured! Please contact an administrator.");
        }
    }
}
