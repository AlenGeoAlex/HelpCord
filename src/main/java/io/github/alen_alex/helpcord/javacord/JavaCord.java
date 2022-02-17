package io.github.alen_alex.helpcord.javacord;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.logs.Debug;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class JavaCord {

    private DiscordApi javaCord = null;
    private final HelpCord instance;

    public JavaCord(HelpCord instance) {
        this.instance = instance;
        Debug.debug("Javacord has been instantiated");
    }

    public boolean connect(){
        final String token = instance.getConfigurationHandler().getConfiguration().getBotToken();
        Debug.debug("The provided token is : "+token);
        try {
            instance.getLogger().info("Starting to init Connection");
            javaCord = new DiscordApiBuilder()
                    .setToken(token)
                    .setAllIntents()
                    .setWaitForServersOnStartup(true)
                    .setWaitForUsersOnStartup(true)
                    .login().join();
        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
        return javaCord != null ;
    }
}
