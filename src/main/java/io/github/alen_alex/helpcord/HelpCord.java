package io.github.alen_alex.helpcord;


import io.github.alen_alex.helpcord.exceptions.IllegalInstanceAccess;
import io.github.alen_alex.helpcord.handler.ConfigurationHandler;
import io.github.alen_alex.helpcord.javacord.JavaCord;
import io.github.alen_alex.helpcord.listener.PasteListener;
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
    private static HelpCord INSTANCE = null;
    //Final Variables
    private final Logger logger;
    private final File dataFolder;

    //Handlers
    private final ConfigurationHandler configurationHandler;

    //JavaCord Instance
    private final JavaCord javaCord;

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
        this.javaCord = new JavaCord(this);
        Debug.debug("Successfully instantiated HelpCord Instance!");
    }

    public void start(){
        INSTANCE = this;
        Debug.debug("Preparing to start connection");
        if(!configurationHandler.initFiles()){
            Debug.err("Failed on initFiles!");
            System.exit(-1);
        }

        if(!configurationHandler.getConfiguration().validateConfiguration()){
            Debug.err("Failed on ConfigValidation!");
            System.exit(-1);
        }

        configurationHandler.getConfiguration().loadConfiguration();

        if(!javaCord.connect()){
            Debug.err("Failed to connect to Discord");
            System.exit(-1);
        }

        new PasteListener(this);
        getLogger().info("Connected to Discord!");
    }

    @NotNull
    public Logger getLogger() {
        return logger;
    }

    @NotNull
    public File getDataFolder() {
        return dataFolder;
    }

    @NotNull
    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }

    @Nullable
    public InputStream getResourceAsStream(@NotNull String name){
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }

    public JavaCord getJavaCord() {
        return javaCord;
    }

    public static HelpCord getInstance(){
        if(INSTANCE == null)
            throw new IllegalInstanceAccess("The instance is not yet instantiated!");

        return INSTANCE;
    }
}
