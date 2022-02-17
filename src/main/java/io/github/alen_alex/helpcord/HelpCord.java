package io.github.alen_alex.helpcord;


import io.github.alen_alex.helpcord.handler.ConfigurationHandler;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.logs.LoggerBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class HelpCord {
    //Statics
    public final static String NAME = "HelpCord";

    //Final Variables
    private final Logger logger;
    private final File dataFolder;

    //Handlers
    private final ConfigurationHandler configurationHandler;

    public HelpCord() throws URISyntaxException {
        new Debug();
        this.logger = new LoggerBuilder().getLogger();
        final String currentDir = new File(BootStrap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        dataFolder = new File(currentDir,"HelpCord");
        if(!dataFolder.exists()){
            dataFolder.mkdirs();
            logger.info("Data folder was missing! created...");
        }
        this.configurationHandler = new ConfigurationHandler(this);
    }

    public void start(){
        if(!configurationHandler.initFiles()){
            System.exit(-1);
        }

        if(configurationHandler.getConfiguration().validateConfiguration()){
            System.exit(-1);
        }
    }

    @NotNull
    public Logger getLogger() {
        return logger;
    }

    @NotNull
    public File getDataFolder() {
        return dataFolder;
    }


    @Nullable
    public InputStream getResourceAsStream(@NotNull String name){
        return this.getClass().getResourceAsStream(name);
    }
}
