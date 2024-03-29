package io.github.alen_alex.helpcord.configuration.files;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.alen_alex.helpcord.abstracts.AbstractFile;
import io.github.alen_alex.helpcord.enums.ConfigPath;
import io.github.alen_alex.helpcord.configuration.IConfig;
import io.github.alen_alex.helpcord.handler.ConfigurationHandler;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.utils.ConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Configuration extends AbstractFile implements IConfig {

    private final ConfigurationHandler handler;

    private String botToken,botOwner;

    private boolean pasteGlobal,pasteEnabled;
    private FlatFileSection cooldownSettings;
    private List<String> pasteChannels;

    private List<File> messagesFiles;

    public Configuration(@NotNull final ConfigurationHandler handler) {
        super(new Config("config.yml",
                handler.getInstance().getDataFolder().getPath(),
                handler.getInstance().getResourceAsStream("config.yml")
                ));
        this.handler = handler;

        this.pasteChannels = new ArrayList<>();
        this.messagesFiles = new ArrayList<>();
    }

    @Override
    public FlatFile getConfigurationFile() {
        return baseFile;
    }

    @Override
    public long reload() {
        pasteChannels.clear();
        messagesFiles.clear();
        return 0;
    }

    @Override
    public void loadConfiguration() {
        Debug.debug("Loading configurations");
        this.botToken = getString(ConfigPath.BOT_TOKEN.getPath());
        this.botOwner = getString(ConfigPath.BOT_OWNER.getPath());

        this.pasteGlobal = getBoolean(ConfigPath.PASTE_GLOBAL.getPath());
        this.pasteEnabled = getBoolean(ConfigPath.PASTE_ENABLED.getPath());
        this.pasteChannels = baseFile.getStringList(ConfigPath.PASTE_CHANNELS.getPath());
        this.cooldownSettings = getSectionOn(ConfigPath.PASTE_COOL_DOWN.getPath());
        Debug.debug("Loaded "+pasteChannels.size()+" channels for checking paste configuration!");

        this.baseFile.getStringList(ConfigPath.MESSAGES.getPath()).iterator().forEachRemaining((pathString) -> {
            messagesFiles.add(new File(handler.getInstance().getDataFolder().getAbsoluteFile()+pathString));
        });
    }

    //https://bytebin.lucko.me/BiBsbwOGzm
    @Override
    public boolean validateConfiguration() {
        Debug.debug("Starting to validate configuration options!");
        AtomicBoolean valid = new AtomicBoolean(true);
        ConfigUtils.getStrictEnums().forEachRemaining(enumPath -> {
            if(!this.baseFile.contains(enumPath.getPath())){
                if(enumPath.getDef() == null) {
                    valid.set(false);
                    Debug.err("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field. Its missing!");
                }else {
                    this.baseFile.set(enumPath.getPath(),enumPath.getDef());
                    Debug.warn("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field and seems missing, replaced with a default value");

                }
            }else {
                if (StringUtils.isEmpty(getString(enumPath.getPath()))) {
                    if(enumPath.getDef() == null) {
                        valid.set(false);
                        Debug.err("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field. It can't be blank");
                    }else {
                        this.baseFile.set(enumPath.getPath(),enumPath.getDef());
                        Debug.warn("Configuration Option " + enumPath.name() + "[" + enumPath.getPath() + "] is a strict field and seems blank, replaced with a default value");

                    }
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

    public List<String> getPasteChannels() {
        return pasteChannels;
    }

    public boolean isPasteGlobal() {
        return pasteGlobal;
    }

    public boolean isPasteEnabled() {
        return pasteEnabled;
    }


    public FlatFileSection getCooldownSettings() {
        return cooldownSettings;
    }

    public List<File> getMessagesFiles() {
        return messagesFiles;
    }
}
