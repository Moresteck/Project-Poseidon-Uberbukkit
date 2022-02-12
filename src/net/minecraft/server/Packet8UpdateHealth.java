package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet8UpdateHealth extends Packet {

    public int a;

    public Packet8UpdateHealth() {}

    public Packet8UpdateHealth(int i) {
        this.a = i;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        if (Uberbukkit.getPVN() >= 7) {
            this.a = datainputstream.readShort();
        } else {
            this.a = datainputstream.readByte();
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        if (Uberbukkit.getPVN() >= 7) {
            dataoutputstream.writeShort(this.a);
        } else {
            dataoutputstream.writeByte(this.a);
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return Uberbukkit.getPVN() >= 7 ? 2 : 1;
    }
}
