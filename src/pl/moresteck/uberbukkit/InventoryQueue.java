package pl.moresteck.uberbukkit;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class InventoryQueue {
    public ArrayList<InventoryItem> queue = new ArrayList<InventoryItem>();

    public InventoryQueue() {
        
    }

    public InventoryQueue(ArrayList<InventoryItem> queue) {
        this.queue = queue;
    }

    @Override
    public InventoryQueue clone() {
        return new InventoryQueue(this.queue);
    }

    public ArrayList<ItemStack> getQueue() {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        // need to add up all occurrences of an item into itemstack(s)
        // remember to account for max stack size for the item type
        for (InventoryItem item : queue) {
            int max = Item.byId[item.getId()].getMaxStackSize();

            // search for already processed stacks of the same item type that can hold at least 1 more count
            // if none are found, or if all compatible stacks are full, a new one gets added to the list
            ItemStack toadd = new ItemStack(item.getId(), 0, item.getDamage());
            for (int i = 0; i < stacks.size(); i++) {
                ItemStack it = stacks.get(i);

                if (it.id == item.getId() && it.damage == item.getDamage() && it.count < max) {
                    stacks.remove(it); // remove the stack, its size will increment and get added back
                    toadd = it;
                    break;
                }
            }

            toadd.count++; // this makes count always 1 for new stacks
            stacks.add(toadd);
        }
        return stacks;
    }

    public ArrayList<ItemStack> dropAllQueue() {
        ArrayList<ItemStack> stacks = getQueue();
        queue.clear();
        return stacks;
    }

    public void removeStackFromQueue(ItemStack item) {
        if (item == null || item.count <= 0) {
            return;
        }

        ArrayList<InventoryItem> toremove = new ArrayList<>();
        for (InventoryItem it : this.queue) {
            if (it.getId() == item.id && it.getDamage() == item.damage) {
                toremove.add(it);
            }
        }
        this.queue.removeAll(toremove);
    }

    public void addStackToQueue(ItemStack item) {
        if (item == null || item.count <= 0) {
            return;
        }

        for (int i = 0; i < item.count; i++) {
            InventoryItem toadd = new InventoryItem(item.id, item.damage);
            queue.add(toadd);
        }
    }

    public void addToQueue(InventoryItem item) {
        if (item == null) {
            return;
        }

        queue.add(item);
    }

    public boolean hasInQueue(ItemStack item) {
        if (item == null || item.count <= 0) {
            return true;
        }

        ArrayList<InventoryItem> matching = new ArrayList<>();
        for (InventoryItem que: queue) {
            if (que != null) {
                if (que.getId() == item.id && (que.getDamage() == item.damage || item.damage == -1)) {
                    matching.add(que);
                }
            }
        }

        if (matching.size() >= item.count) {
            // only remove the requested amount
            for (int i = 0; i < item.count; i++) {
                queue.remove(matching.get(i));
            }
            return true;
        }
        return false;
    }

    public void merge(InventoryQueue anotherQueue) {
        this.queue.addAll(anotherQueue.queue);
    }

    public void exclude(InventoryQueue anotherQueue) {
        this.queue.removeAll(anotherQueue.queue);
    }
}
