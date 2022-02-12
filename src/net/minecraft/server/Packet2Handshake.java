package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet2Handshake extends Packet {

    public String a;

    public Packet2Handshake() {}

    public Packet2Handshake(String s) {
        this.a = s;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            this.a = a(datainputstream, 32);
        } else {
            this.a = datainputstream.readUTF();
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            a(this.a, dataoutputstream);
        } else {
            dataoutputstream.writeUTF(this.a);
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 4 + this.a.length() + 4;
    }
}
