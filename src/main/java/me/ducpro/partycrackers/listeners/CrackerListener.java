package me.ducpro.partycrackers.listeners;

import me.ducpro.partycrackers.configuration.PartyCrackerConfiguration;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;

public class CrackerListener implements Listener {
    private final Plugin plugin;
    private final PartyCrackerConfiguration partyCrackerConfiguration;

    @Inject
    public CrackerListener(
            Plugin plugin,
            PartyCrackerConfiguration partyCrackerConfiguration) {
        this.plugin = plugin;
        this.partyCrackerConfiguration = partyCrackerConfiguration;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!this.isPartyCracker(event.getItemDrop().getItemStack())) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                handleExplosion(event.getItemDrop());
                event.getItemDrop().remove();
            }
        }.runTaskLater(this.plugin, 40);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (this.isPartyCracker(event.getItem().getItemStack())) {
            event.setCancelled(true);
        }
    }

    private boolean isPartyCracker(ItemStack itemStack) {
        return itemStack.getItemMeta()
                .getPersistentDataContainer()
                .has(new NamespacedKey(this.plugin, "isCracker"), PersistentDataType.BYTE);
    }

    private void handleExplosion(Item itemDrop) {
        this.playEffects(itemDrop);
    }

    private void playEffects(Item itemDrop) {
        Location location = itemDrop.getLocation();
        World world = location.getWorld();

        world.spawnParticle(
                this.partyCrackerConfiguration.getExplosionParticle(),
                location,
                this.partyCrackerConfiguration.getExplosionParticleAmount());

        world.playSound(
                location,
                this.partyCrackerConfiguration.getExplosionSound(),
                1, 1);
    }

    private void dropRewards(Item itemDrop) {

    }
}
