package io.github.alen_alex.helpcord.models.messages.component;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.alen_alex.helpcord.models.messages.Message;
import io.github.alen_alex.helpcord.utils.ConfigUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmbedComponent {

    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_COLOR = "color";
    public static final String FIELD_FOOTER = "footer";
    public static final String FIELD_TIMESTAMP = "show-timestamp";
    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_EMBED_FIELDS = "fields";
    public static final String FIELD_THUMBNAIL = "thumbnail";

    public static final Color DEFAULT_COLOR = Color.WHITE;

    private AuthorComponent author;
    private Color color;
    private FooterComponent footer;
    private boolean showTimestamp;
    private ImageComponent image;
    private String description;
    private List<FieldComponent> fields;
    private ImageComponent thumbnail;

    public EmbedComponent(AuthorComponent author, Color color, FooterComponent footer, boolean showTimestamp, ImageComponent image, String description, List<FieldComponent> fields, ImageComponent thumbnail) {
        this.author = author;
        this.color = color;
        this.footer = footer;
        this.showTimestamp = showTimestamp;
        this.image = image;
        this.description = description;
        this.fields = fields;
        this.thumbnail = thumbnail;
    }

    public EmbedComponent(AuthorComponent author, Color color, FooterComponent footer, boolean showTimestamp, ImageComponent image, String description, ImageComponent thumbnail) {
        this.author = author;
        this.color = color;
        this.footer = footer;
        this.showTimestamp = showTimestamp;
        this.image = image;
        this.description = description;
        this.thumbnail = thumbnail;
        this.fields = new ArrayList<>();
    }

    public EmbedComponent(@NotNull FlatFile file) {
        if(file.contains(asPath(FIELD_AUTHOR)))
            this.author = new AuthorComponent(ConfigUtils.getSectionOf(file, asPath(FIELD_AUTHOR)));
        else author = null;

        if(file.contains(asPath(FIELD_COLOR))){
            final String configColor = file.getString(asPath(FIELD_COLOR));
            color = Color.getColor(configColor, DEFAULT_COLOR);
        }else color = DEFAULT_COLOR;

        if(file.contains(asPath(FIELD_FOOTER))) {
            footer = new FooterComponent(ConfigUtils.getSectionOf(file, asPath(FIELD_FOOTER)));
        }else footer = null;

        if(file.contains(asPath(FIELD_TIMESTAMP))) {
            this.showTimestamp = file.getBoolean(asPath(FIELD_TIMESTAMP));
        }else showTimestamp = true;

        if(file.contains(asPath(FIELD_IMAGE))){
            this.image = new ImageComponent(ConfigUtils.getSectionOf(file,asPath(FIELD_IMAGE)));
        }else image = null;

        if(file.contains(asPath(FIELD_DESCRIPTION))){
            this.description = file.getString(asPath(FIELD_DESCRIPTION));
        }

        if(file.contains(asPath(FIELD_THUMBNAIL))){
            this.thumbnail = new ImageComponent(file.getMapParameterized(asPath(FIELD_THUMBNAIL)));
        }else thumbnail = null;

        this.fields = new ArrayList<>();
        file.singleLayerKeySet(asPath(FIELD_EMBED_FIELDS)).iterator().forEachRemaining(eachKey -> {
            this.fields.add(new FieldComponent(ConfigUtils.getSectionOf(file,asPath(FIELD_EMBED_FIELDS)+"."+eachKey)));
        });
    }

    public AuthorComponent getAuthor() {
        return author;
    }

    public void setAuthor(AuthorComponent author) {
        this.author = author;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isShowTimestamp() {
        return showTimestamp;
    }

    public void setShowTimestamp(boolean showTimestamp) {
        this.showTimestamp = showTimestamp;
    }

    public ImageComponent getImage() {
        return image;
    }

    public void setImage(ImageComponent image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FieldComponent> getFields() {
        return fields;
    }

    public void setFields(List<FieldComponent> fields) {
        this.fields = fields;
    }

    public boolean isAuthorNull(){
        return this.author == null;
    }

    public boolean isImageNull(){
        return this.image == null;
    }

    public boolean isFooterNull(){
        return this.footer == null;
    }

    public boolean isThumbnailNull(){
        return this.thumbnail == null;
    }

    private String asPath(@NotNull String path){
        return Message.FIELD_EMBED+"."+path;
    }

    public void setFooter(FooterComponent footer) {
        this.footer = footer;
    }

    public ImageComponent getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageComponent thumbnail) {
        this.thumbnail = thumbnail;
    }

    public FieldComponent addField(@NotNull FieldComponent component){
        fields.add(component);
        return component;
    }

    public FieldComponent addField(@NotNull FlatFileSection section){
        final FieldComponent component = new FieldComponent(section);
        fields.add(component);
        return component;
    }

    public FieldComponent addField(@NotNull String title, @NotNull String value, boolean inline){
        final FieldComponent component = new FieldComponent(title, value, inline);
        fields.add(component);
        return component;
    }

    public void removeField(@NotNull FieldComponent component){
        fields.remove(component);
    }

    public Iterator<FieldComponent> componentIterator(){
        return new ArrayList<FieldComponent>(fields).stream().iterator();
    }

    public FooterComponent getFooter() {
        return footer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbedComponent)) return false;

        EmbedComponent that = (EmbedComponent) o;

        if (showTimestamp != that.showTimestamp) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (footer != null ? !footer.equals(that.footer) : that.footer != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
        return thumbnail != null ? thumbnail.equals(that.thumbnail) : that.thumbnail == null;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (footer != null ? footer.hashCode() : 0);
        result = 31 * result + (showTimestamp ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("author", author)
                .append("color", color)
                .append("footer", footer)
                .append("showTimestamp", showTimestamp)
                .append("image", image)
                .append("description", description)
                .append("fields", fields)
                .append("thumbnail", thumbnail)
                .toString();
    }
}
