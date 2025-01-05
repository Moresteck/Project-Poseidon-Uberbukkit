package net.minecraft.server;

import uk.betacraft.uberbukkit.mods.ExtendedFont;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet {

    public String message;

    public Packet3Chat() {
    }

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
        if (this.pvn >= 11) {
            this.message = a(datainputstream, 119);
        } else {
            this.message = datainputstream.readUTF();
        }
    }

    public void a(DataOutputStream dataoutputstream) throws IOException { // CraftBukkit
        // uberbukkit
        String msg;
        if (!this.extendedFontSupport) {
            msg = ExtendedFont.translateToVanilla(this.message);
        } else {
            msg = this.message;
        }

        if (this.pvn >= 11) {
            a(msg, dataoutputstream);
        } else {
            dataoutputstream.writeUTF(msg);
        }
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return this.message.length();
    }
}
