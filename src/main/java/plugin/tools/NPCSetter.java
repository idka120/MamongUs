package plugin.tools;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class NPCSetter implements Listener {

    public static void createNPC(Player p, String name, float yaw, float pitch) {
        Location loc = new Location(p.getWorld(), (int) p.getLocation().getX() + 0.5, (int) p.getLocation().getY(), (int) p.getLocation().getZ() + 0.5);
        MinecraftServer sever = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) p.getWorld()).getHandle();

        EntityPlayer npc = new EntityPlayer(sever, world, new GameProfile(p.getUniqueId(), name) , new PlayerInteractManager(world));
        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), yaw, pitch);

        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
    }

    Entity previousEntity;
    private HashMap<EntityPlayer, Location> npcs = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material m = e.getClickedBlock().getType();
        Location loc = e.getClickedBlock().getLocation();
        Action a = e.getAction();
        EquipmentSlot slot = e.getHand();
        ItemStack stack = p.getInventory().getItemInMainHand();
        if(a == Action.LEFT_CLICK_BLOCK && slot == EquipmentSlot.HAND ) {

        }
    }

    @EventHandler
    public void onClickEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        previousEntity = entity;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player sender = e.getPlayer();
        String message = e.getMessage();

        if(message.indexOf("@") == 0 && message.indexOf("npc") == 1) {
            message = message.replace("@npc", "").trim();
            String[] args = message.split(" ");
            if(args.length == 3) { //npc spawn <Game> <Type>
                if(args[0].equals("spawn")) {
                    if(args[1].equals("amongUs")) {
                        switch (args[2]) {
                            case "1" :

                                break;
                            case "2" :

                                break;
                            case "3" :

                                break;
                            case "ultimate" :

                                break;
                            case "special" :

                                break;
                        }
                    }
                }
            }else if(args.length == 1) {
                if(args[0].equals("remove")) {
                    previousEntity.remove();
                }
            }
        }
    }
}
