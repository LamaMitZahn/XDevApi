package de.ruben.xdevapi.labymod.display;

import com.google.gson.JsonObject;
import de.ruben.xdevapi.labymod.LabyModProtocol;
import org.bukkit.entity.Player;

public class TablistDisplay {
    public void sendServerBanner(Player player, String imageUrl) {
        JsonObject object = new JsonObject();
        object.addProperty("url", imageUrl);
        LabyModProtocol.sendLabyModMessage(player, "server_banner", object);
    }
}
