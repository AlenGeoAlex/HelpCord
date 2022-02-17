package io.github.alen_alex.helpcord.listener;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.enums.CooldownKeys;
import io.github.alen_alex.helpcord.enums.Tags;
import io.github.alen_alex.helpcord.abstracts.AbstractListener;
import io.github.alen_alex.helpcord.logs.Debug;
import io.github.alen_alex.helpcord.models.CodeBlocks;
import io.github.alen_alex.helpcord.utils.PasteUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PasteListener extends AbstractListener implements MessageCreateListener {

    public PasteListener(HelpCord handler) {
        super(handler);
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

        if(instance.getCooldownRegistry().inCooldown(messageCreateEvent.getMessageAuthor().getIdAsString(),CooldownKeys.PASTE)){
            //TODO SEND COOLDOWN MESSAGE
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
                instance.getCooldownRegistry().updateCooldown(messageCreateEvent.getMessageAuthor().getIdAsString(),CooldownKeys.PASTE);
           }
        });
    }

    @Override
    protected void register() {
        getJavaCordAPI().addListener(this);
    }
}
