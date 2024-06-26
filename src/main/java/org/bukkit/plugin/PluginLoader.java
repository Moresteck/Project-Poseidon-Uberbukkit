package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Represents a plugin loader, which handles direct access to specific types
 * of plugins
 */
public interface PluginLoader {

    /**
     * Loads the plugin contained in the specified file
     *
     * @param file File to attempt to load
     * @return Plugin that was contained in the specified file, or null if
     * unsuccessful
     * @throws InvalidPluginException Thrown when the specified file is not a plugin
     */
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

    // Project Poseidon Start

    /**
     * Creates listener map for class
     *
     * @param listener listener class
     * @param plugin   plugin
     * @return Map for all the events in the class
     */
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener listener, @NotNull final Plugin plugin);
    // Project Poseidon End

    /**
     * Loads the plugin contained in the specified file
     *
     * @param file                   File to attempt to load
     * @param ignoreSoftDependencies Loader will ignore soft dependencies if this flag is set to true
     * @return Plugin that was contained in the specified file, or null if
     * unsuccessful
     * @throws InvalidPluginException Thrown when the specified file is not a plugin
     */
    public Plugin loadPlugin(File file, boolean ignoreSoftDependencies) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

    /**
     * Returns a list of all filename filters expected by this PluginLoader
     */
    public Pattern[] getPluginFileFilters();

    /**
     * Creates and returns an event executor
     *
     * @param type     Type of the event executor to create
     * @param listener the object that will handle the eventual call back
     */
    public EventExecutor createExecutor(Event.Type type, Listener listener);

    /**
     * Enables the specified plugin
     * <p>
     * Attempting to enable a plugin that is already enabled will have no effect
     *
     * @param plugin Plugin to enable
     */
    public void enablePlugin(Plugin plugin);

    /**
     * Disables the specified plugin
     * <p>
     * Attempting to disable a plugin that is not enabled will have no effect
     *
     * @param plugin Plugin to disable
     */
    public void disablePlugin(Plugin plugin);
}
