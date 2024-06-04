package me.corecraft.kelp.config;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import org.jetbrains.annotations.NotNull;

public class KelpConfig implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        String[] header = {
          "Github: https://github.com/CoreCraftMC/Kelp"
        };

        String[] deprecation = {
          "This section is deprecated and is marked for removal."
        };
    }
}