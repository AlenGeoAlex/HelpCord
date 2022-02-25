package io.github.alen_alex.helpcord.registry;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.exceptions.IllegalRegistryKey;
import io.github.alen_alex.helpcord.interfaces.Module;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class ModuleRegistry {

    private static final HashMap<String, Module> availableModules;

    static {
        availableModules = new HashMap<>();
    }

    public final HelpCord instance;

    public ModuleRegistry(HelpCord instance) {
        this.instance = instance;
    }

    public <T extends Module> void registerModules(@NotNull T module) throws IllegalRegistryKey {
        if(availableModules.containsKey(module.name()))
            throw new IllegalRegistryKey("A module with the registry name "+module.name()+" already exists!");

        availableModules.put(module.name(),module);
    }

    @SafeVarargs
    public final <T extends Module> void registerModules(@NotNull T... module) throws IllegalRegistryKey {
        Arrays.stream(module).iterator().forEachRemaining(mod -> {
            try {
                this.registerModules(mod);
            } catch (IllegalRegistryKey e) {
                e.printStackTrace();
            }
        });
    }

    public void initAllEnabledModules(){
        availableModules.forEach((str, mod) -> {
            if(!mod.isEnabled())
                mod.registerModule();
        });
    }

    public boolean initModule(@NotNull String name){
        if(availableModules.containsKey(name)){
            final Module module = availableModules.get(name);
            if(module.isEnabled())
                return false;

            module.registerModule();
            return true;
        }else return false;
    }

    public boolean disableModule(@NotNull String name){
        if(availableModules.containsKey(name)){
            final Module module = availableModules.get(name);

            if(module.isEnabled()) {
                module.unregisterModule();
                return true;
            }else return false;
        }else return false;
    }

    public void disableAllEnabledModules(){
        availableModules.forEach((str, mod) -> {
            if(mod.isEnabled())
                mod.unregisterModule();
        });
    }

    public Iterator<Module> getAllModules(){
        return new ArrayList<Module>(availableModules.values()).iterator();
    }

    public Iterator<Module> getAllEnabledModules(){
        return new ArrayList<Module>(availableModules.values()).stream().filter(Module::isEnabled).iterator();
    }

    public Optional<Module> getIfPresent(@NotNull String name){
        return Optional.ofNullable(availableModules.get(name));
    }

}
