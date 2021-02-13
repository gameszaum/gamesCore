package com.gameszaum.core.bungee.command.base;

import com.gameszaum.core.bungee.command.asserts.BungeeCommandAsserts;
import com.gameszaum.core.bungee.command.builder.BungeeCommandBuilder;
import com.gameszaum.core.bungee.command.helper.BungeeCommandHelper;
import com.gameszaum.core.bungee.command.helper.impl.BungeeCommandHelperImpl;
import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeCommandBase implements BungeeCommandBuilder, BungeeCommandAsserts {

    private boolean async, assertPlayer;
    private String perm;

    @Getter
    private String[] alias;

    private void setAsync(boolean async) {
        this.async = async;
    }

    private void setPerm(String perm) {
        this.perm = perm;
    }

    private void setAssertPlayer(boolean assertPlayer) {
        this.assertPlayer = assertPlayer;
    }

    @Override
    public BungeeCommandAsserts runAsync() {
        setAsync(true);

        return this;
    }

    @Override
    public BungeeCommandAsserts assertPlayer() {
        setAssertPlayer(true);

        return this;
    }

    @Override
    public BungeeCommandAsserts assertPermision(String perm) {
        setPerm(perm);

        return this;
    }

    @Override
    public void register(Plugin plugin, String... alias) {
        this.alias = alias;

        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new Command(alias[0], perm, alias) {
            @Override
            public void execute(CommandSender sender, String[] args) {
                if (async) {
                    ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> {
                        if (assertPlayer && !(sender instanceof ProxiedPlayer)) {
                            sender.sendMessage(TextComponent.fromLegacyText("Only for players."));
                            return;
                        }
                        if (perm != null) {
                            if (sender.hasPermission(perm)) {
                                handle(sender, new BungeeCommandHelperImpl(), args);
                            } else {
                                sender.sendMessage(TextComponent.fromLegacyText("§cVocê não possui permissão para executar este comando."));
                            }
                        } else {
                            handle(sender, new BungeeCommandHelperImpl(), args);
                        }
                    });
                } else {
                    if (assertPlayer && !(sender instanceof ProxiedPlayer)) {
                        sender.sendMessage(TextComponent.fromLegacyText("Only for players."));
                        return;
                    }
                    if (perm != null) {
                        if (sender.hasPermission(perm)) {
                            handle(sender, new BungeeCommandHelperImpl(), args);
                        } else {
                            sender.sendMessage(TextComponent.fromLegacyText("§cVocê não possui permissão para executar este comando."));
                        }
                    } else {
                        handle(sender, new BungeeCommandHelperImpl(), args);
                    }
                }
            }
        });
    }

    @Override
    public abstract void handle(CommandSender sender, BungeeCommandHelper helper, String... args);
}
