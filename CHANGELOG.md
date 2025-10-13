# Changelog

## Version 1.0.0 - Initial Release

### Features
- **Halloween Candy Drop System**
  - Configurable drop chance from mobs (default 15%)
  - Works with hostile, passive, and boss mobs
  - Beautiful candy item with enchanted glow effect
  - Custom name and lore with Halloween theme

- **Trick or Treat Mechanic**
  - Right-click candy to activate
  - 50/50 chance for Trick or Treat
  - Item consumed on use
  - Instant feedback with messages and effects

- **9 Treat Rewards**
  - Speedy Treat (Speed II)
  - High Jumps (Jump Boost II)
  - Candy Cash (Economy integration)
  - XP Rush (Experience points)
  - Nourishing Nibble (Hunger restoration)
  - Spectral Sight (Glowing effect on nearby mobs)
  - Midas Touch (Valuable item drops)
  - Instant Repair (Item durability restoration)
  - Health Surge (Absorption hearts)

- **9 Trick Effects**
  - Temporary Blindness
  - Unlucky Strike (Lightning effect)
  - Wobbly Legs (Nausea)
  - Slowness Curse
  - Random Teleport
  - Fake Scare (Spooky sounds)
  - Gravity Shift (Levitation)
  - Inventory Jumble (Hotbar scramble)
  - Cobweb Curse (Temporary trap)

- **Configuration System**
  - Fully configurable drop rates
  - Enable/disable individual treats and tricks
  - Customize durations, amounts, and effects
  - Configurable messages with color codes
  - Economy reward ranges

- **Commands & Permissions**
  - `/trickortreat reload` - Reload config
  - `/trickortreat give` - Give candy to players
  - `/trickortreat help` - Show help
  - Tab completion support
  - Permission-based access control

- **Optional Integrations**
  - Vault economy support
  - Works standalone without Vault

### Technical Details
- Built for Spigot/Paper 1.20.1+
- Java 17 compatible
- Maven build system
- Persistent data containers for item tracking
- Event-driven architecture
- Lightweight and optimized

### Known Limitations
- Economy features require Vault plugin
- Some sounds may vary between Minecraft versions
- Cobweb placement respects world protection plugins
