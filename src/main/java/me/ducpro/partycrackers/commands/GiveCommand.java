package me.ducpro.partycrackers.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;

import me.ducpro.partycrackers.items.CrackerBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@CommandAlias("partycrackers")
public class GiveCommand extends BaseCommand {
    private final CrackerBuilder crackerItemBuilder;

    @Inject
    public GiveCommand(CrackerBuilder crackerItemBuilder) {
        this.crackerItemBuilder = crackerItemBuilder;
    }

    @Default
    public void onDefault() { }

    @Subcommand("give")
    @CommandCompletion("@players @range:1-1")
    public void onGive(CommandSender sender, String playerName, int amount) {
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Invalid target player");
            return;
        }

        targetPlayer.getInventory().addItem(this.crackerItemBuilder.build(amount));
        sender.sendMessage("Successfully given " + playerName + " " + Integer.toString(amount) + " party crackers");
    }
}
