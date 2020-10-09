package plugin.tools;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SectionSetter implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if(message.indexOf("@") == 0 && message.indexOf("section") == 1) {
            message = message.replace("@section", " ").trim();
            String[] args = message.split(" ");
            if(args.length == 1) {

            }
        }
    }
}
