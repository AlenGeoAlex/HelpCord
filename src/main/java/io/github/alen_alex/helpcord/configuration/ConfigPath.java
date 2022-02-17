package io.github.alen_alex.helpcord.configuration;

public enum ConfigPath {
        BOT_TOKEN("bot.auth.token", true, null),
        BOT_OWNER("bot.auth.owner-id",true,"403569609518743552"),

        PASTE_ENABLED("paste-settings.enabled",true,true),
        PASTE_COOL_DOWN("paste-settings.cooldown",true,null),
        PASTE_COOL_DOWN_ENABLED("paste-settings.cooldown.enabled",true,true),
        PASTE_GLOBAL("paste-settings.global",true, true),
        PASTE_CHANNELS("paste-settings.channels",false, null)
    ;

    private String path;
    private boolean isStrict;
    private Object def;

    ConfigPath(String path) {
        this.path = path;
    }

    ConfigPath(String path, boolean isStrict) {
        this.path = path;
        this.isStrict = isStrict;
    }

    ConfigPath(String path, boolean isStrict, Object def) {
        this.path = path;
        this.isStrict = isStrict;
        this.def = def;
    }

    public String getPath() {
        return path;
    }

    public boolean isStrict() {
        return isStrict;
    }

    public Object getDef() {
        return def;
    }
}
