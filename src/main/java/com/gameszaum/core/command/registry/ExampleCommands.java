package com.gameszaum.core.command.registry;

import com.gameszaum.core.command.Command;
import com.gameszaum.core.command.builder.CommandBase;
import com.gameszaum.core.command.helper.CommandHelper;
import com.gameszaum.core.menu.MenuBuilder;
import com.gameszaum.core.menu.helper.Menu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExampleCommands {

    public static void setup() {
        Command.create(new CommandBase() {
            @Override
            public void handler(CommandSender commandSender, CommandHelper helper, String... args) {
                if (helper.isPlayer(commandSender)) {
                    Player player = helper.getPlayer(commandSender);

                    player.sendMessage("/test - player command.");
                } else {
                    commandSender.sendMessage("/test - console command.");
                }
            }
        }).runAsync().setCommand("test");

        Command.create(new CommandBase() {
            @Override
            public void handler(CommandSender commandSender, CommandHelper helper, String... args) throws Exception {
                MenuBuilder.buildMenu(new Menu("Teste", 3)).showMenu(helper.getPlayer(commandSender));
            }
        }).onlyPlayer().setCommand("menu");
    }

}
