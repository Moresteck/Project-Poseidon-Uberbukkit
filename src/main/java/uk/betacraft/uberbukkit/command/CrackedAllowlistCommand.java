package uk.betacraft.uberbukkit.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.legacyminecraft.poseidon.util.CrackedAllowlist;

public class CrackedAllowlistCommand extends Command {
    public CrackedAllowlistCommand() {
        super("cracked");
        this.description = "Manages the cracked names allowlist";
        this.usageMessage = "/cracked";
        this.setPermission("uberbukkit.command.cracked");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length < 2) {
            sender.sendMessage(ChatColor.GRAY + "/cracked add <username> - Adds the username to cracked names allowlist");
            sender.sendMessage(ChatColor.GRAY + "/cracked rem <username> - Removes the username from cracked names allowlist");
            return true;
        }

        String crackedName = args[1];
        if (args[0].equalsIgnoreCase("add")) {
            if (CrackedAllowlist.get().addName(crackedName)) {
                sender.sendMessage(ChatColor.GREEN + "Username '" + crackedName + "' has been ADDED to the cracked names allowlist");
            } else {
                sender.sendMessage(ChatColor.RED + "Username '" + crackedName + "' is already on the cracked names allowlist");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("rem")) {
            if (CrackedAllowlist.get().removeName(crackedName)) {
                sender.sendMessage(ChatColor.GREEN + "Username '" + crackedName + "' has been REMOVED from the cracked names allowlist");
            } else {
                sender.sendMessage(ChatColor.RED + "Username '" + crackedName + "' isn't on the cracked names allowlist");
            }
            return true;
        }

        return true;
    }
}
