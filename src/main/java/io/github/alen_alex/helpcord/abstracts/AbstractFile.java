package io.github.alen_alex.helpcord.abstracts;

import de.leonhard.storage.Json;
import de.leonhard.storage.Toml;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.sections.FlatFileSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public abstract class AbstractFile {

    protected final FlatFile baseFile;
    private final HashMap<String, FlatFileSection> sectionCache = new HashMap<>();

    public AbstractFile(FlatFile baseFile) {
        this.baseFile = baseFile;
    }

    public AbstractFile(@NotNull final String fileSrc, @NotNull final String name, @Nullable Class classType) {
        if(classType == null){
            this.baseFile = new Yaml(name,fileSrc);
        }else if(classType.getName().equals("Json")){
            this.baseFile = new Json(name,fileSrc);
        }else if(classType.getName().equals("Toml")){
            this.baseFile = new Toml(name,fileSrc);
        }else {
            this.baseFile = new Yaml(name,fileSrc);
        }
    }

    public String getString(@NotNull String path){
        return this.baseFile.getString(path);
    }

    public int getInteger(@NotNull String path){
        return this.baseFile.getInt(path);
    }

    public boolean getBoolean(@NotNull String path){
        return this.baseFile.getBoolean(path);
    }

    public double getDouble(@NotNull String path){
        return this.baseFile.getDouble(path);
    }

    public <T extends Enum<T>> T getEnum(@Nullable String path, Class<T> enumType){
        return this.baseFile.getEnum(path,enumType);
    }

    public FlatFileSection getSectionOn(@NotNull String path){
        if(sectionCache.containsKey(path))
            return sectionCache.get(path);
        else {
            FlatFileSection flatFileSection = new FlatFileSection(this.baseFile, path);
            sectionCache.put(path,flatFileSection);
            return flatFileSection;
        }
    }

    public void flush(){
        this.baseFile.getFileData().clear();
        this.sectionCache.clear();
    }

    public boolean contains(@NotNull String path){
        return this.baseFile.contains(path);
    }

    public boolean putIfAbsent(@NotNull String path, @NotNull Object def){
        if(this.baseFile.contains(path))
            return false;
        else {
            this.baseFile.set(path,def);
            return true;
            }
        }

}
