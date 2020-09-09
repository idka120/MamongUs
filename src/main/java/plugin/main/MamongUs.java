package plugin.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.environment.PlayerData;
import plugin.environment.Vent;
import plugin.tools.LocationSetter;
import plugin.tools.Setter;

import java.util.HashMap;
import java.util.UUID;

public final class MamongUs extends JavaPlugin {

    public static HashMap<UUID, PlayerData> data = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Vent(), this);
        Bukkit.getPluginManager().registerEvents(new LocationSetter(), this);
        Bukkit.getPluginManager().registerEvents(new Setter(), this);
        this.getDescription().getCommands().keySet().forEach(l -> getCommand(l).setExecutor(new TestCommands()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
