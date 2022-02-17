package io.github.alen_alex.helpcord.logs;

import io.github.alen_alex.helpcord.HelpCord;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.*;

public class Debug extends Formatter {

    private static final Logger logger;
    private static boolean enabled;
    //private static List<String> debugList;

    static {
        logger = Logger.getLogger("HC DEBUG");
        enabled = false;
    }

    private static final String WARN_PREFIX = "[HC DEBUG - WARN] %s \n";
    private static final String SEVERE_PREFIX = "[HC DEBUG  - ERROR] %s \n";
    private static final String NORMAL_PREFIX = "[HC DEBUG] %s \n";

    public Debug() {
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(this);
        logger.addHandler(handler);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        Debug.enabled = enabled;
    }

    @Override
    public String format(LogRecord record) {
        if(record.getLevel() == Level.INFO)
            return String.format(NORMAL_PREFIX, record.getMessage());

        if(record.getLevel() == Level.WARNING)
            return String.format(WARN_PREFIX,record.getMessage());

        if(record.getLevel() == Level.SEVERE)
            return String.format(SEVERE_PREFIX, record.getMessage());

        return String.format(NORMAL_PREFIX,record.getMessage());
    }

    public static void debug(@NotNull String message){
        if(enabled)
            logger.info(message);
    }

    public static void err(@NotNull String message){
        if(enabled)
            logger.severe(message);
    }

    public static void warn(@NotNull String message){
        if(enabled)
            logger.warning(message);
    }




}
