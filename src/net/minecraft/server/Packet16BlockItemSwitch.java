package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet16BlockItemSwitch extends Packet {

    public int itemInHandIndex;
    public int itemId;
    public int itemDamage;

    public Packet16BlockItemSwitch() {}

    // pvn <= 6
    public Packet16BlockItemSwitch(int i, int j) {
        this.itemId = i;
        this.itemDamage = j;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        if (Uberbukkit.getPVN() >= 7) {
            this.itemInHandIndex = datainputstream.readShort();
        } else {
            this.itemDamage = datainputstream.readInt();
            this.itemId = datainputstream.readShort();
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        if (Uberbukkit.getPVN() >= 7) {
            dataoutputstream.writeShort(this.itemInHandIndex);
        } else {
            dataoutputstream.writeInt(this.itemDamage);
            dataoutputstream.writeShort(this.itemId);
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return Uberbukkit.getPVN() >= 7 ? 2 : 6;
    }
}
