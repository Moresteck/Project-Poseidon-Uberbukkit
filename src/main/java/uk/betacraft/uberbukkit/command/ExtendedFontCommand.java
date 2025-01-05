package uk.betacraft.uberbukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ExtendedFontCommand extends Command {
    public ExtendedFontCommand() {
        super("32626180319180");
        this.description = "Makes the server aware of the player having extended-font installed";
        this.usageMessage = "/32626180319180";
        this.setHidden(true);
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player-only reserved command, not for manual use.");
            return true;
        }

        CraftPlayer p = (CraftPlayer) sender;
        p.getHandle().netServerHandler.networkManager.hasExtendedFontMod = true;

        return true;
    }
}
