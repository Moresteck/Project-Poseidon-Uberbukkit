package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet15Place extends Packet {

    public int a;
    public int b;
    public int c;
    public int face;
    public ItemStack itemstack;
    public int data;

    public Packet15Place() {}

    public void a(DataInputStream datainputstream) throws IOException {
        if (Uberbukkit.getPVN() <= 6) {
            this.data = datainputstream.readShort();
            this.a = datainputstream.readInt();
            this.b = datainputstream.read();
            this.c = datainputstream.readInt();
            this.face = datainputstream.read();

        } else if (Uberbukkit.getPVN() >= 7) {
            this.a = datainputstream.readInt();
            this.b = datainputstream.read();
            this.c = datainputstream.readInt();
            this.face = datainputstream.read();

            short short1 = datainputstream.readShort();

            if (short1 >= 0) {
                byte b0 = datainputstream.readByte();
                short short2 = 0;
                // uberbukkit
                if (Uberbukkit.getPVN() >= 8) {
                    short2 = datainputstream.readShort();
                } else {
                    short2 = datainputstream.readByte();
                }

                this.itemstack = new ItemStack(short1, b0, short2);
            } else {
                this.itemstack = null;
            }
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        if (Uberbukkit.getPVN() <= 6) {
            dataoutputstream.writeShort(this.data);
            dataoutputstream.writeInt(this.a);
            dataoutputstream.write(this.b);
            dataoutputstream.writeInt(this.c);
            dataoutputstream.write(this.face);

        } else if (Uberbukkit.getPVN() >= 7) {
            dataoutputstream.writeInt(this.a);
            dataoutputstream.write(this.b);
            dataoutputstream.writeInt(this.c);
            dataoutputstream.write(this.face);

            if (this.itemstack == null) {
                dataoutputstream.writeShort(-1);
            } else {
                dataoutputstream.writeShort(this.itemstack.id);
                dataoutputstream.writeByte(this.itemstack.count);
                // uberbukkit
                if (Uberbukkit.getPVN() >= 8) {
                    dataoutputstream.writeShort(this.itemstack.getData());
                } else {
                    dataoutputstream.writeByte(this.itemstack.getData());
                }
            }
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        // uberbukkit
        return Uberbukkit.getPVN() >= 8 ? 15 : 14;
    }
}
