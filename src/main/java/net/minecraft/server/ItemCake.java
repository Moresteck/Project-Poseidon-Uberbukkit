package net.minecraft.server;

public class ItemCake extends ItemBlock {

    public ItemCake(int i) {
        super(i);
        this.d(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }
}
