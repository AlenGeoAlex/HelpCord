package io.github.alen_alex.helpcord.configuration;

import java.util.Arrays;
import java.util.Iterator;

public enum ConfigPath {
        BOT_TOKEN("bot.auth.token", true),
        BOT_OWNER("bot.auth.owner-id",true)
    ;

    private String path;
    private boolean isStrict;

    ConfigPath(String path) {
        this.path = path;
    }

    ConfigPath(String path, boolean isStrict) {
        this.path = path;
        this.isStrict = isStrict;
    }

    public String getPath() {
        return path;
    }

    public boolean isStrict() {
        return isStrict;
    }

}
