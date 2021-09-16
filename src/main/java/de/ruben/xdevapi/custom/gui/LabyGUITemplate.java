package de.ruben.xdevapi.custom.gui;

import com.google.gson.JsonObject;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.labymod.LabyModProtocol;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;
import java.util.function.BiConsumer;

public class LabyGUITemplate {

    public static void createInput(Player player, String message, String placeholder, int maxLength, BiConsumer<UUID, String> consumer) {
        JsonObject object = new JsonObject();
        int promptSessionId = new Random().nextInt(10000);

        while (XDevApi.getInstance().getLabyModGUIResultHandler().isPendingResult(promptSessionId)){
            promptSessionId = new Random().nextInt(10000);
        }

        object.addProperty( "id", promptSessionId );
        object.addProperty( "message", message );
        object.addProperty( "value", "" );
        object.addProperty( "placeholder", placeholder );
        object.addProperty( "max_length", maxLength );

        // If you want to use the new text format in 1.16+
        // object.add("raw_json_text", textObject );

        LabyModProtocol.sendLabyModMessage( player, "input_prompt", object );

        XDevApi.getInstance().getLabyModGUIResultHandler().addPendingResult(promptSessionId, player.getUniqueId(), consumer);
    }

}
