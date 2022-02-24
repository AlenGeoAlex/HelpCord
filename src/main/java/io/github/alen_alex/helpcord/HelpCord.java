package io.github.alen_alex.helpcord;


import io.github.alen_alex.helpcord.exceptions.IllegalInstanceAccess;
import io.github.alen_alex.helpcord.handler.ConfigurationHandler;
import io.github.alen_alex.helpcord.registry.CooldownRegistry;
import io.github.alen_alex.helpcord.javacord.JavaCord;
import io.github.alen_alex.helpcord.listener.PasteListener;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.logs.LoggerBuilder;
import org.apache.commons.lang3.StringUtils;
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

    //Registry
    private final CooldownRegistry cooldownRegistry;

    //JavaCord Instance
    private final JavaCord javaCord;

    //Interactive Console
    private final InteractiveConsole console;

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
        this.cooldownRegistry = new CooldownRegistry(this);
        this.javaCord = new JavaCord(this);
        this.console = new InteractiveConsole(this);
        Debug.debug("Successfully instantiated HelpCord Instance!");
    }

    public void start(){
        INSTANCE = this;
        console.start();
        Debug.debug("Preparing to start connection");
        if(!configurationHandler.initFiles()){
            end("Failed on initFiles!",true);
        }

        if(!configurationHandler.getConfiguration().validateConfiguration()){
            end("Failed on ConfigValidation!",true);
        }

        configurationHandler.getConfiguration().loadConfiguration();

        if(!javaCord.connect()){
            end("Failed to connect to Discord",true);
        }

        new PasteListener(this);


        getLogger().info("Connected to Discord!");
    }

    public void reload(){

    }

    public void end(@Nullable String message, boolean errClose){
        if(StringUtils.isNotBlank(message)) {
            if(errClose)
                Debug.err(message);
            else Debug.debug(message);
        }
        if(javaCord != null && javaCord.getJavaCordAPI() != null) {
            this.logger.info("Disconnecting JavaCord Connection!");
            this.javaCord.getJavaCordAPI().disconnect();
        }
        if(this.console != null && this.console.isAlive()) {
            this.logger.info("Stopping active threads!");
            console.interrupt();
        }
        this.logger.info("Goodbye!");
        System.exit(-1);
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

    public CooldownRegistry getCooldownRegistry() {
        return cooldownRegistry;
    }

    public void registerCoolDowns(){

    }
}
