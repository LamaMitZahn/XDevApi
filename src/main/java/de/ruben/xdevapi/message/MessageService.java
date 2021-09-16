package de.ruben.xdevapi.message;

import com.google.gson.Gson;
import de.ruben.xdevapi.XDevApi;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageService {

    private Map<String, Message> messages;
    private File file;
    private YamlConfiguration yamlConfiguration;

    private Gson gson = new Gson();

    public MessageService(){
        this.messages = new ConcurrentHashMap<>();
        this.file = new File(XDevApi.getInstance().getDataFolder(), "Messages.yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }


    public void addMessageWithoutPrefix(String identifier, String message){
        addMessage(identifier, message, false);
    }

    public void addMessageWithPrefix(String identifier, String message){
        addMessage(identifier, message, true);
    }

    public void addMessage(String identifier, String message, boolean withPrefix){
        if(yamlConfiguration.get(identifier) != null){
            Message messageObject = gson.fromJson(yamlConfiguration.getString(identifier), Message.class);

            if(!messageObject.getMessage().equals(message) || !messageObject.isWithPrefix()==withPrefix){
                yamlConfiguration.set(identifier, gson.toJson(new Message(message, withPrefix)));
                save();
                messages.remove(identifier);
            }
        }else {
            yamlConfiguration.set(identifier, gson.toJson(new Message(message, withPrefix)));
            save();
            loadMessagesWithDefaults();
        }

    }

    public String getPrefix(){
        return getMessage("prefix");
    }

    public String getNoPerm(){
        return getMessage("noperm");
    }

    public String getMessage(String identifier){
        Message message = messages.containsKey(identifier) ? messages.get(identifier) : gson.fromJson(yamlConfiguration.getString(identifier), Message.class);
        return message.isWithPrefix() ? getMessage("prefix") + ChatColor.translateAlternateColorCodes('&', message.getMessage()) : ChatColor.translateAlternateColorCodes('&', message.getMessage());
    }

    public String sendMessage(Player player, String identifier){
        String message = getMessage(identifier);
        player.sendMessage(message);
        return message;
    }

    public void loadMessagesWithDefaults(){

        for(String key : yamlConfiguration.getKeys(false)){
            Message message = gson.fromJson(yamlConfiguration.getString(key), Message.class);
            if(!messages.containsKey(key)){
                messages.put(key, message);
            }
        }

        if(!messages.containsKey("prefix")){
            messages.put("prefix", new Message("&9&lAddictZone &8➜ &7", false));
        }

        if(!messages.containsKey("noperm")){
            messages.put("noperm", new Message("&c&lDazu hast du keine Rechte!", true));
        }

    }


    public void save(){

        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            if(!file.exists()){
                file.createNewFile();
            }

            this.yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
