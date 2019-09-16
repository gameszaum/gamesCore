package com.gameszaum.core.event.registry;

import com.gameszaum.core.event.EventBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class TimeSecondEvent extends EventBuilder {

    @Getter
    private Player player;

}
