package uk.betacraft.uberbukkit;

import com.legacyminecraft.poseidon.PoseidonConfig;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.List;

public class UberbukkitConfig extends Configuration {
    private static UberbukkitConfig singleton;
    private static final int CONFIG_VERSION = 1;

    public synchronized static UberbukkitConfig getInstance() {
        if (singleton == null) {
            singleton = new UberbukkitConfig();
        }
        return singleton;
    }

    public UberbukkitConfig() {
        super(new File("uberbukkit.yml"));

        this.init();
    }

    public void init() {
        this.load();
        this.writeDefaults();
        this.save();
    }

    private void migrateSubKeys(String oldKey, String newKey) {
        List<String> subKeys = PoseidonConfig.getInstance().getKeys(oldKey);
        if (subKeys == null) return;

        for (String key : subKeys) {
            this.migrateExact(oldKey + "." + key, newKey + "." + key, Object.class);
        }

        PoseidonConfig.getInstance().removeProperty(oldKey);
    }

    private void migrateExact(String oldKey, String newKey, Class<?> clazz) {
        Object val = PoseidonConfig.getInstance().getProperty(oldKey);

        if (clazz.isInstance(val)) {
            this.setProperty(newKey, val);
            PoseidonConfig.getInstance().removeProperty(oldKey);
        }
    }

    private void migrateExact(String key, Class<?> clazz) {
        this.migrateExact(key, key, clazz);
    }

    private void migrateFromPoseidonConfig() {
        migrateSubKeys("version.mechanics", "mechanics");
        migrateSubKeys("version.mechanics.boats", "mechanics.boats");
        migrateSubKeys("version.worldgen", "worldgen");
        migrateSubKeys("version.worldgen.biomes", "worldgen.biomes");
        migrateSubKeys("version.worldgen.ores.world", "worldgen.ores.world");
        migrateSubKeys("version.experimental", "experimental");
        migrateSubKeys("fix.illegal-container-interaction", "fix.illegal-container-interaction");

        migrateExact("settings.exempt-staff-from-flight-kick", Boolean.class);
        migrateExact("version.allow_join.protocol", "client.allowed_protocols.value", String.class);

        PoseidonConfig.getInstance().removeProperty("version");

        PoseidonConfig.getInstance().save();
    }

    private void writeDefaults() {
        if (this.getString("config-version") == null || Integer.valueOf(this.getString("config-version")) < CONFIG_VERSION) {
            System.out.println("[Uberbukkit] Converting from config version " + (this.getString("config-version") == null ? "0" : this.getString("config-version")) + " to " + CONFIG_VERSION);
            migrateFromPoseidonConfig();
            this.setProperty("config-version", CONFIG_VERSION);
        }

        writeDefault("worldgen.cocoabeans_loot", true);
        writeDefault("worldgen.pre_b1_2_ore_generation", false);
        writeDefault("worldgen.pre_b1_2_tree_generation", false);
        writeDefault("worldgen.generate_sandstone", true);
        writeDefault("worldgen.biomes.generate_spruces", true);
        writeDefault("worldgen.biomes.generate_birches", true);
        writeDefault("worldgen.generate_steveco_chests", false);
        writeDefault("worldgen.generate_lapis_ores", true);
        writeDefault("worldgen.generate_tallgrass", true);
        writeDefault("worldgen.ores.world.custom_seed", false);
        writeDefault("worldgen.ores.world.seed", 0L);

        writeDefault("mechanics.tile_grass_drop_seeds", false);
        writeDefault("mechanics.flammable_fences_stairs", true);
        writeDefault("mechanics.glowstone_pre1_6_6", false);
        writeDefault("mechanics.wool_recipe_pre1_6_6", false);
        writeDefault("mechanics.allow_grow_tallgrass", true);
        writeDefault("mechanics.allow_1_7_fence_placement", true);
        writeDefault("mechanics.tnt_require_lighter", true);
        writeDefault("mechanics.sheep_drop_wool_on_punch", false);
        writeDefault("mechanics.mushroom_spread", true);
        writeDefault("mechanics.ice_generate_only_when_snowing", false);
        writeDefault("mechanics.pre_1_6_fire", false);
        writeDefault("mechanics.nether_bed_explode", true);
        writeDefault("mechanics.arrows_pickup_by_others", true);
        writeDefault("mechanics.allow_minecart_boosters", false);
        writeDefault("mechanics.spawn_squids", true);
        writeDefault("mechanics.spawn_wolves", true);
        writeDefault("mechanics.spawn_slimes", true);
        writeDefault("mechanics.do_weather", true);
        writeDefault("mechanics.allow_ladder_gap", false);
        writeDefault("mechanics.old_slab_recipe", false);
        writeDefault("mechanics.burning_pig_drop_cooked_meat", true);
        writeDefault("mechanics.spawn_sheep_with_shades_of_black", true);
        writeDefault("mechanics.spawn_brown_and_pink_sheep", true);
        writeDefault("mechanics.drop_saplings_of_leaf_type", true);
        writeDefault("mechanics.spiders_trample_crops", false);
        writeDefault("mechanics.spiders_climb_walls", true);
        writeDefault("mechanics.allow_blocks_at_y_127", false);
        writeDefault("mechanics.drop_lapis_as_b1_2", false);
        writeDefault("mechanics.allow_milking_squids", false);
        writeDefault("mechanics.pre_b1_6_block_opacity", false);
        writeDefault("mechanics.pre_b1_5_pumpkins", false);
        writeDefault("mechanics.allow_bone_meal_on_grass", true);
        writeDefault("mechanics.beds_pre_b1_6_5", false);
        writeDefault("mechanics.beds_set_spawnpoint", true);
        writeDefault("mechanics.pre_b1_5_block_placement_rules", false);
        writeDefault("mechanics.trample_farmland_above_fence", false);
        writeDefault("mechanics.seeds_replace_blocks", false);
        writeDefault("mechanics.boats.drop_boat_not_wood", false);
        writeDefault("mechanics.boats.break_boat_on_collision", true);

        writeDefault("settings.exempt-staff-from-flight-kick", false);

        writeDefault("fix.illegal-container-interaction.info", "Prevents interactions in a container if the player is farther away than the max distance.");
        writeDefault("fix.illegal-container-interaction.max-distance", 4);
        writeDefault("fix.illegal-container-interaction.log-violation", false);

        writeDefault("experimental.force_fix_chunk_coords_corruption", false);

        writeDefault("client.mods.extended-font.enabled", true);
        writeDefault("client.mods.extended-font.info", "Enable extended-font support to let players chat with more characters (fallbacks to vanilla characters for those not using the mod)");

        writeDefault("client.allowed_protocols.value", "14");
        writeDefault("client.allowed_protocols.info1", "Specify client versions to accept (separated by commas - first PVN is treated as target PVN of the server)");
        writeDefault("client.allowed_protocols.info2", "6 - a1.2.3_05 to a1.2.6; 7 - b1.0 to b1.1_02; 8 - b1.2 to b1.2_02; 9 - b1.3(_01); 10 - b1.4(_01); 11 - b1.5(_01); 12 - b1.6_test_build_3; 13 - b1.6 to b1.6.6, 14 - b1.7 to b1.7.3");
    }

    private void writeDefault(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
    }
}
