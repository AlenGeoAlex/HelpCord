package io.github.alen_alex.helpcord.modules;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.enums.Tags;
import io.github.alen_alex.helpcord.abstracts.AbstractListener;
import io.github.alen_alex.helpcord.interfaces.Module;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.models.CodeBlocks;
import io.github.alen_alex.helpcord.utils.PasteUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PasteListener extends AbstractListener implements MessageCreateListener, Module {

    private boolean enabled = false;

    public PasteListener(HelpCord handler) {
        super(handler);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        final String channelID = messageCreateEvent.getChannel().getIdAsString();

        /*
        Checks if the paste settings is global. If the paste setting is global then the list of channels act as
        Blacklist, so here if the paste is global and if the channel id is present in the blacklisted block then it will
        return out!
         */
        if(getMainConfiguration().isPasteGlobal() && getMainConfiguration().getPasteChannels().contains(channelID))
            return;

        /*
        Checks if the paste is not global, if the paste is not global then the paste channels act as whitelist...
        so here...if the paste is not global and if the channel is not whitelisted then it will return out
         */
        if(!getMainConfiguration().isPasteGlobal() && !getMainConfiguration().getPasteChannels().contains(channelID))
            return;

        final String message = messageCreateEvent.getMessageContent();

        if(!PasteUtils.isCodeBlocked(message)){
            return;
        }

        CodeBlocks blocks = PasteUtils.parseLanguage(message);
        blocks.upload().thenAccept(oString -> {
           if(oString.isEmpty()){
               Debug.debug("Failed to create new paste of !");
               //TODO Failed Embed!
           }else {
               final String url = Tags.PASTE_DOMAIN.getTag()+"/"+oString.get();
               instance.getLogger().info("Created new paste ["
                       +blocks.getLanguage()
                       +"] for "
                       +messageCreateEvent.getMessageAuthor().getDisplayName()
                       +"["+messageCreateEvent.getMessageAuthor().getIdAsString()+"]"
                       +" with a successful API response of "
                       +url
               );
               messageCreateEvent.getMessage().delete("The code block has been uploaded to "+url);
               messageCreateEvent.getChannel().sendMessage(url);
               Debug.debug("Finishing up paste on "+oString.get());
               //TODO Success Message
           }
        });
    }

    @Override
    protected void register() {
        getJavaCordAPI().addListener(this);
    }

    @Override
    public String name() {
        return "Paste";
    }

    @Override
    public String description() {
        return "Automatically uploads codes pasted with in code blocks to the provided paste server";
    }

    @Override
    public void registerModule() {
        if(enabled)
            return;

        if(shouldEnable()){
            registerModule();
            enabled = true;
            instance.getLogger().info("Registered Module : "+name());
        }
    }

    @Override
    public void unregisterModule() {
        if(!enabled)
            return;

        instance.getJavaCord().getJavaCordAPI().removeListener(this);
        enabled = false;
        instance.getLogger().info("Unregistered Module : "+name());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean shouldEnable() {
        return instance.getConfigurationHandler().getConfiguration().isPasteEnabled();
    }
}
