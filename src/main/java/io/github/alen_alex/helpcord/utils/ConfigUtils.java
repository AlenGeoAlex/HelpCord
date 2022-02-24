package io.github.alen_alex.helpcord.utils;

import io.github.alen_alex.helpcord.enums.ConfigPath;

import java.util.Arrays;
import java.util.Iterator;

public class ConfigUtils {

    public static Iterator<ConfigPath> getStrictEnums(){
        return Arrays.stream(ConfigPath.values()).filter(ConfigPath::isStrict).iterator();
    }

}
