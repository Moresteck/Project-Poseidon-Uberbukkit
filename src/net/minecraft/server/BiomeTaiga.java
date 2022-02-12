package net.minecraft.server;

import java.util.Random;

import com.legacyminecraft.poseidon.PoseidonConfig;

public class BiomeTaiga extends BiomeBase {

    public BiomeTaiga() {
        // uberbukkit
        if (PoseidonConfig.getInstance().getBoolean("version.mechanics.spawn_wolves", true))
            this.t.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public WorldGenerator a(Random random) {
        // uberbukkit
        if (!PoseidonConfig.getInstance().getBoolean("version.worldgen.biomes.generate_spruces", true))
            return super.a(random);

        return (WorldGenerator) (random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2());
    }
}
