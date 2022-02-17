package io.github.alen_alex.helpcord.enums;

public enum CodeLanguage {
    //MISC
    PLAIN("plain"),
    DOCKER_FILE("dockerfile"),
    MARKDOWN("markdown"),

    //CONFIGURATIONS
    YAML("yaml"),
    JSON("json"),
    XML("xml"),
    INI("ini"),

    //WEB LANGS
    HTML("html"),
    CSS("css"),
    PHP("php"),

    //PROGRAMING LANGUAGES
    JAVA("java"),
    JAVASCRIPT("javascript"),
    TYPESCRIPT("typescript"),
    PYTHON("python"),
    KOTLIN("kotlin"),
    CPP("cpp"),
    CSHARP("csharp"),
    SHELL("shell"),
    RUBY("ruby"),
    RUST("rust"),
    SQL("sql"),
    GO("go"),
    ;

    private String language;

    CodeLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
