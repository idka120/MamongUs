package plugin.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.game.Direction;
import plugin.game.Game;
import plugin.tools.NPCSetter;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("test")) {
            NPCSetter.createNPC((Player) sender, ChatColor.translateAlternateColorCodes('&', args[0]), 0, 0);
        }
        return false;
    }
}
