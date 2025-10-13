# 🎃 Halloween Trick or Treat Plugin

A polished, Halloween-themed Minecraft plugin that adds a festive trick-or-treat mechanic to your server!

## Features

### 🍬 Halloween Candy Drop System
- Configurable chance (default 15%) for mobs to drop Halloween Candy when killed by players
- Works with hostile mobs, passive mobs, and bosses
- Beautiful custom candy item with enchanted glow effect

### 🎲 Trick or Treat Mechanic
- Right-click Halloween Candy to activate
- 50/50 chance of receiving a Treat (reward) or Trick (effect)
- All outcomes are fully configurable

### ✨ Treats (Positive Rewards)
1. **Speedy Treat** - Speed II for 30 seconds
2. **High Jumps** - Jump Boost II for 30 seconds
3. **Candy Cash** - Receive $50-$200 (requires Vault)
4. **XP Rush** - Gain 50-100 experience points
5. **Nourishing Nibble** - Restore 4 hunger + saturation
6. **Spectral Sight** - See mobs through walls (Glowing effect)
7. **Midas Touch** - Spawn 1-3 valuable items (diamonds, emeralds, etc.)
8. **Instant Repair** - Repair 5-10% of held item's durability
9. **Health Surge** - Absorption II for 45 seconds (extra golden hearts)

### 👻 Tricks (Negative Effects)
1. **Temporary Blindness** - Blindness for 10 seconds
2. **Unlucky Strike** - Harmless lightning effect
3. **Wobbly Legs** - Nausea for 15 seconds
4. **Slowness Curse** - Slowness IV for 10 seconds
5. **Random Teleport** - Teleport 5-10 blocks away
6. **Fake Scare** - Loud spooky sound effect
7. **Gravity Shift** - Brief levitation then soft landing
8. **Inventory Jumble** - Scramble hotbar items
9. **Cobweb Curse** - Trapped in cobweb for 5 seconds

## Installation

1. Download the latest release JAR file
2. Place it in your server's `plugins` folder
3. (Optional) Install Vault for economy features
4. Restart your server
5. Configure the plugin in `plugins/HalloweenTrickOrTreat/config.yml`

## Building from Source

### Requirements
- Java 17 or higher
- Gradle 8.5+ (or use included wrapper)

### Build Steps
```bash
./gradlew clean build
```

Or use the build script:
```bash
./build.sh
```

The compiled JAR will be in the `build/libs` folder.

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/trickortreat reload` | Reload configuration | `trickortreat.admin` |
| `/trickortreat give <player> [amount]` | Give candy to a player | `trickortreat.admin` |
| `/trickortreat help` | Show help message | `trickortreat.admin` |

**Aliases:** `/tot`, `/halloween`

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `trickortreat.admin` | Access to admin commands | OP |
| `trickortreat.use` | Use Halloween candy | All players |

## Configuration

The plugin is highly configurable! Edit `config.yml` to customize:

- Drop chances and mob types
- Trick/treat probabilities
- Effect durations and strengths
- Economy rewards
- Enable/disable specific treats and tricks
- Custom messages

### Example Configuration Snippets

```yaml
candy-drop:
  enabled: true
  drop-chance: 15.0
  drop-from-hostile: true
  drop-from-passive: true

trick-or-treat:
  trick-chance: 50.0

treats:
  speedy-treat:
    enabled: true
    speed-level: 2
    duration: 30
```

## Dependencies

- **Required:** Spigot/Paper 1.20.1+
- **Optional:** Vault (for economy features)

## Support

For issues, suggestions, or contributions, please visit the GitHub repository.

## License

This plugin is provided as-is for use on Minecraft servers.

---

**Happy Halloween! 🎃👻🍬**
