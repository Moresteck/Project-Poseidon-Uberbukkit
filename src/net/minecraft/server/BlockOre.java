package net.minecraft.server;

import java.util.Random;

import com.legacyminecraft.poseidon.PoseidonConfig;

public class BlockOre extends Block {

    public BlockOre(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return this.id == Block.COAL_ORE.id ? Item.COAL.id : (this.id == Block.DIAMOND_ORE.id ? Item.DIAMOND.id : (this.id == Block.LAPIS_ORE.id ? Item.INK_SACK.id : this.id));
    }

    public int a(Random random) {
        if (this.id == Block.LAPIS_ORE.id && PoseidonConfig.getInstance().getBoolean("version.mechanics.drop_lapis_as_b1_2", false)) {
            return 4 + random.nextInt(5);
        } else {
            return 1;
        }
    }

    protected int a_(int i) {
        if (this.id == Block.LAPIS_ORE.id && PoseidonConfig.getInstance().getBoolean("version.mechanics.drop_lapis_as_b1_2", false)) {
            return 4;
        } else {
            return 0;
        }
    }
}
