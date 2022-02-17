package io.github.alen_alex.helpcord.abstracts;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.configuration.files.Configuration;
import io.github.alen_alex.helpcord.javacord.JavaCord;
import org.javacord.api.DiscordApi;

import java.util.HashMap;

public abstract class AbstractListener {

    protected HelpCord instance;

    public AbstractListener(HelpCord instance) {
        this.instance = instance;
    }

    protected abstract void register();

    protected Configuration getMainConfiguration(){
        return this.instance.getConfigurationHandler().getConfiguration();
    }

    protected JavaCord getJavaCord(){
        return this.instance.getJavaCord();
    }

    protected DiscordApi getJavaCordAPI(){
        return this.getJavaCord().getJavaCordAPI();
    }


}
