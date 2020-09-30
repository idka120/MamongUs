package plugin.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import plugin.tools.SectionData;
import plugin.tools.SectionSetter;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("t")) {
            SectionSetter.data.put("test", new SectionData(((Player) sender).getLocation(), ((Player) sender).getLocation().add(3, 3, 3)));
            SectionSetter.data.get("test").load(((Player) sender).getLocation().add(10, 10, 10), true, "west", (LivingEntity) sender);
            SectionSetter.data.get("test").show((LivingEntity) sender);
            SectionSetter.data.forEach((string, sectionData) -> sender.sendMessage("섹션 " + string + "의 첫번째 위치 ("+ (int) sectionData.getPos1().getX() + ", " + (int) sectionData.getPos1().getY() + ", " + (int) sectionData.getPos1().getZ() + ") 와 두번째 위치 ("+ (int) sectionData.getPos2().getX() + ", " + (int) sectionData.getPos2().getY() + ", " + (int) sectionData.getPos2().getZ() + ")"));
        }
        return false;
    }
}
