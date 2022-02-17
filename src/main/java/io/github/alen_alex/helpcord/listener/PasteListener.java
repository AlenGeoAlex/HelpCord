package io.github.alen_alex.helpcord.listener;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.enums.Tags;
import io.github.alen_alex.helpcord.abstracts.AbstractListener;
import io.github.alen_alex.helpcord.models.CodeBlocks;
import io.github.alen_alex.helpcord.utils.PasteUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PasteListener extends AbstractListener implements MessageCreateListener {

    public PasteListener(HelpCord instance) {
        super(instance);
        register();
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
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
        blocks.upload();
    }

    @Override
    protected void register() {
        getJavaCordAPI().addListener(this);
    }
}
