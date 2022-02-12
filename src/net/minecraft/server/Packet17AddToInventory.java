package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet17AddToInventory extends Packet {

    public int a;
    public int b;
    public int c;

    public Packet17AddToInventory() {}

    public Packet17AddToInventory(ItemStack itemstack) {
        this.a = itemstack.id;
        this.b = itemstack.count;
        this.c = itemstack.damage;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readShort();
        this.b = datainputstream.readByte();
        this.c = datainputstream.readShort();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeShort(this.a);
        dataoutputstream.writeByte(this.b);
        dataoutputstream.writeShort(this.c);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 5;
    }
}
