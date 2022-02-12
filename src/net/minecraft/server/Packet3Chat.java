package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet3Chat extends Packet {

    public String message;

    public Packet3Chat() {}

    public Packet3Chat(String s) {
        /* CraftBukkit start - handle this later
        if (s.length() > 119) {
            s = s.substring(0, 119);
        }
        // CraftBukkit end */

        this.message = s;
    }

    public void a(DataInputStream datainputstream) throws IOException { // CraftBukkit
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            this.message = a(datainputstream, 119);
        } else {
            this.message = datainputstream.readUTF();
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException { // CraftBukkit
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            a(this.message, dataoutputstream);
        } else {
            dataoutputstream.writeUTF(this.message);
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return this.message.length();
    }
}
