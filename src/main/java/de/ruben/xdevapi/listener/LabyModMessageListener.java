package de.ruben.xdevapi.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.labymod.LabyModProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class LabyModMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals("labymod3:main")) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        ByteBuf buf = Unpooled.wrappedBuffer(message);
        String key = LabyModProtocol.readString(buf, Short.MAX_VALUE);
        String json = LabyModProtocol.readString(buf, Short.MAX_VALUE);

        if(key.equals("INFO")) {

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode node = objectMapper.readTree(json);
                    String version = node.get("version").asText();
                    UUID uuid = player.getUniqueId();

                    XDevApi.getInstance().getLabyUsers().addUser(uuid, version);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }



        }else if(key.equalsIgnoreCase("input_prompt")){
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode node = objectMapper.readTree(json);

                    int id = node.get("id").asInt();

                    XDevApi.getInstance().getLabyModGUIResultHandler().getConsumer(id).accept(XDevApi.getInstance().getLabyModGUIResultHandler().getUUID(id), json);
                    XDevApi.getInstance().getLabyModGUIResultHandler().removePendingResult(id, XDevApi.getInstance().getLabyModGUIResultHandler().getUUID(id));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
        }
    }
}
