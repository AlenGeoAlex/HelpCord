package io.github.alen_alex.helpcord.enums;

public enum CooldownKeys {
    PASTE("PASTE")
    ;

    private String key;

    CooldownKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
