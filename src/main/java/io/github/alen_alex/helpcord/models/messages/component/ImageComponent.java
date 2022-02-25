package io.github.alen_alex.helpcord.models.messages.component;

import de.leonhard.storage.sections.FlatFileSection;
import io.github.alen_alex.helpcord.HelpCord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

public class ImageComponent{

    public static final String FIELD_LOCAL = "local";
    public static final String FIELD_URL = "url";

    private final boolean local;
    private final String url;

    private boolean valid = false;

    public ImageComponent(boolean local, String url) {
        this.local = local;
        this.url = url;
    }

    public ImageComponent(String url) {
        this.url = url;
        if(url.startsWith("http") || url.startsWith("https"))
            local = false;
        else local = true;

        if(StringUtils.isNotBlank(url))
            valid = true;
    }

    public ImageComponent(@NotNull FlatFileSection section) {
        this.url = section.getString(FIELD_LOCAL);
        this.local = section.getBoolean(FIELD_LOCAL);

        if(StringUtils.isNotBlank(url))
            valid = true;
    }

    public ImageComponent(Map<String, Object> map) {
        this.url = (String) map.get(FIELD_URL);
        this.local = Boolean.parseBoolean((String) map.get(FIELD_LOCAL));

        if(StringUtils.isNotBlank(url))
            valid = true;
    }

    public boolean local(){
        return this.local;
    }

    public String url(){
        return this.url;
    }

    public File asFile(){
        return new File(HelpCord.getInstance().getDataFolder().getAbsolutePath()+this.url);
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageComponent)) return false;

        ImageComponent that = (ImageComponent) o;

        if (local != that.local) return false;
        if (valid != that.valid) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = (local ? 1 : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (valid ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("local", local)
                .append("url", url)
                .append("valid", valid)
                .toString();
    }
}
