package me.ducpro.partycrackers.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CrackerBuilder {
    public void setType(Material type);

    public void setLore(List<String> lore);

    public void setShiny(boolean shiny);

    public ItemStack build(int amount);
}
