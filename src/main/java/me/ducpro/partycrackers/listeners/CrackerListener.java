package me.ducpro.partycrackers.listeners;

import org.bukkit.*;
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

    @Inject
    public CrackerListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!this.isPartyCracker(event.getItemDrop().getItemStack())) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Location location = event.getItemDrop().getLocation();
                World world = location.getWorld();

                world.spawnParticle(
                        Particle.EXPLOSION_NORMAL,
                        location,
                        1);

                world.playSound(
                        location,
                        Sound.ENTITY_GENERIC_EXPLODE,
                        1, 1);

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
}
