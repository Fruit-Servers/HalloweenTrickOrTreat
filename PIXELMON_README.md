# Halloween Trick or Treat Plugin - Pixelmon Edition

## Version 1.1.1-pixelmon for Minecraft 1.16.5 with Pixelmon Reforged

This special version of the Halloween Trick or Treat plugin includes full support for Pixelmon Reforged servers!

## Features

### All Standard Features Plus:
- **Pokemon Battle Drops** - Defeat wild Pokemon for candy drops
- **Capture Rewards** - Get candy when capturing Pokemon
- **Legendary Bonuses** - Special drop rates for legendary Pokemon
- **Ultra Beast Support** - Configurable drops from Ultra Beasts
- **Shiny Pokemon Bonus** - Increased drop rates from shiny Pokemon

## Pixelmon-Specific Configuration

```yaml
pixelmon-drops:
  enabled: true
  drop-chance: 15.0        # Base chance for candy from Pokemon
  rare-chance: 2.0         # Chance for rare candy
  from-capture: true       # Drop candy when capturing Pokemon
  from-legendary: true     # Allow drops from legendary Pokemon
  from-ultra-beast: true   # Allow drops from Ultra Beasts
  shiny-bonus: true        # Enable bonus drops from shiny Pokemon
  shiny-drop-chance: 30.0  # Drop chance from shiny Pokemon
```

## Building the Pixelmon Version

Due to Pixelmon dependencies, the Pixelmon version requires a special build process:

1. **Rename the Pixelmon listener file:**
   ```bash
   mv src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java.pixelmon \
      src/main/java/com/halloween/trickortreat/listeners/PixelmonDeathListener.java
   ```

2. **Build with the Pixelmon Gradle configuration:**
   ```bash
   ./gradlew clean build -b build-pixelmon.gradle
   ```

3. **The JAR will be created as:**
   ```
   build/libs/TrickOrTreat-1.1.1-pixelmon.jar
   ```

## Installation

1. Ensure you have Pixelmon Reforged 9.1.11 (or compatible version) installed
2. Place the `TrickOrTreat-1.1.1-pixelmon.jar` in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/TrickOrTreat/config.yml`

## Requirements

- Minecraft 1.16.5
- Spigot/Paper 1.16.5
- Pixelmon Reforged 9.1.11+
- Java 8 or higher

## Compatibility

This version is specifically designed for Pixelmon servers. The plugin will:
- Automatically detect if Pixelmon is present
- Enable Pokemon-specific features only when Pixelmon is loaded
- Fall back to vanilla mob drops if Pixelmon is not available

## Drop Mechanics

### Wild Pokemon Battles
- Candy drops when defeating wild Pokemon
- Drop chance configurable per Pokemon type
- Rare candy has separate drop chance with 1-hour cooldown

### Pokemon Capture
- Optional candy rewards for successful captures
- Higher chance for rare Pokemon captures
- Shiny Pokemon have bonus drop rates

### Special Pokemon Types
- **Legendary Pokemon**: Configurable separate drop rates
- **Ultra Beasts**: Can be enabled/disabled separately
- **Shiny Pokemon**: Bonus multiplier for all drops

## Commands

All standard commands plus:
- `/trickortreat reload` - Reloads all configuration including Pixelmon settings
- `/trickortreat cooldown check <player>` - Check rare candy cooldown status

## Permissions

Same as standard version:
- `trickortreat.use` - Use candy items (default: true)
- `trickortreat.admin` - Admin commands (default: op)

## Support

For issues specific to the Pixelmon version, please mention:
1. Your Pixelmon version
2. Your Minecraft/Forge version
3. Any error messages from console
4. Your config.yml settings

## Notes

- The Pixelmon listener uses Forge event system
- Candy drops are server-side and work with all Pixelmon battles
- Compatible with other Pixelmon addons and plugins
- Respects vanilla mob spawn settings alongside Pokemon
