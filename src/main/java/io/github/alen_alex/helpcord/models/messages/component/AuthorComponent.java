package io.github.alen_alex.helpcord.models.messages.component;

import de.leonhard.storage.sections.FlatFileSection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

public class AuthorComponent {

    public static final String FIELD_AUTHOR_NAME = "name";
    public static final String FIELD_AUTHOR_URL = "url";
    public static final String FIELD_AUTHOR_ICON = "icon";

    private final String author;
    private final String url;
    private final ImageComponent icon;

    public AuthorComponent(String author, String url, ImageComponent icon) {
        this.author = author;
        this.url = url;
        this.icon = icon;
    }

    public AuthorComponent(@NotNull FlatFileSection section) {
        this.author = section.getString(FIELD_AUTHOR_NAME);
        if(section.contains(FIELD_AUTHOR_URL) && StringUtils.isNotBlank(FIELD_AUTHOR_URL)) {
            final String tempUrl = section.getString("url");
            this.url = !tempUrl.startsWith("https") || !tempUrl.startsWith("http") ? "http://"+tempUrl : tempUrl;
        }
        else this.url = "";
        this.icon = new ImageComponent(section.getMapParameterized("icon"));
    }

    public String author(){
        return this.author;
    }

    public String url(){
        return this.url;
    }

    public ImageComponent icon(){
        return this.icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorComponent)) return false;

        AuthorComponent that = (AuthorComponent) o;

        if (!author.equals(that.author)) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return icon != null ? icon.equals(that.icon) : that.icon == null;
    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("author", author)
                .append("url", url)
                .append("icon", icon)
                .toString();
    }
}
