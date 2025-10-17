package com.halloween.trickortreat.rewards;

public enum RareTreat {
    TOKEN("Token", "You received a token!", "token"),
    COLLECTABLE_PASS("Collectable Pass", "You received a collectable pass!", "collectpass"),
    FRUITSTERS_KEY("Fruitsters Key", "You received a Fruitsters key!", "fruitkey"),
    SPOOKEY("SpooKey", "You received a SpooKey!", "spookey"),
    BLOCK_OF_NETHERITE("Block of Netherite", "You received a block of netherite!", "netherite"),
    WITCH_SPAWNER("Witch Spawner", "You received a witch spawner!", "wspawn"),
    SPIDER_SPAWNER("Spider Spawner", "You received a spider spawner!", "sspawn");
    
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
