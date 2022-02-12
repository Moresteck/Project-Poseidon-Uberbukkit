package pl.moresteck.uberbukkit;

import org.bukkit.Bukkit;

import com.legacyminecraft.poseidon.PoseidonConfig;

import net.minecraft.server.MinecraftServer;
import pl.moresteck.uberbukkit.protocol.Protocol;

public class Uberbukkit {
    private static Integer pvn = null;

    public static int getPVN() {
        if (pvn != null) return pvn;

        String pvnstr = PoseidonConfig.getInstance().getString("version.allow_join.protocol", "14");
        int pvn = 14;

        try {
            pvn = Integer.parseInt(pvnstr);
        } catch (Throwable t) {
            MinecraftServer.log.warning("[Uberbukkit] PVN is not a number! Can't proceed!");
            Bukkit.getServer().shutdown();
        }

        Uberbukkit.pvn = pvn;
        return pvn;
    }

    public static Protocol getProtocolHandler() {
        return Protocol.getProtocolClass(getPVN());
    }
}
