package com.legacyminecraft.poseidon;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

public class PoseidonConfig extends Configuration {
    private static PoseidonConfig singleton;
    private final int configVersion = 3;
    private Integer[] treeBlacklistIDs;

    public Integer[] getTreeBlacklistIDs() {
        return treeBlacklistIDs;
    }

    private PoseidonConfig() {
        super(new File("poseidon.yml"));
        this.reload();
    }

    public void reload() {
        this.load();
        this.write();
        this.save();
    }

    public void resetConfig() {
        // Delete all the config options
        for(String key : this.getKeys()) {
            this.removeProperty(key);
        }
        // Reload the config
        this.write();
    }

    private void write() {
        if (this.getString("config-version") == null || Integer.valueOf(this.getString("config-version")) < configVersion) {
            System.out.println("Converting to Config Version: " + configVersion);
            convertToNewConfig();
        }
        //Main
        generateConfigOption("config-version", 3);
        //Setting
        generateConfigOption("settings.allow-graceful-uuids", true);
        generateConfigOption("settings.uuids-source-port", -1);
        generateConfigOption("settings.delete-duplicate-uuids", false);
        generateConfigOption("settings.save-playerdata-by-uuid", true);
        generateConfigOption("settings.per-day-logfile", false);
        generateConfigOption("settings.fetch-uuids-from", "https://api.mojang.com/profiles/minecraft");
        generateConfigOption("settings.remove-join-leave-debug", true);
        generateConfigOption("settings.enable-tpc-nodelay", false);
        generateConfigOption("settings.use-get-for-uuids.enabled", false);
        generateConfigOption("settings.use-get-for-uuids.info", "This setting causes the server to use the GET method for Username to UUID conversion. This is useful incase the POST method goes offline.");

        generateConfigOption("settings.faster-packets.enabled", false);
        generateConfigOption("settings.faster-packets.info", "This setting increases the speed of packets, a fix from newer Minecraft versions.");

        generateConfigOption("settings.fix-drowning-push-down.enabled", true);
        generateConfigOption("settings.fix-drowning-push-down.info", "This setting fixes taking drowning damage pushing you down.");
        
        generateConfigOption("settings.player-knockback-fix.enabled", true);
        generateConfigOption("settings.player-knockback-fix.info", "This setting fixes reduced knockback for certain players on the server.");

        //Watchdog
        //generateConfigOption("settings.enable-watchdog", true);
        generateConfigOption("settings.watchdog.info", "Watchdog is a automatic hang detection system which can print stacktraces and kill the server automatically after a predefined interval.");
        generateConfigOption("settings.watchdog.enable", true);
        generateConfigOption("settings.watchdog.timeout.value", 120);
        generateConfigOption("settings.watchdog.timeout.info", "The number of seconds to kill the server process after no ticks occurring.");
        generateConfigOption("settings.watchdog.debug-timeout.enabled", false);
        generateConfigOption("settings.watchdog.debug-timeout.value", 30);
        generateConfigOption("settings.watchdog.debug-timeout.info", "debug-timeout can be used to print a stack trace at a lower interval then the main timeout allowing admins to locate blocking tasks that cause hangs over a certain duration. Only enable this if you have experienced temporary hangs/server freezes.");

        //Packet Events
        generateConfigOption("settings.packet-events.enabled", false);
        generateConfigOption("settings.packet-events.info", "This setting causes the server to fire a Bukkit event for each packet received and sent to a player once they have finished the initial login process. This only needs to be enabled if you have a plugin that uses this specific feature.");
//        generateConfigOption("settings.bukkit-event.disabled-plugin-unregister.value", true);
//        generateConfigOption("settings.bukkit-event.disabled-plugin-unregister.info", "This setting will automatically unregister listeners from disabled plugins. This is useful if you have a plugin that can get disabled at runtime and you want to prevent errors to the disabled plugin.");
        generateConfigOption("settings.packet-spam-detection.enabled", true);
        generateConfigOption("settings.packet-spam-detection.info", "This setting causes the server to detect and kick malicious players who send too many packets in a short period of time. This is useful to prevent players from sending too many packets to the server to cause lag.");
        generateConfigOption("settings.packet-spam-detection.threshold", 10000);

        //Statistics
        generateConfigOption("settings.statistics.key", UUID.randomUUID().toString());
        generateConfigOption("settings.statistics.enabled", true);

        //World Settings
        generateConfigOption("world-settings.optimized-explosions", false);
        generateConfigOption("world-settings.send-explosion-velocity", true);
        generateConfigOption("world-settings.randomize-spawn", true);
        generateConfigOption("world-settings.teleport-to-highest-safe-block", true);
        generateConfigOption("world-settings.use-modern-fence-bounding-boxes", false);
        //TODO: Actually implement the tree growth functionality stuff
        generateConfigOption("world.settings.block-tree-growth.enabled", true);
        generateConfigOption("world.settings.block-tree-growth.list", "54,63,68");
        generateConfigOption("world.settings.block-tree-growth.info", "This setting allows for server owners to easily block trees growing from automatically destroying certain blocks. The list must be a string with numerical item ids separated by commas.");
        generateConfigOption("world.settings.block-pistons-pushing-furnaces.info", "This workaround prevents pistons from pushing furnaces which prevents a malicious server crash.");
        generateConfigOption("world.settings.block-pistons-pushing-furnaces.enabled", true);
        generateConfigOption("world.settings.skeleton-shooting-sound-fix.info", "This setting fixes the sound of skeletons and players shooting not playing on clients.");
        generateConfigOption("world.settings.skeleton-shooting-sound-fix.enabled", true);
        generateConfigOption("world.settings.speed-hack-check.enable", true);
        generateConfigOption("world.settings.speed-hack-check.teleport", true);
        generateConfigOption("world.settings.speed-hack-check.distance", 100.0D);
        generateConfigOption("world.settings.speed-hack-check.info", "This setting allows you to configure the automatic speedhack detection.");
        //generateConfigOption("world-settings.eject-from-vehicle-on-teleport.enabled", true);
        //generateConfigOption("world-settings.eject-from-vehicle-on-teleport.info", "Eject the player from a boat or minecart before teleporting them preventing cross world coordinate exploits.");

        //Release2Beta Settings
        generateConfigOption("settings.release2beta.enable-ip-pass-through", false);
        generateConfigOption("settings.release2beta.proxy-ip", "127.0.0.1");

        //BungeeCord
        generateConfigOption("settings.bungeecord.bungee-mode.enable", false);
        generateConfigOption("settings.bungeecord.bungee-mode.kick-message", "You must connect through BungeeCord to join this server!");
        generateConfigOption("settings.bungeecord.bungee-mode.info", "Only allows connections via BungeeCord to join. Includes optional custom kick message for players not using BungeeCord.");

        //Modded Jar Support
        generateConfigOption("settings.support.modloader.enable", false);
        generateConfigOption("settings.support.modloader.info", "EXPERIMENTAL support for ModloaderMP.");

        //Offline Username Check
        generateConfigOption("settings.check-username-validity.enabled", true);
        generateConfigOption("settings.check-username-validity.info", "If enabled, verifies the validity of a usernames of cracked players.");
        generateConfigOption("settings.check-username-validity.regex", "[a-zA-Z0-9_?]*");
        generateConfigOption("settings.check-username-validity.max-length", 16);
        generateConfigOption("settings.check-username-validity.min-length", 3);

        generateConfigOption("settings.exempt-staff-from-flight-kick", false);

        // Uberbukkit settings
        generateConfigOption("version.worldgen.cocoabeans_loot", true);
        generateConfigOption("version.worldgen.pre_b1_2_ore_generation", false);
        generateConfigOption("version.worldgen.pre_b1_2_tree_generation", false);
        generateConfigOption("version.worldgen.generate_sandstone", true);
        generateConfigOption("version.worldgen.biomes.generate_spruces", true);
        generateConfigOption("version.worldgen.biomes.generate_birches", true);
        generateConfigOption("version.worldgen.generate_steveco_chests", false);
        generateConfigOption("version.worldgen.generate_lapis_ores", true);
        generateConfigOption("version.worldgen.generate_tallgrass", true);
        generateConfigOption("version.worldgen.ores.world.custom_seed", false);
        generateConfigOption("version.worldgen.ores.world.seed", 0L);
        
        generateConfigOption("version.mechanics.tile_grass_drop_seeds", false);
        generateConfigOption("version.mechanics.flammable_fences_stairs", true);
        generateConfigOption("version.mechanics.glowstone_pre1_6_6", false);
        generateConfigOption("version.mechanics.wool_recipe_pre1_6_6", false);
        generateConfigOption("version.mechanics.allow_grow_tallgrass", true);
        generateConfigOption("version.mechanics.allow_1_7_fence_placement", true);
        generateConfigOption("version.mechanics.tnt_require_lighter", true);
        generateConfigOption("version.mechanics.sheep_drop_wool_on_punch", false);
        generateConfigOption("version.mechanics.mushroom_spread", true);
        generateConfigOption("version.mechanics.ice_generate_only_when_snowing", false);
        generateConfigOption("version.mechanics.pre_1_6_fire", false);
        generateConfigOption("version.mechanics.nether_bed_explode", true);
        generateConfigOption("version.mechanics.arrows_pickup_by_others", true);
        generateConfigOption("version.mechanics.allow_minecart_boosters", false);
        generateConfigOption("version.mechanics.spawn_squids", true);
        generateConfigOption("version.mechanics.spawn_wolves", true);
        generateConfigOption("version.mechanics.spawn_slimes", true);
        generateConfigOption("version.mechanics.do_weather", true);
        generateConfigOption("version.mechanics.allow_ladder_gap", false);
        generateConfigOption("version.mechanics.old_slab_recipe", false);
        generateConfigOption("version.mechanics.burning_pig_drop_cooked_meat", true);
        generateConfigOption("version.mechanics.spawn_sheep_with_shades_of_black", true);
        generateConfigOption("version.mechanics.spawn_brown_and_pink_sheep", true);
        generateConfigOption("version.mechanics.drop_saplings_of_leaf_type", true);
        generateConfigOption("version.mechanics.spiders_trample_crops", false);
        generateConfigOption("version.mechanics.spiders_climb_walls", true);
        generateConfigOption("version.mechanics.allow_blocks_at_y_127", false);
        generateConfigOption("version.mechanics.drop_lapis_as_b1_2", false);
        generateConfigOption("version.mechanics.allow_milking_squids", false);
        generateConfigOption("version.mechanics.pre_b1_6_block_opacity", false);
        generateConfigOption("version.mechanics.pre_b1_5_pumpkins", false);
        generateConfigOption("version.mechanics.allow_bone_meal_on_grass", true);
        generateConfigOption("version.mechanics.beds_pre_b1_6_5", false);
        generateConfigOption("version.mechanics.pre_b1_5_block_placement_rules", false);
        generateConfigOption("version.mechanics.trample_farmland_above_fence", false);
        generateConfigOption("version.mechanics.seeds_replace_blocks", false);
        
        generateConfigOption("version.mechanics.boats.drop_boat_not_wood", false);
        generateConfigOption("version.mechanics.boats.break_boat_on_collision", true);
        
        generateConfigOption("version.experimental.force_fix_chunk_coords_corruption", false);
        generateConfigOption("version.allow_join.protocol", "14");
        generateConfigOption("version.allow_join.info", "Specify client versions to accept; 6 - a1.2.3_05 to a1.2.6; 7 - b1.0 to b1.1_02; 8 - b1.2 to b1.2_02; 9 - b1.3(_01); 10 - b1.4(_01); 11 - b1.5(_01); 12 - b1.6_test_build_3; 13 - b1.6 to b1.6.6, 14 - b1.7 to b1.7.3");

        generateConfigOption("fix.illegal-container-interaction.info", "Prevents interactions in a container if the player is farther away than the max distance.");
        generateConfigOption("fix.illegal-container-interaction.max-distance", 4);
        generateConfigOption("fix.illegal-container-interaction.log-violation", false);

        generateConfigOption("emergency.debug.regenerate-corrupt-chunks.enable", false);
        generateConfigOption("emergency.debug.regenerate-corrupt-chunks.info", "This setting allows you to automatically regenerate corrupt chunks. This is useful after a ungraceful shutdown while a file is being written to or out of memory exception.");

        //Messages
        generateConfigOption("message.kick.banned", "You are banned from this server!");
        generateConfigOption("message.kick.ip-banned", "Your IP address is banned from this server!");
        generateConfigOption("message.kick.not-whitelisted", "You are not white-listed on this server!");
        generateConfigOption("message.kick.full", "The server is full!");
        generateConfigOption("message.kick.shutdown", "\u00A7cServer is shutting down, please rejoin later.");
        generateConfigOption("message.kick.already-online", "\u00A7cA player with your username or uuid is already online, try reconnecting in a minute.");
        generateConfigOption("message.player.join", "\u00A7e%player% joined the game.");
        generateConfigOption("message.player.leave", "\u00A7e%player% left the game.");

        //Tree Leave Destroy Blacklist
        if (Boolean.valueOf(String.valueOf(getConfigOption("world.settings.block-tree-growth.enabled", true)))) {
            if (String.valueOf(this.getConfigOption("world.settings.block-tree-growth.list", "")).trim().isEmpty()) {
                //Empty Blacklist
            } else {
                String[] rawBlacklist = String.valueOf(this.getConfigOption("world.settings.block-tree-growth.list", "")).trim().split(",");
                int blackListCount = 0;
                for (String stringID : rawBlacklist) {
                    if (Pattern.compile("-?[0-9]+").matcher(stringID).matches()) {
                        blackListCount = blackListCount + 1;
                    } else {
                        System.out.println("The ID " + stringID + " for leaf decay blocker has been detected as invalid, and won't be used.");
                    }
                }
                //Loop a second time to get correct array length. I know this is horrible code, but it works and only runs on server startup.
                treeBlacklistIDs = new Integer[blackListCount];
                int i = 0;
                for (String stringID : rawBlacklist) {
                    if (Pattern.compile("-?[0-9]+").matcher(stringID).matches()) {
                        treeBlacklistIDs[i] = Integer.valueOf(stringID);
                        i = i + 1;
                    }
                }
                System.out.println("Leaf blocks can't replace the following block IDs: " + Arrays.toString(treeBlacklistIDs));
            }
        } else {
            treeBlacklistIDs = new Integer[0];
        }
    }


