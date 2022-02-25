package io.github.alen_alex.helpcord.models.messages.component;

import de.leonhard.storage.sections.FlatFileSection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

public class FooterComponent {

    public static final String FIELD_TEXT = "text";
    public static final String FIELD_ICON = "icon";

    private String text;
    private ImageComponent icon;

    public FooterComponent(String text) {
        this.text = text;
    }

    public FooterComponent(String text, ImageComponent icon) {
        this.text = text;
        this.icon = icon;
    }

    public FooterComponent(@NotNull FlatFileSection section) {
        this.text = section.getString(FIELD_TEXT);
        this.icon = new ImageComponent(section.getMapParameterized("icon"));
    }

    public String getText() {
        return text;
    }

    public ImageComponent getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FooterComponent)) return false;

        FooterComponent that = (FooterComponent) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return icon != null ? icon.equals(that.icon) : that.icon == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("icon", icon)
                .toString();
    }
}
