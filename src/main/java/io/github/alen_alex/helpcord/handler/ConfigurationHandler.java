package io.github.alen_alex.helpcord.handler;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.configuration.files.Configuration;
import io.github.alen_alex.helpcord.logs.Debug;

public class ConfigurationHandler {

    private HelpCord instance;
    private Configuration configuration;

    public ConfigurationHandler(HelpCord instance) {
        this.instance = instance;
        Debug.debug("Configuration handler has been instantiated");
    }

    public boolean initFiles(){
        try {
            Debug.debug("Starting to create/fetch all the required configuration files");
            configuration = new Configuration(this);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public HelpCord getInstance() {
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
