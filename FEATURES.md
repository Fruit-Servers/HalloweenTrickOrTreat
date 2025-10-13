# 🎃 Halloween Trick or Treat - Feature Reference

## Quick Start

1. **Build the plugin**: Run `./build.sh` or `./gradlew clean build`
2. **Install**: Copy `build/libs/TrickOrTreat-1.0.0.jar` to your server's `plugins` folder
3. **Configure**: Edit `plugins/HalloweenTrickOrTreat/config.yml` after first run
4. **Reload**: Use `/trickortreat reload` to apply config changes

## How It Works

### For Players
1. Kill any mob (hostile, passive, or boss)
2. 15% chance to drop **Halloween Candy** (configurable)
3. Right-click the candy to consume it
4. 50/50 chance of getting a Treat or Trick!

### For Admins
- `/trickortreat give <player> [amount]` - Give candy to players
- `/trickortreat reload` - Reload configuration
- `/trickortreat help` - Show help

## All Treats (Positive Rewards)

| Treat | Effect | Duration/Amount |
|-------|--------|-----------------|
| **Speedy Treat** | Speed II | 30 seconds |
| **High Jumps** | Jump Boost II | 30 seconds |
| **Candy Cash** | Money reward | $50-$200 |
| **XP Rush** | Experience points | 50-100 XP |
| **Nourishing Nibble** | Restore hunger | 4 drumsticks |
| **Spectral Sight** | See mobs through walls | 20 seconds |
| **Midas Touch** | Spawn valuable items | 1-3 items |
| **Instant Repair** | Repair held item | 5-10% durability |
| **Health Surge** | Extra golden hearts | 45 seconds |

## All Tricks (Negative Effects)

| Trick | Effect | Duration/Amount |
|-------|--------|-----------------|
| **Temporary Blindness** | Blindness | 10 seconds |
| **Unlucky Strike** | Lightning effect | Instant |
| **Wobbly Legs** | Nausea | 15 seconds |
| **Slowness Curse** | Slowness IV | 10 seconds |
| **Random Teleport** | Teleport nearby | 5-10 blocks |
| **Fake Scare** | Spooky sound | Instant |
| **Gravity Shift** | Levitation | 2 seconds |
| **Inventory Jumble** | Scramble hotbar | Instant |
| **Cobweb Curse** | Trapped in cobweb | 5 seconds |

## Configuration Tips

### Adjust Drop Rates
```yaml
candy-drop:
  drop-chance: 15.0  # Increase for more drops
```

### Balance Trick/Treat Odds
```yaml
trick-or-treat:
  trick-chance: 50.0  # Lower = more treats
```

### Disable Specific Rewards
```yaml
treats:
  midas-touch:
    enabled: false  # Disable this treat
```

### Customize Economy Rewards
```yaml
treats:
  candy-cash:
    min-amount: 100.0
    max-amount: 500.0
```

### Change Valuable Items
```yaml
treats:
  midas-touch:
    possible-items:
      - DIAMOND
      - EMERALD
      - NETHERITE_INGOT
```

## Permissions

- `trickortreat.admin` - Admin commands (default: OP)
- `trickortreat.use` - Use candy (default: all players)

## Dependencies

- **Required**: Spigot/Paper 1.20.1+
- **Optional**: Vault (for economy features)

## Troubleshooting

**Candy not dropping?**
- Check `candy-drop.enabled: true` in config
- Verify mobs are being killed by players
- Check drop-chance percentage

**Economy not working?**
- Install Vault plugin
- Install an economy plugin (e.g., EssentialsX)
- Restart server

**Effects not applying?**
- Check if specific treat/trick is enabled in config
- Verify player has `trickortreat.use` permission

## Server Performance

This plugin is lightweight and optimized:
- Minimal event listeners
- No database requirements
- Configurable to reduce drops if needed
- All effects are temporary

---

**Happy Halloween! 🎃👻🍬**
