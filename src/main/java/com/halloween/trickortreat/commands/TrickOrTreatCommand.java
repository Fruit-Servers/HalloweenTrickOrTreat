package com.halloween.trickortreat.commands;

import com.halloween.trickortreat.TrickOrTreatPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
            case "giverare":
                handleGiveRare(sender, args);
                break;
            case "set":
                handleSet(sender, args);
                break;
            case "cooldown":
                handleCooldown(sender, args);
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
    
    private void handleGiveRare(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /trickortreat giverare <player> [amount]");
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
        
        ItemStack rareCandy = plugin.getRareCandyManager().createRareCandyItem();
        rareCandy.setAmount(amount);
        target.getInventory().addItem(rareCandy);
        
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Gave " + amount + " Rare Halloween Candy to " + target.getName() + "!");
    }
    
    private void handleSet(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return;
        }
        
        if (args.length < 3 || !args[1].equalsIgnoreCase("item")) {
            sender.sendMessage(ChatColor.RED + "Usage: /trickortreat set item <token|collectpass|fruitkey|spookey|wspawn|sspawn>");
            return;
        }
        
        Player player = (Player) sender;
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        
        if (heldItem == null || heldItem.getType() == Material.AIR) {
            sender.sendMessage(ChatColor.RED + "You must be holding an item to set it as a reward!");
            return;
        }
        
        String itemKey = args[2].toLowerCase();
        String[] validKeys = {"token", "collectpass", "fruitkey", "spookey", "wspawn", "sspawn"};
        
        boolean validKey = false;
        for (String key : validKeys) {
            if (key.equals(itemKey)) {
                validKey = true;
                break;
            }
        }
        
        if (!validKey) {
            sender.sendMessage(ChatColor.RED + "Invalid item type! Valid types: token, collectpass, fruitkey, spookey, wspawn, sspawn");
            return;
        }
        
        plugin.getConfigManager().setCustomRareTreatItem(itemKey, heldItem.clone());
        
        String itemName = heldItem.hasItemMeta() && heldItem.getItemMeta().hasDisplayName() 
            ? heldItem.getItemMeta().getDisplayName() 
            : heldItem.getType().name();
        
        sender.sendMessage(ChatColor.GREEN + "Successfully set " + itemKey + " reward to: " + itemName);
    }
    
    private void handleCooldown(CommandSender sender, String[] args) {
        if (!sender.hasPermission("trickortreat.admin")) {
            sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return;
        }
        
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /trickortreat cooldown <check|clear> [player]");
            return;
        }
        
        String action = args[1].toLowerCase();
        
        switch (action) {
            case "check":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /trickortreat cooldown check <player>");
                    return;
                }
                
                Player target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found: " + args[2]);
                    return;
                }
                
                if (plugin.getCooldownManager().isOnRareCandyCooldown(target)) {
                    long remaining = plugin.getCooldownManager().getRareCandyCooldownRemaining(target);
                    String timeLeft = plugin.getCooldownManager().formatCooldownTime(remaining);
                    sender.sendMessage(ChatColor.YELLOW + target.getName() + " has " + timeLeft + " remaining on rare candy cooldown.");
                } else {
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " is not on rare candy cooldown.");
                }
                break;
                
            case "clear":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /trickortreat cooldown clear <player|all>");
                    return;
                }
                
                if (args[2].equalsIgnoreCase("all")) {
                    plugin.getCooldownManager().clearAllCooldowns();
                    sender.sendMessage(ChatColor.GREEN + "Cleared all rare candy cooldowns!");
                } else {
                    Player targetPlayer = Bukkit.getPlayer(args[2]);
                    if (targetPlayer == null) {
                        sender.sendMessage(ChatColor.RED + "Player not found: " + args[2]);
                        return;
                    }
                    
                    plugin.getCooldownManager().clearCooldown(targetPlayer);
                    sender.sendMessage(ChatColor.GREEN + "Cleared rare candy cooldown for " + targetPlayer.getName() + "!");
                }
                break;
                
            default:
                sender.sendMessage(ChatColor.RED + "Usage: /trickortreat cooldown <check|clear> [player]");
                break;
        }
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== Halloween Trick or Treat ===");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat reload " + ChatColor.GRAY + "- Reload the configuration");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat give <player> [amount] " + ChatColor.GRAY + "- Give candy to a player");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat giverare <player> [amount] " + ChatColor.GRAY + "- Give rare candy to a player");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat set item <type> " + ChatColor.GRAY + "- Set custom item for rare treat reward");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat cooldown <check|clear> [player] " + ChatColor.GRAY + "- Manage rare candy cooldowns");
        sender.sendMessage(ChatColor.YELLOW + "/trickortreat help " + ChatColor.GRAY + "- Show this help message");
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.addAll(Arrays.asList("reload", "give", "giverare", "set", "cooldown", "help"));
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("giverare"))) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            completions.add("item");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cooldown")) {
            completions.addAll(Arrays.asList("check", "clear"));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("item")) {
            completions.addAll(Arrays.asList("token", "collectpass", "fruitkey", "spookey", "wspawn", "sspawn"));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("cooldown")) {
            if (args[1].equalsIgnoreCase("clear")) {
                completions.add("all");
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("giverare"))) {
            completions.addAll(Arrays.asList("1", "5", "10", "16", "32", "64"));
        }
        
        return completions;
    }
}
