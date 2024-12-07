package uk.betacraft.uberbukkit;

import com.legacyminecraft.poseidon.PoseidonConfig;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import uk.betacraft.uberbukkit.protocol.Protocol;

import java.util.LinkedList;
import java.util.List;

public class Uberbukkit {
    // TODO: Change this automatically migration to Gradle
    private static final String version = "2.0.2";
    private static Integer pvn = null;
    private static List<Integer> pvns = null;

    public static int getTargetPVN() {
    // If cached, apply the special case only if Alpha Mode is enabled
    if (pvn != null) {
        return (pvn == 2000 && PoseidonConfig.getInstance().getBoolean("version.uberclient.alphamode", false)) ? 6 : pvn;
    }

    String pvnstr = PoseidonConfig.getInstance().getString("version.allow_join.protocol", "14");

    // Separate target version from other allowed PVNs
    int commaIndex = pvnstr.indexOf(",");
    if (commaIndex != -1) {
        pvnstr = pvnstr.substring(0, commaIndex);
    }

    pvn = 14; // Default PVN

    try {
        int parsedPVN = Integer.parseInt(pvnstr);
        // Custom logic: override 2000 with 6 only if Alpha Mode is enabled
        if (parsedPVN == 2000 && PoseidonConfig.getInstance().getBoolean("version.uberclient.alphamode", false)) {
            pvn = 6;
        } else {
            pvn = parsedPVN;
        }
    } catch (Throwable t) {
        MinecraftServer.log.warning("[Uberbukkit] Target PVN is not a number! Can't proceed!");
        Bukkit.getServer().shutdown();
    }

    return pvn;
}

    public static List<Integer> getAllowedPVNs() {
        if (pvns != null) return pvns;

        String[] pvnstrs = PoseidonConfig.getInstance().getString("version.allow_join.protocol", "14").split(",");

        pvns = new LinkedList<>();

        for (String pvnstr : pvnstrs) {
            try {
                pvns.add(Integer.parseInt(pvnstr));
            } catch (Throwable t) {
                MinecraftServer.log.severe("[Uberbukkit] PVN \"" + pvnstr + "\" is not a number! Can't proceed!");
                Bukkit.getServer().shutdown();
            }
        }

        return pvns;
    }

    public static Protocol getProtocolHandler() {
        return Protocol.getProtocolClass(getTargetPVN());
    }

    public static String getVersion() {
        return version;
    }
}
