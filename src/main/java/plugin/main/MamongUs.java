package plugin.main;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.environment.Vent;
import plugin.tools.LocationSetter;
import plugin.tools.Setter;
import plugin.tools.data.PlayerData;
import plugin.tools.data.role.Events;
import plugin.tools.data.role.Role;

import java.util.HashMap;
import java.util.UUID;

public final class MamongUs extends JavaPlugin {

    public static HashMap<UUID, PlayerData> data = new HashMap<>();

    public static WorldEditPlugin getWorldEdit() {
        return (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("MamongUs");
    }

    @Override
    public void onEnable() {
        //if(getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            //Bukkit.getLogger().info("§cThere isn't WorldEdit plugin");
            //return;
        //}
        Bukkit.getLogger().info("업데이트 테스트-10");
        Bukkit.getPluginManager().registerEvents(new Role.RoleEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Vent(), this);
        Bukkit.getPluginManager().registerEvents(new LocationSetter(), this);
        Bukkit.getPluginManager().registerEvents(new Setter(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        this.getDescription().getCommands().keySet().forEach(l -> getCommand(l).setExecutor(new TestCommands()));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
