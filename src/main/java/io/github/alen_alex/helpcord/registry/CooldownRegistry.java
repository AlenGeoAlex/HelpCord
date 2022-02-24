package io.github.alen_alex.helpcord.registry;

import io.github.alen_alex.helpcord.HelpCord;
import io.github.alen_alex.helpcord.enums.CooldownKeys;
import io.github.alen_alex.helpcord.exceptions.RegisterExistingData;
import io.github.alen_alex.helpcord.models.Cooldowns;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CooldownRegistry {

    private final HelpCord instance;

    public CooldownRegistry(HelpCord instance) {
        this.instance = instance;
    }



}
