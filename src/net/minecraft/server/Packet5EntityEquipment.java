package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet5EntityEquipment extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public ItemStack[] items;

    public Packet5EntityEquipment() {}

    // pvn >= 7
    public Packet5EntityEquipment(int i, int j, ItemStack itemstack) {
        this.a = i;
        this.b = j;
        if (itemstack == null) {
            this.c = -1;
            this.d = 0;
        } else {
            this.c = itemstack.id;
            this.d = itemstack.getData();
        }
    }

    // pvn <= 6
    public Packet5EntityEquipment(int i, ItemStack[] aitemstack) {
        this.a = i;
        this.items = new ItemStack[aitemstack.length];

        for (int j = 0; j < this.items.length; ++j) {
            this.items[j] = aitemstack[j] == null ? null : aitemstack[j].cloneItemStack();
        }
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        if (Uberbukkit.getPVN() >= 7) {
            this.b = datainputstream.readShort();
            this.c = datainputstream.readShort();
            // uberbukkit
            if (Uberbukkit.getPVN() >= 8) {
                this.d = datainputstream.readShort();
            } else {
                this.d = 0;
            }
        } else {
            short short1 = datainputstream.readShort();

            this.items = new ItemStack[short1];

            for (int i = 0; i < short1; ++i) {
                short short2 = datainputstream.readShort();

                if (short2 >= 0) {
                    byte b0 = datainputstream.readByte();
                    short short3 = datainputstream.readShort();

                    this.items[i] = new ItemStack(short2, b0, short3);
                }
            }
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        if (Uberbukkit.getPVN() >= 7) {
            dataoutputstream.writeShort(this.b);
            dataoutputstream.writeShort(this.c);
            // uberbukkit
            if (Uberbukkit.getPVN() >= 8) {
                dataoutputstream.writeShort(this.d);
            }
        } else {
            dataoutputstream.writeShort(this.items.length);

            for (int i = 0; i < this.items.length; ++i) {
                if (this.items[i] == null) {
                    dataoutputstream.writeShort(-1);
                } else {
                    dataoutputstream.writeShort((short) this.items[i].id);
                    dataoutputstream.writeByte((byte) this.items[i].count);
                    dataoutputstream.writeShort((short) this.items[i].damage);
                }
            }
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return Uberbukkit.getPVN() >= 7 ? 8 : (6 + this.items.length * 5);
    }
}
