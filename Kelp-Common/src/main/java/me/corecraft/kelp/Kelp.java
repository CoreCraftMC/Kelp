package me.corecraft.kelp;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import me.corecraft.kelp.config.KelpConfig;
import java.io.File;

public class Kelp {

    private static SettingsManager config;

    public static void init(File dataFolder) {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "kelp.yml"), builder)
                .configurationData(KelpConfig.class)
                .create();
    }

    /**
     * @return gets kelp.yml
     */
    public static SettingsManager getKelpConfig() {
        return config;
    }
}