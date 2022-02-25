package io.github.alen_alex.helpcord.command;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.abstracts.AbstractListener;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class TestListener extends AbstractListener implements MessageCreateListener {

    public TestListener(HelpCord handler) {
        super(handler);
        register();
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        instance.getMessageRegistry().getIfPresent("example").get().asMessageBuilder().send(messageCreateEvent.getChannel()).join();
    }

    @Override
    protected void register() {
        getJavaCordAPI().addListener(this);
    }
}