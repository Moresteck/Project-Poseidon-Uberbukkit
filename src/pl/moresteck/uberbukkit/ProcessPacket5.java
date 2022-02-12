package pl.moresteck.uberbukkit;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemArmor;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet5EntityEquipment;

public class ProcessPacket5 {
    public InventoryQueue queue;
    private EntityHuman player;

    public ProcessPacket5(EntityHuman player) {
        this.queue = new InventoryQueue();
        this.player = player;
    }

    public void process(Packet5EntityEquipment packet) {
        // we have to find out what's being changed: if any item(stack) is being removed, or if any is being added
        // only allow additions of items if there's enough of them in the queue
        // mark removals by adding the removed items into the queue
        if (player == null || player.inventory == null) return;
        InventoryQueue unfinalized = this.queue.clone();

        // scan inventories for changes
        ItemStack[] stackarray = packet.a == -1 ? player.inventory.items : (packet.a == -3 ? player.inventory.armor : player.inventory.craft);
        //System.out.println("invslot: " + packet.a + ", Size of incoming inv is " + packet.items.length + ", while " + stackarray.length + " is expected");
        if (packet.items.length < stackarray.length) {
            // invalid packet
            return;
        }

        // craft slots are always empty for some reason
        if (packet.a == -2) {
            for (int i = 0; i < stackarray.length; i++) {
                int clid = packet.items[i] != null ? packet.items[i].id : -1;
                int cldmg = packet.items[i] != null ? packet.items[i].damage : -1;
                int clcnt = packet.items[i] != null ? packet.items[i].count : -1;
                
                int srvid = stackarray[i] != null ? stackarray[i].id : -1;
                int srvdmg = stackarray[i] != null ? stackarray[i].damage : -1;
                int srvcnt = stackarray[i] != null ? stackarray[i].count : -1;
                System.out.println("Index: " + i);
                System.out.println("cl.id = " + clid + ", srv.id: " + srvid);
                System.out.println("cl.dmg = " + cldmg + ", srv.dmg: " + srvdmg);
                System.out.println("cl.cnt = " + clcnt + ", srv.cnt: " + srvcnt);
            }
            player.inventory.craft = packet.items;
            return;
        }

        for (int i = 0; i < stackarray.length; i++) {
            ItemStack serverside = stackarray[i];
            ItemStack client = packet.items[i];

            // if both are same, skip
            if (serverside == null && client == null) {
                continue;
            } else if (serverside != null && client != null && serverside.count == client.count && serverside.id == client.id && serverside.damage == client.damage) {
                continue;
            } else {
                // now we gotta find out what's different
                if (client == null && serverside != null) {
                    unfinalized.addStackToQueue(serverside);
                    System.out.println("Removing id: " + serverside.id + ", dmg: " + serverside.damage + ", cnt: " + serverside.count);
//                    for (int j = 0; j < serverside.count; j++) {
//                        unfinalized.addStackToQueue(serverside);
//                    }
                } else if (client != null && serverside == null) {
                    // check if they can put the item in this armor slot
                    if (packet.a == -3) {
                        Item item = Item.byId[client.id];
                        if (item != null && item instanceof ItemArmor) {
                            int fit = ((ItemArmor)item).bk;
                            if ((i == 0 && fit != 3) || (i == 1 && fit != 2) || (i == 2 && fit != 1) || (i == 3 && fit != 0)) {
                                System.out.println("Armor slot " + i + " but item is at " + fit);
                                return;
                            }
                        } else return;
                    }

                    // check if we can allow for addition
                    if (!unfinalized.hasInQueue(client)) {
                        System.out.println("Tried to add at " + i + " index - id: " + client.id + ", dmg: " + client.damage + ", cnt: " + client.count);
                        return;
                    }
                    System.out.println("Adding id: " + client.id + ", dmg: " + client.damage + ", cnt: " + client.count);
                } else if (client.count != serverside.count && client.id == serverside.id && client.damage == serverside.damage) {
                    int change = client.count - serverside.count;
                    if (change > 0) {
                        if (!unfinalized.hasInQueue(client)) {
                            System.out.println("Tried to add at " + i + " index - id: " + client.id + ", dmg: " + client.damage + ", cnt: " + client.count);
                            return;
                        }
                        System.out.println("Adding id: " + client.id + ", dmg: " + client.damage + ", cnt: " + client.count);
                    } else {
                        ItemStack toadd = client.cloneItemStack();
                        toadd.count = -change;
                        unfinalized.addStackToQueue(toadd);
                        System.out.println("Removing id: " + toadd.id + ", dmg: " + toadd.damage + ", cnt: " + toadd.count);
                    }
                } else {
                    // itemstack swap request
                    if (unfinalized.hasInQueue(client)) {
                        unfinalized.addStackToQueue(serverside);
                    } else {
                        System.out.println("What!!!");
                        System.out.println("Index: " + i);
                        System.out.println("cl.id = " + client.id + ", srv.id: " + serverside.id);
                        System.out.println("cl.dmg = " + client.damage + ", srv.dmg: " + serverside.damage);
                        System.out.println("cl.cnt = " + client.count + ", srv.cnt: " + serverside.count);
                        // won't get accepted by the server
                        return;
                    }
                }
            }
        }

        // finalize changes
        this.queue = unfinalized;

        if (packet.a == -1) {
            this.player.inventory.items = packet.items;
        } else if (packet.a == -3) {
            this.player.inventory.armor = packet.items;
        }
    }
}
