package io.github.alen_alex.helpcord.configuration;

import de.leonhard.storage.internal.FlatFile;

public interface IConfig {

    FlatFile getConfigurationFile();

    long reload();

    void loadConfiguration();

    default boolean validateConfiguration(){
        return true;
    }
}
