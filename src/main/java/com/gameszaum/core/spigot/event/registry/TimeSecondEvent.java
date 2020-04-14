package com.gameszaum.core.spigot.event.registry;

import com.gameszaum.core.spigot.event.EventBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class TimeSecondEvent extends EventBuilder {

    @Getter
    private Player player;

}
