package io.github.alen_alex.helpcord.handler;

import io.github.alen_alex.helpcord.HelpCord;

public class CommandHandler {

    private final HelpCord instance;

    public CommandHandler(HelpCord instance) {
        this.instance = instance;
    }

    public HelpCord getInstance() {
        return instance;
    }
}
