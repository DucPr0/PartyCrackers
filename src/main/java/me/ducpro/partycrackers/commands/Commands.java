package me.ducpro.partycrackers.commands;

import co.aikar.commands.annotation.*;

import me.ducpro.partycrackers.configuration.PartyCrackerConfiguration;
import me.ducpro.partycrackers.items.PartyCrackerItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@CommandAlias("partycrackers|pc")
public class Commands extends co.aikar.commands.BaseCommand {
    private final PartyCrackerItem crackerItemBuilder;
    private final PartyCrackerConfiguration partyCrackerConfiguration;

    @Inject
    public Commands(
            PartyCrackerItem crackerItemBuilder,
            PartyCrackerConfiguration partyCrackerConfiguration) {
        this.crackerItemBuilder = crackerItemBuilder;
        this.partyCrackerConfiguration = partyCrackerConfiguration;
    }

    @Default
    public void onDefault() { }

    @Subcommand("give")
    @CommandPermission("partycrackers.give")
    @CommandCompletion("@players @range:1-5")
    public void onGive(CommandSender sender, String playerName, int amount) {
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Invalid target player");
            return;
        }

        targetPlayer.getInventory().addItem(this.crackerItemBuilder.getItem(amount));

        sender.sendMessage("Successfully given " + playerName + " " + Integer.toString(amount) + " party crackers.");
    }

    @Subcommand("reload")
    @CommandPermission("partycrackers.reload")
    public void onReload(CommandSender sender) {
        this.partyCrackerConfiguration.reloadConfiguration();

        sender.sendMessage("Successfully reloaded plugin.");
    }
}
