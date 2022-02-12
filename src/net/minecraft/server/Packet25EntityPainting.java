package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.moresteck.uberbukkit.Uberbukkit;

public class Packet25EntityPainting extends Packet {

    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;

    public Packet25EntityPainting() {}

    public Packet25EntityPainting(EntityPainting entitypainting) {
        this.a = entitypainting.id;
        this.b = entitypainting.b;
        this.c = entitypainting.c;
        this.d = entitypainting.d;
        this.e = entitypainting.a;
        this.f = entitypainting.e.A;
    }

    public void a(DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            this.f = a(datainputstream, EnumArt.z);
        } else {
            this.f = datainputstream.readUTF();
        }

        this.b = datainputstream.readInt();
        this.c = datainputstream.readInt();
        this.d = datainputstream.readInt();
        this.e = datainputstream.readInt();
    }

    public void a(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        // uberbukkit
        if (Uberbukkit.getPVN() >= 11) {
            a(this.f, dataoutputstream);
        } else {
            dataoutputstream.writeUTF(this.f);
        }

        dataoutputstream.writeInt(this.b);
        dataoutputstream.writeInt(this.c);
        dataoutputstream.writeInt(this.d);
        dataoutputstream.writeInt(this.e);
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public int a() {
        return 24;
    }
}
