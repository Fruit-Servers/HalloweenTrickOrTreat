package com.halloween.trickortreat.rewards;

public enum Treat {
    SPEEDY_TREAT("Speedy Treat", "You got a burst of speed!"),
    HIGH_JUMPS("High Jumps", "You can jump super high!"),
    CANDY_CASH("Candy Cash", "You received some money!"),
    XP_RUSH("XP Rush", "You gained experience!"),
    NOURISHING_NIBBLE("Nourishing Nibble", "Your hunger has been restored!"),
    SPECTRAL_SIGHT("Spectral Sight", "You can see through walls!"),
    MIDAS_TOUCH("Midas Touch", "Valuable items appeared at your feet!"),
    INSTANT_REPAIR("Instant Repair", "Your item has been repaired!"),
    HEALTH_SURGE("Health Surge", "You gained extra hearts!");
    
    private final String displayName;
    private final String description;
    
    Treat(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getConfigKey() {
        return name().toLowerCase().replace("_", "-");
    }
}
