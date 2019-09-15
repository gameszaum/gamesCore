package com.gameszaum.core.plugin;

import com.gameszaum.core.Services;

public final class GamesCore extends GamesPlugin {

    @Override
    public void load() {
        Services.create(this);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
