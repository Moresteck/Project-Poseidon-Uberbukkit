package org.bukkit.command.defaults;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import pl.moresteck.uberbukkit.Uberbukkit;

public class FlushInvCommand extends Command {
    public FlushInvCommand() {
        super("flushinv");
        this.description = "Empties the inventory queue into player's inventory or on the ground";
        this.usageMessage = "/flushinv";
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        Player player = null;
        if (args.length != 1 && sender instanceof Player)  {
            player = (Player) sender;
        } else if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        } else {
            player = Bukkit.getPlayer(args[0]);
        }

        EntityPlayer entity = ((CraftPlayer)player).getHandle();
        if (Uberbukkit.getPVN() <= 6) {
            ArrayList<ItemStack> queue = entity.packet5.queue.dropAllQueue();
            for (ItemStack item : queue) {
                System.out.println("Drop queue id: " + item.id + ", dmg: " + item.damage + ", cnt: " + item.count);
                HashMap<Integer, org.bukkit.inventory.ItemStack> map = player.getInventory().addItem(new CraftItemStack(item));
                // drop what couldn't fit in the inventory
                for (org.bukkit.inventory.ItemStack stack : map.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), stack);
                }
            }

            player.sendMessage(ChatColor.YELLOW + "Your item queue has been restored!");
            if (sender != player) {
                sender.sendMessage(ChatColor.YELLOW + "Item queue of " + player.getName() + " has been restored!");
            }
        }

        return true;
    }
}
