package me.ducpro.partycrackers.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.List;

public class PartyCrackerConfiguration {
    public Material getCrackerMaterial() {
        return crackerMaterial;
    }

    public List<String> getCrackerLore() {
        return crackerLore;
    }

    public boolean isCrackerShiny() {
        return crackerShiny;
    }

    private final Plugin plugin;

    private String crackerMaterialPath = "cracker.material";
    private Material crackerMaterial;

    private String crackerLorePath = "cracker.lore";
    private List<String> crackerLore;

    private String crackerShinyPath = "cracker.shiny";
    private boolean crackerShiny;

    @Inject
    public PartyCrackerConfiguration(Plugin plugin) {
        this.plugin = plugin;

        this.loadConfigurationValues();
    }

    public void reloadConfiguration() {
        plugin.reloadConfig();

        this.loadConfigurationValues();
    }

    private void loadConfigurationValues() {
        crackerMaterial = Material.getMaterial(this.plugin.getConfig().getString(crackerMaterialPath));

        crackerLore = this.plugin.getConfig().getStringList(crackerLorePath);

        crackerShiny = this.plugin.getConfig().getBoolean(crackerShinyPath);
    }
}
