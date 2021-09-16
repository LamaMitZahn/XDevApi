package de.ruben.xdevapi.listener;

import de.ruben.xdevapi.XDevApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        XDevApi.getInstance().getxScheduler().async(() -> {
            if(XDevApi.getInstance().getLabyUsers().isLabyUser(event.getPlayer().getUniqueId())){
                XDevApi.getInstance().getLabyUsers().removeLabyUser(event.getPlayer().getUniqueId());
            }
        });
    }
}
