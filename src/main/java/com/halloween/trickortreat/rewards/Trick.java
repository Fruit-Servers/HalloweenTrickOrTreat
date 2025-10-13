package com.halloween.trickortreat.rewards;

public enum Trick {
    TEMPORARY_BLINDNESS("Temporary Blindness", "You've been blinded!"),
    UNLUCKY_STRIKE("Unlucky Strike", "Lightning struck you!"),
    WOBBLY_LEGS("Wobbly Legs", "You feel dizzy!"),
    SLOWNESS_CURSE("Slowness Curse", "You've been slowed down!"),
    RANDOM_TELEPORT("Random Teleport", "You've been teleported!"),
    FAKE_SCARE("Fake Scare", "BOO!"),
    GRAVITY_SHIFT("Gravity Shift", "Gravity is shifting!"),
    INVENTORY_JUMBLE("Inventory Jumble", "Your hotbar has been scrambled!"),
    COBWEB_CURSE("Cobweb Curse", "You're stuck in a cobweb!");
    
    private final String displayName;
    private final String description;
    
    Trick(String displayName, String description) {
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
