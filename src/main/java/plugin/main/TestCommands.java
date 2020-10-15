package plugin.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import plugin.tools.data.SectionData;
import plugin.tools.SectionSetter;
import plugin.tools.data.role.Crewmate;
import plugin.tools.data.role.Imposter;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("t")) {
            Crewmate c = new Crewmate((Player) sender);
            c.setInventory();
        }else if(cmd.getName().equalsIgnoreCase("test")) {
            Imposter i = new Imposter((Player) sender);
            i.setInventory();
        }else if(cmd.getName().equalsIgnoreCase("test2")) {
            Imposter i = new Imposter((Player) sender);
            i.killTest();
        }
        return false;
    }
}
