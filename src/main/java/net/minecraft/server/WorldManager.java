package net.minecraft.server;

import uk.betacraft.uberbukkit.packet.Packet62Sound;

public class WorldManager implements IWorldAccess {

    private MinecraftServer server;
    public WorldServer world; // CraftBukkit - private -> public

    public WorldManager(MinecraftServer minecraftserver, WorldServer worldserver) {
        this.server = minecraftserver;
        this.world = worldserver;
    }

    public void a(String s, double d0, double d1, double d2, double d3, double d4, double d5) {
    }

    public void a(Entity entity) {
        this.server.getTracker(this.world.dimension).track(entity); // CraftBukkit
    }

    public void b(Entity entity) {
        this.server.getTracker(this.world.dimension).untrackEntity(entity); // CraftBukkit
    }

    public void a(String s, double d0, double d1, double d2, float f, float f1) {
        // uberbukkit start - use method to send sound
        float var10 = 16.0F;
        if (f > 1.0F) {
            var10 *= f;
        }

        this.server.serverConfigurationManager.sendPacketNearby(d0, d1, d2, var10, this.world.dimension, new Packet62Sound(s, d0, d1, d2, f, f1));
        // uberbukkit end
    }

    public void a(int i, int j, int k, int l, int i1, int j1) {
    }

    public void a() {
    }

    public void a(int i, int j, int k) {
        this.server.serverConfigurationManager.flagDirty(i, j, k, this.world.dimension); // CraftBukkit
    }

    public void a(String s, int i, int j, int k) {
    }

    public void a(int i, int j, int k, TileEntity tileentity) {
        this.server.serverConfigurationManager.a(i, j, k, tileentity);
    }

    public void a(EntityHuman entityhuman, int i, int j, int k, int l, int i1) {
        this.server.serverConfigurationManager.sendPacketNearby(entityhuman, (double) j, (double) k, (double) l, 64.0D, this.world.dimension, new Packet61(i, j, k, l, i1)); // CraftBukkit
    }
}
