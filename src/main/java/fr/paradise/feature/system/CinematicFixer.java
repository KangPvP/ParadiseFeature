package fr.paradise.feature.system;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import fr.paradise.feature.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

public class CinematicFixer {
    private final Main plugin;

    private final List<UUID> toCancel = new ArrayList<>();

    public CinematicFixer(Main instance) {
        this.plugin = instance;
        startPacketListening();
    }

    private void startPacketListening() {
        this.plugin.getProtocol().addPacketListener((PacketListener)new PacketAdapter(
                (Plugin)Main.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                System.out.println("Chat");
                UUID playerUUID = event.getPlayer().getUniqueId();
                if (CinematicFixer.this.toCancel.contains(playerUUID)) {
                    System.out.println("testChat");
                    event.setCancelled(true);
                }
            }
        });
    }

    public boolean toggleCanceling(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (this.toCancel.contains(playerUUID)) {
            this.toCancel.remove(playerUUID);
            return true;
        }
        this.toCancel.add(playerUUID);
        return false;
    }
}