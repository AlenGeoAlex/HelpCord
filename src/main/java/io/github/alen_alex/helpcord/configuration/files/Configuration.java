package io.github.alen_alex.helpcord.configuration.files;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.FlatFile;
import io.github.alen_alex.helpcord.abstracts.AbstractFile;
import io.github.alen_alex.helpcord.configuration.ConfigPath;
import io.github.alen_alex.helpcord.configuration.IConfig;
import io.github.alen_alex.helpcord.handler.ConfigurationHandler;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.utils.ConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class Configuration extends AbstractFile implements IConfig {

    private final ConfigurationHandler handler;

    private String botToken,botOwner;

    public Configuration(@NotNull final ConfigurationHandler handler) {
        super(new Config("config.yml",
                handler.getInstance().getDataFolder().getPath(),
                handler.getInstance().getResourceAsStream("config.yml")
                ));
        this.handler = handler;
    }

    @Override
    public FlatFile getConfigurationFile() {
        return baseFile;
    }

    @Override
    public long reload() {
        return 0;
    }

    @Override
    public void loadConfiguration() {
        Debug.debug("Loading configurations");
        this.botToken = getString(ConfigPath.BOT_TOKEN.getPath());
        this.botOwner = getString(ConfigPath.BOT_OWNER.getPath());
    }

    @Override
    public boolean validateConfiguration() {
        Debug.debug("Starting to validate configuration options!");
        AtomicBoolean valid = new AtomicBoolean(true);
        ConfigUtils.getStrictEnums().forEachRemaining(enumPath -> {
            if(!this.baseFile.contains(enumPath.getPath())){
                valid.set(false);
                Debug.err("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field. Its missing!");
            }else {
                if (StringUtils.isEmpty(getString(enumPath.getPath()))) {
                    valid.set(false);
                    Debug.err("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field. It can't be blank");
                }
            }
        });
        return valid.get();
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotOwner() {
        return botOwner;
    }
}
