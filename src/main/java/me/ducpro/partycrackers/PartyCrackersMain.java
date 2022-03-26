package me.ducpro.partycrackers;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.*;
import me.ducpro.partycrackers.commands.Commands;
import me.ducpro.partycrackers.configuration.PartyCrackerConfiguration;
import me.ducpro.partycrackers.items.PartyCrackerItem;
import me.ducpro.partycrackers.listeners.CrackerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyCrackersMain extends JavaPlugin {
    @Override
    public void onEnable() {
        final Plugin pluginInstance = this;
        Injector injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Plugin.class).toInstance(pluginInstance);
                        bind(PartyCrackerItem.class).in(Scopes.SINGLETON);
                        bind(PartyCrackerConfiguration.class).in(Scopes.SINGLETON);
                    }
                }
        );

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(injector.getInstance(Commands.class));

        this.registerListeners(
                injector.getInstance(CrackerListener.class)
        );

        this.saveDefaultConfig();
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
