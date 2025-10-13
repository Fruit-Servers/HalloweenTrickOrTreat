package com.halloween.trickortreat.commands;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrickOrTreatCommand implements CommandExecutor, TabCompleter {
    
    private final TrickOrTreatPlugin plugin;
    
    public TrickOrTreatCommand(TrickOrTreatPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("trickortreat.admin")) {
            sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;
            case "give":
                handleGive(sender, args);
                break;
            case "help":
                sendHelp(sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /trickortreat help");
                break;
        }
        
        return true;
    }
    
    private void handleReload(CommandSender sender) {
        plugin.reloadPluginConfig();
        sender.sendMessage(plugin.getConfigManager().getMessage("config-reloaded"));
    }
    
    private void handleGive(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /trickortreat give <player> [amount]");
            return;
        }
        
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return;
        }
        
        int amount = 1;
        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
                if (amount < 1 || amount > 64) {
                    sender.sendMessage(ChatColor.RED + "Amount must be between 1 and 64!");
                    return;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid amount!");
                return;
            }
        }
        
        ItemStack candy = plugin.getCandyManager().createCandyItem();
        candy.setAmount(amount);
        target.getInventory().addItem(candy);
        
        String message = plugin.getConfigManager().getMessage("candy-given")
                .replace("{amount}", String.valueOf(amount))
                .replace("{player}", target.getName());
        sender.sendMessage(message);
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== Halloween Trick or Treat ===");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat reload " + ChatColor.GRAY + "- Reload the configuration");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat give <player> [amount] " + ChatColor.GRAY + "- Give candy to a player");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat help " + ChatColor.GRAY + "- Show this help message");
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.addAll(Arrays.asList("reload", "give", "help"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            completions.addAll(Arrays.asList("1", "5", "10", "16", "32", "64"));
        }
        
        return completions;
    }
}
