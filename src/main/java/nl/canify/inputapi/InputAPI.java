package nl.canify.inputapi;

import org.bukkit.plugin.java.JavaPlugin;

public class InputAPI {

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * @param plugin
     * Your main class that extends JavaPlugin
     */
    public static void setPlugin(JavaPlugin plugin) {
        InputAPI.plugin = plugin;
    }

}
