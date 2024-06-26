package org.bukkit.command;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PluginCommandYamlParser {

    @SuppressWarnings("unchecked")
    public static List<Command> parse(Plugin plugin) {
        List<Command> pluginCmds = new ArrayList<Command>();
        Object object = plugin.getDescription().getCommands();

        if (object == null) {
            return pluginCmds;
        }

        Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) object;

        if (map != null) {
            for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
                Command newCmd = new PluginCommand(entry.getKey(), plugin);
                Object description = entry.getValue().get("description");
                Object usage = entry.getValue().get("usage");
                Object aliases = entry.getValue().get("aliases");
                Object permission = entry.getValue().get("permission");
                Object isHidden = entry.getValue().get("hidden");

                if (isHidden != null) {
                    newCmd.setHidden(String.valueOf(isHidden).equalsIgnoreCase("true"));
                }

                if (description != null) {
                    newCmd.setDescription(description.toString());
                }

                if (usage != null) {
                    newCmd.setUsage(usage.toString());
                }

                if (aliases != null) {
                    List<String> aliasList = new ArrayList<String>();

                    if (aliases instanceof List) {
                        for (Object o : (List<Object>) aliases) {
                            aliasList.add(o.toString());
                        }
                    } else {
                        aliasList.add(aliases.toString());
                    }

                    newCmd.setAliases(aliasList);
                }

                if (permission != null) {
                    newCmd.setPermission(permission.toString());
                }

                pluginCmds.add(newCmd);
            }
        }
        return pluginCmds;
    }
}
