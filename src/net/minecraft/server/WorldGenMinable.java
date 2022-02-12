package net.minecraft.server;

import java.util.Random;

import com.legacyminecraft.poseidon.PoseidonConfig;

import pl.moresteck.uberbukkit.Uberbukkit;

public class WorldGenMinable extends WorldGenerator {

    private int a;
    private int b;

    public WorldGenMinable(int i, int j) {
        this.a = i;
        this.b = j;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        if (PoseidonConfig.getInstance().getBoolean("version.worldgen.ores." + world.worldData.name + ".custom_seed", false)) {
            String text = PoseidonConfig.getInstance().getString("version.worldgen.ores." + world.worldData.name + ".seed");
            long seed = 0L;
            if (text != null) {
                seed = Long.parseLong(text);
            }
            random = new Random(seed);
        }

        float f = random.nextFloat() * 3.1415927F;
        double d0 = (double) ((float) (i + 8) + MathHelper.sin(f) * (float) this.b / 8.0F);
        double d1 = (double) ((float) (i + 8) - MathHelper.sin(f) * (float) this.b / 8.0F);
        double d2 = (double) ((float) (k + 8) + MathHelper.cos(f) * (float) this.b / 8.0F);
        double d3 = (double) ((float) (k + 8) - MathHelper.cos(f) * (float) this.b / 8.0F);
        double d4 = (double) (j + random.nextInt(3) + 2);
        double d5 = (double) (j + random.nextInt(3) + 2);

        for (int l = 0; l <= this.b; ++l) {
            double d6 = d0 + (d1 - d0) * (double) l / (double) this.b;
            double d7 = d4 + (d5 - d4) * (double) l / (double) this.b;
            double d8 = d2 + (d3 - d2) * (double) l / (double) this.b;
            double d9 = random.nextDouble() * (double) this.b / 16.0D;
            double d10 = (double) (MathHelper.sin((float) l * 3.1415927F / (float) this.b) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) l * 3.1415927F / (float) this.b) + 1.0F) * d9 + 1.0D;

            // uberbukkit
            if (PoseidonConfig.getInstance().getBoolean("version.worldgen.pre_b1_2_ore_generation", false)) {
                for (int i1 = (int) (d6 - d10 / 2.0D); i1 <= (int) (d6 + d10 / 2.0D); ++i1) {
                    for (int j1 = (int) (d7 - d11 / 2.0D); j1 <= (int) (d7 + d11 / 2.0D); ++j1) {
                        for (int k1 = (int) (d8 - d10 / 2.0D); k1 <= (int) (d8 + d10 / 2.0D); ++k1) {
                            double d12 = ((double) i1 + 0.5D - d6) / (d10 / 2.0D);
                            double d13 = ((double) j1 + 0.5D - d7) / (d11 / 2.0D);
                            double d14 = ((double) k1 + 0.5D - d8) / (d10 / 2.0D);

                            if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getTypeId(i1, j1, k1) == Block.STONE.id) {
                                world.setRawTypeId(i1, j1, k1, this.a);
                            }
                        }
                    }
                }
            } else {
                int i1 = MathHelper.floor(d6 - d10 / 2.0D);
                int j1 = MathHelper.floor(d7 - d11 / 2.0D);
                int k1 = MathHelper.floor(d8 - d10 / 2.0D);
                int l1 = MathHelper.floor(d6 + d10 / 2.0D);
                int i2 = MathHelper.floor(d7 + d11 / 2.0D);
                int j2 = MathHelper.floor(d8 + d10 / 2.0D);

                for (int k2 = i1; k2 <= l1; ++k2) {
                    double d12 = ((double) k2 + 0.5D - d6) / (d10 / 2.0D);

                    if (d12 * d12 < 1.0D) {
                        for (int l2 = j1; l2 <= i2; ++l2) {
                            double d13 = ((double) l2 + 0.5D - d7) / (d11 / 2.0D);

                            if (d12 * d12 + d13 * d13 < 1.0D) {
                                for (int i3 = k1; i3 <= j2; ++i3) {
                                    double d14 = ((double) i3 + 0.5D - d8) / (d10 / 2.0D);

                                    if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getTypeId(k2, l2, i3) == Block.STONE.id) {
                                        world.setRawTypeId(k2, l2, i3, this.a);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
