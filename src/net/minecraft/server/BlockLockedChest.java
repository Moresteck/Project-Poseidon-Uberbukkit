package net.minecraft.server;

import java.util.Random;

import com.legacyminecraft.poseidon.PoseidonConfig;

public class BlockLockedChest extends Block {

    protected BlockLockedChest(int i) {
        super(i, Material.WOOD);
        this.textureId = 26;
    }

    public int a(int i) {
        return i == 1 ? this.textureId - 1 : (i == 0 ? this.textureId - 1 : (i == 3 ? this.textureId + 1 : this.textureId));
    }

    public boolean canPlace(World world, int i, int j, int k) {
        // uberbukkit
        if (!PoseidonConfig.getInstance().getBoolean("version.worldgen.generate_steveco_chests", true)) {
            return true;
        }

        int l = 0;

        if (world.getTypeId(i - 1, j, k) == this.id) {
            ++l;
        }

        if (world.getTypeId(i + 1, j, k) == this.id) {
            ++l;
        }

        if (world.getTypeId(i, j, k - 1) == this.id) {
            ++l;
        }

        if (world.getTypeId(i, j, k + 1) == this.id) {
            ++l;
        }

        return l > 1 ? false : (this.g(world, i - 1, j, k) ? false : (this.g(world, i + 1, j, k) ? false : (this.g(world, i, j, k - 1) ? false : !this.g(world, i, j, k + 1))));
    }

    private boolean g(World world, int i, int j, int k) {
        return world.getTypeId(i, j, k) != this.id ? false : (world.getTypeId(i - 1, j, k) == this.id ? true : (world.getTypeId(i + 1, j, k) == this.id ? true : (world.getTypeId(i, j, k - 1) == this.id ? true : world.getTypeId(i, j, k + 1) == this.id)));
    }

    public void a(World world, int i, int j, int k, Random random) {
        // uberbukkit
        if (!PoseidonConfig.getInstance().getBoolean("version.worldgen.generate_steveco_chests", true))
            world.setTypeId(i, j, k, 0);
    }
}
