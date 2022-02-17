package io.github.alen_alex.helpcord.logs;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerFormatter extends Formatter {

    private static final String WARN_PREFIX = "[HelpCord - WARN] %s \n";
    private static final String SEVERE_PREFIX = "[HelpCord - ERROR] %s \n";
    private static final String NORMAL_PREFIX = "[HelpCord] %s \n";

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


}
