package io.github.alen_alex.helpcord.models;

import de.leonhard.storage.sections.FlatFileSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Cooldowns {

    private final String cooldownFor;
    private final HashMap<String, Long> cooldownMap;
    private long cooldownDuration;
    private List<String> excludedRoles;

    private Cooldowns(String cooldownFor, long cooldownDuration, List<String> excludedRoles) {
        this.cooldownFor = cooldownFor;
        this.cooldownDuration = cooldownDuration;
        this.cooldownMap = new HashMap<>();
        this.excludedRoles = excludedRoles;
    }

    public boolean containsUser(@NotNull String id){
        return (cooldownMap.containsKey(id));
    }

    public long incrementAndGet(@NotNull String id){
        final long newCooldown = System.currentTimeMillis() + cooldownDuration;
        this.cooldownMap.put(id,newCooldown);
        return newCooldown;
    }

    public void removeCooldown(@NotNull String id){
        cooldownMap.remove(id);
    }

    public String getCooldownFor() {
        return cooldownFor;
    }

    public long getCooldownDuration() {
        return cooldownDuration;
    }

    public void setCooldownDuration(long cooldownDuration) {
        this.cooldownDuration = cooldownDuration;
    }

    public void flush(){
        this.cooldownMap.clear();
    }

    public List<String> getExcludedRoles() {
        return excludedRoles;
    }

    public static Cooldowns buildFrom(@NotNull FlatFileSection section, @NotNull String name ){
        return new Cooldowns(name, TimeUnit.SECONDS.toMillis(section.getInt("duration-in-sec")), section.getStringList("excluded-roles"));
    }
}
