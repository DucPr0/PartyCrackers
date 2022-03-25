package me.ducpro.partycrackers.items;

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

public class CrackerItem implements CrackerBuilder {
    private Plugin plugin;

    private Material type;
    private List<String> lore;
    private boolean shiny;

    @Inject
    public CrackerItem(Plugin plugin) {
        this.plugin = plugin;

        setType(Material.FIREWORK_ROCKET);
        setLore(Arrays.asList("test1", "test2", "test3"));
        setShiny(true);
    }

    public void setType(Material type) {
        this.type = type;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    public ItemStack build(int amount) {
        ItemStack crackers = new ItemStack(this.type, amount);

        ItemMeta itemMeta = crackers.getItemMeta();
        itemMeta.setLore(this.lore);
        if (this.shiny) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(this.plugin, "isCracker"), PersistentDataType.BYTE, (byte) 1);

        crackers.setItemMeta(itemMeta);

        return crackers;
    }
}
