package uk.betacraft.uberbukkit.protocol.extension;

import uk.betacraft.uberbukkit.protocol.Protocol;

public class Protocol2000 implements Protocol {

    public boolean canReceiveBlockItem(int id) {
        return true;
    }

    public boolean canReceivePacket(int id) {
        return true;
    }

    public boolean canSeeMob(Class<?> claz) {
        return true;
    }
}
