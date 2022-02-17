package io.github.alen_alex.helpcord.handler;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.configuration.files.Configuration;

public class ConfigurationHandler {

    private HelpCord instance;
    private Configuration configuration;

    public ConfigurationHandler(HelpCord instance) {
        this.instance = instance;
    }

    public boolean initFiles(){
        try {
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
