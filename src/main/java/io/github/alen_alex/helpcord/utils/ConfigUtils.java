package io.github.alen_alex.helpcord.utils;

import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;
import io.github.alen_alex.helpcord.enums.ConfigPath;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;

public class ConfigUtils {

    public static Iterator<ConfigPath> getStrictEnums(){
        return Arrays.stream(ConfigPath.values()).filter(ConfigPath::isStrict).iterator();
    }

    public static FlatFileSection getSectionOf(@NotNull FlatFile flatFile, String path){
        return new FlatFileSection(flatFile,path);
    }

    public static boolean saveResourceTo(@NotNull InputStream resourceStream, @NotNull File folderName, @NotNull String fileName) {
        final File file = new File(folderName.getAbsolutePath());
        if (!file.exists()) {
            try (OutputStream outputStream = new FileOutputStream(file, false)) {
                resourceStream.transferTo(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean saveResourceTo(@NotNull InputStream resourceStream, @NotNull String folderName, @NotNull String fileName) {
        final File file = new File(folderName + File.separator + folderName, fileName);
        if (!file.exists()) {
            try (OutputStream outputStream = new FileOutputStream(file, false)) {
                resourceStream.transferTo(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
