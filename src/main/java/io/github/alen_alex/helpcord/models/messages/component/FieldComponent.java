package io.github.alen_alex.helpcord.models.messages.component;

import de.leonhard.storage.sections.FlatFileSection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

public class FieldComponent {

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_INLINE = "inline";

    private final String title;
    private final String value;
    private final boolean inline;

    public FieldComponent(String title, String value, boolean inline) {
        this.title = title;
        this.value = value;
        this.inline = inline;
    }

    public FieldComponent(String title, String value) {
        this.title = title;
        this.value = value;
        this.inline = false;
    }

    public FieldComponent(@NotNull FlatFileSection section) {
        this.title = section.getString(FIELD_TITLE);
        this.value = section.getString(FIELD_VALUE);
        if(section.contains(FIELD_INLINE)){
            inline = section.getBoolean(FIELD_INLINE);
        }else inline = false;
    }

    public String title(){
        return this.title;
    }

    public String value(){
        return this.value;
    }

    public boolean isInline(){
        return this.inline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldComponent)) return false;

        FieldComponent that = (FieldComponent) o;

        if (inline != that.inline) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (inline ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("value", value)
                .append("inline", inline)
                .toString();
    }
}
