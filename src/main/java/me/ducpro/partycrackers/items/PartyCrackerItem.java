package me.ducpro.partycrackers.items;

import me.ducpro.partycrackers.configuration.PartyCrackerConfiguration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class PartyCrackerItem {
    private final Plugin plugin;
    private final PartyCrackerConfiguration partyCrackerConfiguration;

    @Inject
    public PartyCrackerItem(
            Plugin plugin,
            PartyCrackerConfiguration partyCrackerConfiguration) {
        this.plugin = plugin;
        this.partyCrackerConfiguration = partyCrackerConfiguration;
    }

    public ItemStack getItem(int amount) {
        ItemStack crackers = new ItemStack(this.partyCrackerConfiguration.getCrackerMaterial(), amount);

        ItemMeta itemMeta = crackers.getItemMeta();
        itemMeta.setLore(this.partyCrackerConfiguration.getCrackerLore());
        if (this.partyCrackerConfiguration.isCrackerShiny()) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, "isCracker"), PersistentDataType.BYTE, (byte) 1);

        crackers.setItemMeta(itemMeta);

        return crackers;
    }
}
