package io.github.alen_alex.helpcord.registry;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.FlatFile;
import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.models.messages.Message;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MessageRegistry {

    private final HelpCord instance;

    private final HashMap<String, Message> messageHashMap;

    public MessageRegistry(HelpCord instance) {
        this.instance = instance;
        this.messageHashMap = new HashMap<>();
    }

    public void registerMessage(@NotNull Message message){
        this.messageHashMap.put(message.getMessageID(), message);
    }

    public void registerMessage(@NotNull FlatFile messageFile){
        final Message message = new Message(messageFile);
        this.registerMessage(message);
    }

    public void removeMessage(@NotNull String messageID){
        this.messageHashMap.remove(messageID);
    }

    public void removeMessage(@NotNull Message message){
        this.messageHashMap.remove(message.getMessageID());
    }

    public Optional<Message> getIfPresent(@NotNull String messageID){
        return Optional.ofNullable(messageHashMap.get(messageID));
    }

    public void registerMessages(List<File> filesList){
        filesList.iterator().forEachRemaining(file -> {
            this.registerMessage(new Yaml(file.getName(), file.getParent()));
        });
    }

    public boolean isRegistered(@NotNull String messageID){
        return this.messageHashMap.containsKey(messageID);
    }

    public int size(){
        return messageHashMap.size();
    }
}
