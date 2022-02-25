package io.github.alen_alex.helpcord.models.messages;

import de.leonhard.storage.internal.FlatFile;
import io.github.alen_alex.helpcord.models.messages.component.EmbedComponent;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Message {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TEXT = "text";
    public static final String FIELD_EMBED = "embed";

    private final String messageID;

    private List<String> text;
    private EmbedComponent embedComponent;
    private MessageBuilder builder;


    public Message(String messageID) {
        this.messageID = messageID;
        this.text = new ArrayList<>();
        build();
    }

    public Message(@NotNull FlatFile flatFile){
        this.messageID = flatFile.getString(FIELD_ID);
        this.text = flatFile.getStringList(FIELD_TEXT);
        this.embedComponent = new EmbedComponent(flatFile);
        build();
    }

    public void build(){
        final MessageBuilder messageBuilder = new MessageBuilder();
        text.stream().iterator().forEachRemaining(messageBuilder::append);
        if(embedComponent != null) {
            final EmbedBuilder embedBuilder = new EmbedBuilder();

            if(!embedComponent.isAuthorNull()){
                if(embedComponent.getAuthor().icon() != null  && embedComponent.getAuthor().icon().isValid()){
                    if(embedComponent.getAuthor().icon().local()){
                        embedBuilder.setAuthor(
                                embedComponent.getAuthor().author(),
                                embedComponent.getAuthor().url(),
                                embedComponent.getAuthor().icon().asFile()
                        );
                    }else {
                        embedBuilder.setAuthor(
                                embedComponent.getAuthor().author(),
                                embedComponent.getAuthor().url(),
                                embedComponent.getAuthor().icon().url()
                        );
                    }
                }else {
                    embedBuilder.setAuthor(embedComponent.getAuthor().author());
                }
            }

            embedBuilder.setColor(embedComponent.getColor());

            if(!embedComponent.isFooterNull()){
                if(embedComponent.getFooter().getIcon() != null && embedComponent.getFooter().getIcon().isValid()){
                    if(embedComponent.getFooter().getIcon().local()){
                        embedBuilder.setFooter(
                                embedComponent.getFooter().getText(),
                                embedComponent.getFooter().getIcon().asFile()
                        );
                    }else {
                        embedBuilder.setFooter(
                                embedComponent.getFooter().getText(),
                                embedComponent.getFooter().getIcon().url()
                        );
                    }
                }else {
                    embedBuilder.setFooter(embedComponent.getFooter().getText());
                }
            }

            embedBuilder.setDescription(embedComponent.getDescription());

            if(embedComponent.isShowTimestamp())
                embedBuilder.setTimestampToNow();

            if(!embedComponent.isImageNull()){
                if(embedComponent.getImage().isValid()) {
                    if (embedComponent.getImage().local())
                        embedBuilder.setImage(embedComponent.getImage().asFile());
                    else embedBuilder.setImage(embedComponent.getImage().url());
                }
            }

            if(!embedComponent.isThumbnailNull() && embedComponent.getThumbnail().isValid()){
                if(embedComponent.getThumbnail().local()){
                    embedBuilder.setThumbnail(embedComponent.getThumbnail().asFile());
                }else embedBuilder.setThumbnail(embedComponent.getThumbnail().url());
            }

            embedComponent.componentIterator().forEachRemaining(fields -> {
                embedBuilder.addField(fields.title(), fields.value(), fields.isInline());
            });
            messageBuilder.setEmbed(embedBuilder);
        }
        builder = messageBuilder;
    }

    public String getMessageID() {
        return messageID;
    }

    public List<String> getText() {
        return text;
    }

    public EmbedComponent getEmbedComponent() {
        return embedComponent;
    }

    public MessageBuilder asMessageBuilder(){
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        if (messageID != null ? !messageID.equals(message.messageID) : message.messageID != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return embedComponent.equals(message.embedComponent);
    }

    @Override
    public int hashCode() {
        int result = messageID != null ? messageID.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + embedComponent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("messageID", messageID)
                .append("text", text)
                .append("embedComponent", embedComponent)
                .toString();
    }
}
