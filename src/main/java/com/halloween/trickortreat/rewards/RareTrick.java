package com.halloween.trickortreat.rewards;

public enum RareTrick {
    FAKE_CREEPER_BLAST("Fake Creeper Blast", "A creeper exploded nearby!", "fake-creeper-blast"),
    FAKE_WITHER("Fake Wither", "A wither appeared!", "fake-wither"),
    MULTI_POTION_EFFECT("Multi-Potion Effect", "You've been cursed with multiple effects!", "multi-potion-effect"),
    UPGRADED_COBWEB("Upgraded Cobweb", "You're trapped in a massive cobweb!", "upgraded-cobweb");
    
    private final String displayName;
    private final String description;
    private final String configKey;
    
    RareTrick(String displayName, String description, String configKey) {
        this.displayName = displayName;
        this.description = description;
        this.configKey = configKey;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getConfigKey() {
        return configKey;
    }
}
