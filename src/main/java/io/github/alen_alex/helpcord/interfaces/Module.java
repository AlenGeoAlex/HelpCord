package io.github.alen_alex.helpcord.interfaces;

public interface Module {

    String name();

    String description();

    void registerModule();

    void unregisterModule();

    boolean isEnabled();

    boolean shouldEnable();

}
