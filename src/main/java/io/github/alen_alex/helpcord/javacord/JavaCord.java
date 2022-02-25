package io.github.alen_alex.helpcord.javacord;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.logs.Debug;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
            FallbackLoggerConfiguration.setDebug(true);
            FallbackLoggerConfiguration.setTrace(true);
        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
        return javaCord != null ;
    }

    public DiscordApi getJavaCordAPI() {
        return javaCord;
    }

    public Iterator<Server> getAllServers(){
        return javaCord.getServers().iterator();
    }

    public Iterator<Server> getAllServerIf(final Predicate<Server> condition){
        return javaCord.getServers().stream().filter(condition).iterator();
    }

    public Optional<Server> getServerOf(@NotNull String id){
        return javaCord.getServerById(id);
    }

}
