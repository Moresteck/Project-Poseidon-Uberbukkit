package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet130UpdateSign extends Packet {

    public int x;
    public int y;
    public int z;
    public String[] lines;

    public Packet130UpdateSign() {
        this.k = true;
    }

    public Packet130UpdateSign(int i, int j, int k, String[] astring) {
        this.k = true;
        this.x = i;
        this.y = j;
        this.z = k;
        this.lines = astring;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.x = datainputstream.readInt();
        this.y = datainputstream.readShort();
        this.z = datainputstream.readInt();
        this.lines = new String[4];

        for (int i = 0; i < 4; ++i) {
            // uberbukkit
            if (Uberbukkit.getPVN() >= 11) {
                this.lines[i] = a(datainputstream, 15);
            } else {
                this.lines[i] = datainputstream.readUTF();
            }
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.x);
        dataoutputstream.writeShort(this.y);
        dataoutputstream.writeInt(this.z);

        for (int i = 0; i < 4; ++i) {
            // uberbukkit
            if (Uberbukkit.getPVN() >= 11) {
                a(this.lines[i], dataoutputstream);
            } else {
                dataoutputstream.writeUTF(this.lines[i]);
            }
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        int i = 0;

        for (int j = 0; j < 4; ++j) {
            i += this.lines[j].length();
        }

        return i;
    }
}
