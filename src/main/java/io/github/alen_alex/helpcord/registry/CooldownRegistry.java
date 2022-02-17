package io.github.alen_alex.helpcord.registry;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.enums.CooldownKeys;
import io.github.alen_alex.helpcord.exceptions.RegisterExistingData;
import io.github.alen_alex.helpcord.models.Cooldowns;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CooldownRegistry {

    private final HelpCord instance;
    private final HashMap<String, Cooldowns> register;

    public CooldownRegistry(HelpCord instance) {
        this.instance = instance;
        this.register = new HashMap<>();
    }

    public void registerCooldown(@NotNull Cooldowns cooldowns) throws RegisterExistingData {
        if(register.containsKey(cooldowns.getCooldownFor()))
            throw new RegisterExistingData("A Cooldown is already registered for the given handler"+cooldowns.getCooldownFor());
        else this.register.put(cooldowns.getCooldownFor(),cooldowns);
    }

    public boolean inCooldown(@NotNull String playerID, CooldownKeys key){
        if(register.containsKey(key.getKey())){
            //TODO CHECK register.get(key).
            return true;
        }else return false;
    }

    public long updateCooldown(@NotNull String playerID, CooldownKeys keys){
        //TODO
        return 0;
    }
    public boolean isCooldownRegistered(@NotNull String name){
        return register.containsKey(name);
    }

    public Cooldowns getCooldownFor(@NotNull String name){
        return register.get(name);
    }

    public void unregister(@NotNull String name){
        this.register.get(name).flush();
        this.register.remove(name);
    }


}
