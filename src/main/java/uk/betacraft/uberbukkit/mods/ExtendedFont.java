package uk.betacraft.uberbukkit.mods;

import net.minecraft.server.Packet3Chat;

import java.util.HashMap;

public class ExtendedFont {
    private static final String[][] replaceMap = {
        {
            "A", "Ą", "Á"
        },
        {
            "E", "Ę", "Ě", "Ė", "Ê"
        },
        {
            "O", "Ǫ", "Ó", "Ô"
        },
        {
            "I", "Į", "Í", "Î"
        },
        {
            "U", "Ų", "Ů", "Ú"
        },
        {
            "", "̨", "̌", "́"
        },
        {
            "C", "Č", "Ć"
        },
        {
            "G", "Ǧ", "Ğ"
        },
        {
            "N", "Ň", "Ń"
        },
        {
            "R", "Ř", "Ŕ"
        },
        {
            "S", "Š", "Ś"
        },
        {
            "Z", "Ž", "Ż", "Ź"
        },
        {
            "T", "Ť"
        },
        {
            "D", "Ď", "Đ"
        },
        {
            "L", "Ł", "Ĺ", "Ľ"
        },
        {
            "Y", "Ý"
        },
        {
            "a", "ą"
        },
        {
            "e", "ę", "ě", "ė"
        },
        {
            "o", "ǫ"
        },
        {
            "i", "į"
        },
        {
            "u", "ų", "ů"
        },
        {
            "c", "č", "ć"
        },
        {
            "g", "ǧ", "ğ"
        },
        {
            "n", "ň", "ń"
        },
        {
            "r", "ř", "ŕ"
        },
        {
            "s", "š", "ś", "ſ"
        },
        {
            "z", "ž", "ż", "ź"
        },
        {
            "t", "ť"
        },
        {
            "d", "ď", "đ"
        },
        {
            "l", "ł", "ĺ", "ľ"
        },
        {
            "y", "ý"
        }
    };

    public static String translateToVanilla(String s) {
        for (String[] map : replaceMap) {
            for (int i = 1; i < map.length; i++) {
                s = s.replace(map[i], map[0]);
            }
        }

        return s;
    }
}
