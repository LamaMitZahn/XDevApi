package de.ruben.xdevapi.labymod.display;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.ruben.xdevapi.labymod.LabyModProtocol;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SubtitleDisplay {

    public void setSubtitle(Player receiver, UUID subtitlePlayer, String value ) {
        JsonArray array = new JsonArray();

        JsonObject subtitle = new JsonObject();
        subtitle.addProperty( "uuid", subtitlePlayer.toString() );

        subtitle.addProperty( "size", 0.8d );

        if(value != null)
            subtitle.addProperty( "value", value );

        array.add(subtitle);

        LabyModProtocol.sendLabyModMessage( receiver, "account_subtitle", array );
    }

}
