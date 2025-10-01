package uk.betacraft.uberbukkit;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import uk.betacraft.uberbukkit.protocol.Protocol;

import java.util.LinkedList;
import java.util.List;

public class Uberbukkit {
    private static final String version = BuildParameters.VERSION;
    private static Integer pvn = null;
    private static List<Integer> pvns = null;

    public static int getTargetPVN() {
        if (pvn != null) return pvn;

        String pvnstr = UberbukkitConfig.getInstance().getString("client.allowed_protocols.value", "14");

        // separate target version from other allowed PVNs
        int commaIndex = pvnstr.indexOf(",");
        if (commaIndex != -1) {
            pvnstr = pvnstr.substring(0, commaIndex);
        }

        pvn = 14;

        try {
            pvn = Integer.parseInt(pvnstr);
        } catch (Throwable t) {
            MinecraftServer.log.warning("[Uberbukkit] Target PVN is not a number! Can't proceed!");
            Bukkit.getServer().shutdown();
        }

        return pvn;
    }

    public static List<Integer> getAllowedPVNs() {
        if (pvns != null) return pvns;

        String[] pvnstrs = UberbukkitConfig.getInstance().getString("client.allowed_protocols.value", "14").split(",");

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
