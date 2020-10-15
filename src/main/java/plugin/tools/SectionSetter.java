package plugin.tools;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import plugin.tools.data.GameType;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

public class SectionSetter implements Listener {

    private HashMap<String, CuboidRegion> regions = new HashMap<>();
    private HashMap<GameType, String> sections = new HashMap<>();

    private File where = new File("plugins/MamongUs/regions");

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        try {
            String message = e.getMessage();
            Player sender = e.getPlayer();
            if (message.indexOf("@") == 0 && message.indexOf("section") == 1) {
                message = message.replace("@section", " ").trim();
                String[] args = message.split(" ");
                if (args.length == 2) {
                    if ("save".equals(args[0])) {
                        CuboidRegion region = (CuboidRegion) WorldEdit.getInstance().getSessionManager().findByName(e.getPlayer().getName()).getSelection(WorldEdit.getInstance().getSessionManager().findByName(e.getPlayer().getName()).getSelectionWorld());
                        String name = args[1];
                        regions.put(name, region);
                    }else if(args[0].equals("file")) {
                        String name = args[1];
                        if(regions.get(name) == null) sender.sendMessage("§c알 수 없는 이름의 지역입니다");
                        else {
                            try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(where))) {
                                writer.write(new BlockArrayClipboard(regions.get(name)));
                            }
                        }
                    }
                }else if(args.length == 3) {
                    switch (args[0]) {
                        case "loader":

                            break;
                    }
                }
            }
        }catch (Exception ex) {
            if(ex instanceof IncompleteRegionException) e.getPlayer().sendMessage("§c지역을 저장해 주세요");
        }
    }

    public static void load() {

    }
}
