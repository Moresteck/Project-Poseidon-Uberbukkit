package pl.moresteck.uberbukkit;

public class InventoryItem {
    private int id;
    private int damage;
    protected long hash; // just to have some differentiation between duplicate queue entries (it's not perfect)

    public InventoryItem(int id, int damage) {
        this.id = id;
        this.damage = damage;
        this.hash = System.nanoTime();
    }

    public int getId() {
        return this.id;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getCount() {
        return 1; // it's always 1, as we don't queue itemstacks, we queue individual items from the itemstack
    }

    public long getHash() {
        return this.hash;
    }
}
