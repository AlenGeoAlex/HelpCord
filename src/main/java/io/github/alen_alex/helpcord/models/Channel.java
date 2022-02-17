package io.github.alen_alex.helpcord.models;

import de.leonhard.storage.sections.FlatFileSection;
import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.exceptions.IllegalConfigurationData;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.server.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Channel {

    private final String guildID;
    private final String channel;
    private final boolean name;

    private Channel(String guildID, String channel) {
        this.guildID = guildID;
        this.channel = channel;
        name = !StringUtils.isNumeric(channel);
    }

    public String getGuildID() {
        return guildID;
    }

    public String getChannel() {
        return channel;
    }

    public Optional<org.javacord.api.entity.channel.Channel> getDiscordChannel(){
        if(name){
            return HelpCord.getInstance().getJavaCord().getJavaCordAPI().getChannelsByName(channel).stream().findFirst();
        }else {
            return HelpCord.getInstance().getJavaCord().getJavaCordAPI().getChannelById(channel);
        }
    }

    public Optional<Server> getServer(){
        return HelpCord.getInstance().getJavaCord().getServerOf(this.guildID);
    }

    public static Channel from(@NotNull FlatFileSection section){
        if(!section.contains("server-id") || !section.contains("channel"))
            throw new IllegalConfigurationData("The section ["+section.getPathPrefix()+"] seems to be not valid for a channel configuration!");

        final String server = section.getString("server-id");
        final String channel = section.getString("channel");
        return new Channel(server,channel);
    }
}
