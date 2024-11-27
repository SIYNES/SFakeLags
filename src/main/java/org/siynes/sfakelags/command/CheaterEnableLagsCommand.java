package org.siynes.sfakelags.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.siynes.sfakelags.TrollingManager;
import org.siynes.sfakelags.TrollingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheaterEnableLagsCommand implements CommandExecutor, TabCompleter {
    private final TrollingManager trollingManager;

    public CheaterEnableLagsCommand(TrollingManager trollingManager) {
        this.trollingManager = trollingManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            return false;
        }

        String action = args[0].toLowerCase();
        String typeString = args[1].toLowerCase();
        Player target = Bukkit.getPlayer(args[2]);

        if (target == null) {
            commandSender.sendMessage("Player offline");
            return true;
        }

        TrollingType type;
        switch (typeString) {
            case "lag":
                type = TrollingType.LAGS;
                break;
            case "lagpvp":
                type = TrollingType.LAGSPVP;
                break;
            default:
                return false;
        }

        switch (action) {
            case "enable":
                if (!trollingManager.isTrolling(target, type)) {
                    trollingManager.addTrolling(target, type);
                    commandSender.sendMessage(typeString + " enabled for " + target.getName());
                } else {
                    commandSender.sendMessage(typeString + " is already enabled for " + target.getName());
                }
                break;

            case "disable":
                if (trollingManager.isTrolling(target, type)) {
                    trollingManager.removeTrolling(target, type);
                    commandSender.sendMessage(typeString + " disabled for " + target.getName());
                } else {
                    commandSender.sendMessage(typeString + " is not enabled for " + target.getName());
                }
                break;

            default:
                return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("enable", "disable"));
        } else if (args.length == 2) {
            completions.addAll(Arrays.asList("lag", "lagpvp"));
        } else if (args.length == 3) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }

        return completions;
    }
}