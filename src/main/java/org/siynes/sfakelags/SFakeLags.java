package org.siynes.sfakelags;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.siynes.sfakelags.command.CheaterEnableLagsCommand;

public final class SFakeLags extends JavaPlugin {

    private TrollingPacketListener packetListener;
    private TrollingManager trollingManager = new TrollingManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(trollingManager, this);
        TrollingPacketListener packetListener = new TrollingPacketListener(trollingManager);
        PacketEvents.getAPI().getEventManager().registerListener(packetListener);

        getCommand("cheatlags").setExecutor(new CheaterEnableLagsCommand(trollingManager));
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().getEventManager().unregisterListener(packetListener);
    }
}
