package com.halloween.trickortreat.rewards;

public enum RareTreat {
    EVENT_MEDAL("Event Medal", "You received a Skyblock Event Medal!", "event-medal"),
    TOKEN("Token", "You received a Skyblock Token!", "token"),
    TRADING_CARD("Trading Card", "You've received a trading card!", "trading-card"),
    ANCIENT_DEBRIS("Ancient Debris", "You received 2 Ancient Debris!", "ancient-debris"),
    MONEY("Money", "You received $5000!", "money"),
    VOTE_SHARD("Vote Shard", "You received 1 Vote Shard!", "vote-shard"),
    END_VOUCHER("End Voucher", "You received a 5 minute End Voucher!", "end-voucher"),
    NETHER_VOUCHER("Nether Voucher", "You received a 5 minute Nether Voucher!", "nether-voucher"),
    DEEP_DARK_VOUCHER("Deep Dark Voucher", "You received a 5 minute Deep Dark Voucher!", "deep-dark-voucher"),
    GEODE_FORTUNE("Geode of Fortune", "You received a Geode of Fortune Enchanted Book!", "geode-fortune"),
    GOD_VOUCHER("God Voucher", "You received a 30 minute God Voucher!", "god-voucher");
    
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
