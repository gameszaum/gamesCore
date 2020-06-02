package com.gameszaum.core.bungee.plugin;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public abstract class GamesBungee extends Plugin implements BungeeLifeCycle {

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    @Override
    public void registerListener(Listener... listener) {
        for (Listener l : listener) {
            getProxy().getPluginManager().registerListener(this, l);
        }
    }

    private Configuration generateConfig(GamesBungee bungee) {
        if (!bungee.getDataFolder().exists())
            bungee.getDataFolder().mkdirs();

        File file = new File(bungee.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream stream = bungee.getResourceAsStream("config.yml")) {
                Files.copy(stream, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}