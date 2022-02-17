package io.github.alen_alex.helpcord.enums;

public enum Tags {
    CODE_BLOCK("```"),
    CODE_BLOCK_REGEX("```(\\w+)\\n[^`]+```"),
    PASTE_API("https://api.pastes.dev/post"),
    PASTE_DOMAIN("https://paste.alenalex.me"),
    ;

    Tags(String tag) {
        this.tag = tag;
    }

    private String tag;

    public String getTag() {
        return tag;
    }
}