    private void generateConfigOption(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    //Getters Start
    public Object getConfigOption(String key) {
        return this.getProperty(key);
    }

    public Object getConfigOption(String key, Object defaultValue) {
        Object value = getConfigOption(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;

    }

    public String getConfigString(String key) {
        return String.valueOf(getConfigOption(key));
    }

    public Integer getConfigInteger(String key) {
        return Integer.valueOf(getConfigString(key));
    }

    public Long getConfigLong(String key) {
        return Long.valueOf(getConfigString(key));
    }

    public Double getConfigDouble(String key) {
        return Double.valueOf(getConfigString(key));
    }

    public Boolean getConfigBoolean(String key) {
        return Boolean.valueOf(getConfigString(key));
    }

    //Getters End

    private void convertToNewConfig() {
        //Graceful UUIDS
        convertToNewAddress("settings.statistics.enabled", "settings.enable-statistics");
        convertToNewAddress("settings.allow-graceful-uuids", "allowGracefulUUID");
        convertToNewAddress("settings.save-playerdata-by-uuid", "savePlayerdataByUUID");

        convertToNewAddress("settings.enable-watchdog", "settings.watchdog.enable");
    }

    private boolean convertToNewAddress(String newKey, String oldKey) {
        if (this.getString(newKey) != null) {
            return false;
        }
        if (this.getString(oldKey) == null) {
            return false;
        }
        System.out.println("Converting Config: " + oldKey + " to " + newKey);
        Object value = this.getProperty(oldKey);
        this.setProperty(newKey, value);
        this.removeProperty(oldKey);
        return true;

    }


    public synchronized static PoseidonConfig getInstance() {
        if (PoseidonConfig.singleton == null) {
            PoseidonConfig.singleton = new PoseidonConfig();
        }
        return PoseidonConfig.singleton;
    }

}
