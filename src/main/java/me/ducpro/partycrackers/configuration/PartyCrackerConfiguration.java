package me.ducpro.partycrackers.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public Particle getExplosionParticle() {
        return explosionParticle;
    }

    public int getExplosionParticleAmount() {
        return explosionParticleAmount;
    }

    private final Plugin plugin;

    private final String crackerMaterialPath = "cracker.material";
    private Material crackerMaterial;

    private final String crackerLorePath = "cracker.lore";
    private List<String> crackerLore;

    private final String crackerShinyPath = "cracker.shiny";
    private boolean crackerShiny;

    private final String explosionSoundPath = "cracker.explosionSound";
    private Sound explosionSound;

    private final String explosionParticlePath = "cracker.explosionParticle";
    private Particle explosionParticle;

    private final String explosionParticleAmountPath = "cracker.explosionParticleAmount";
    private int explosionParticleAmount;

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
        this.crackerMaterial = Material.getMaterial(this.plugin.getConfig().getString(this.crackerMaterialPath));

        this.crackerLore = this.plugin.getConfig().getStringList(this.crackerLorePath);

        this.crackerShiny = this.plugin.getConfig().getBoolean(this.crackerShinyPath);

        this.explosionSound = Sound.valueOf(this.plugin.getConfig().getString(this.explosionSoundPath));

        this.explosionParticle = Particle.valueOf(this.plugin.getConfig().getString(this.explosionParticlePath));

        this.explosionParticleAmount = this.plugin.getConfig().getInt(this.explosionParticleAmountPath);
    }
}
