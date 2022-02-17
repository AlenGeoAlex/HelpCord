package io.github.alen_alex.helpcord.utils;

import io.github.alen_alex.helpcord.enums.CodeLanguage;
import io.github.alen_alex.helpcord.enums.Tags;
import io.github.alen_alex.helpcord.models.CodeBlocks;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasteUtils {

    private static final Pattern LANGUAGE_REGEX;
    private static final Pattern CODE_BLOCK_REPLACE_REGEX;

    static {
        LANGUAGE_REGEX = Pattern.compile(Tags.CODE_BLOCK_REGEX.getTag());
        CODE_BLOCK_REPLACE_REGEX = Pattern.compile("");
    }

    public static CodeBlocks parseLanguage(@NotNull String message){
        final Matcher matcher = LANGUAGE_REGEX.matcher(message);
        final CodeLanguage language;
        if(matcher.find()){
           if(EnumUtils.isValidEnum(CodeLanguage.class,matcher.group(1).toUpperCase())){
               language = Enum.valueOf(CodeLanguage.class,matcher.group(1));
           }else {
               language = CodeLanguage.PLAIN;
           }
        }else {
            language = CodeLanguage.PLAIN;
        }

        final String parsedMessage = message.replace(Tags.CODE_BLOCK.getTag(),"").replace(matcher.group(1),"");
        return new CodeBlocks(message,parsedMessage,language);
    }

    public static boolean isCodeBlocked(@NotNull String message){
        return (message.startsWith(Tags.CODE_BLOCK.getTag()) && message.endsWith(Tags.CODE_BLOCK.getTag()));
    }

}
