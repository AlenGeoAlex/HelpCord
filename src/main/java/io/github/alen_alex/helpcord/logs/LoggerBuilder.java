package io.github.alen_alex.helpcord.logs;

import io.github.alen_alex.helpcord.HelpCord;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class LoggerBuilder {

    public Logger getLogger(){
        final Logger logger = Logger.getLogger(HelpCord.NAME);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        Formatter formatter = new LoggerFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
        return logger;
    }

}
