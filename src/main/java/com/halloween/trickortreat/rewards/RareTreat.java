package com.halloween.trickortreat.rewards;

public enum RareTreat {
    TOKEN("Token", "You received a Halloween Token!", "token"),
    COLLECTPASS("Collect Pass", "You received a Collect Pass!", "collectpass"),
    FRUITKEY("Fruit Key", "You received a Fruit Key!", "fruitkey"),
    SPOOKEY("Spook Key", "You received a Spook Key!", "spookey"),
    NETHERITE("Netherite", "You received Netherite!", "netherite"),
    WSPAWN("Wither Spawn", "You received a Wither Spawn!", "wspawn"),
    SSPAWN("Skeleton Spawn", "You received a Skeleton Spawn!", "sspawn");
    
    private final String displayName;
    private final String description;
    private final String configKey;
    
    RareTreat(String displayName, String description, String configKey) {
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
