package me.ducpro.partycrackers;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.*;
import me.ducpro.partycrackers.commands.GiveCommand;
import me.ducpro.partycrackers.items.CrackerBuilder;
import me.ducpro.partycrackers.items.CrackerItem;
import me.ducpro.partycrackers.listeners.CrackerListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public class PartyCrackersMain extends JavaPlugin {
    @Override
    public void onEnable() {
        final Plugin pluginInstance = this;
        Injector injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Plugin.class).toInstance(pluginInstance);
                    }
                },
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(CrackerBuilder.class).to(CrackerItem.class).in(Scopes.SINGLETON);
                    }
                }
        );

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(injector.getInstance(GiveCommand.class));

        this.registerListeners(
                injector.getInstance(CrackerListener.class)
        );
    }

    public void registerListeners(Listener...listeners) {
        for (Listener listener: listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {

    }
}
