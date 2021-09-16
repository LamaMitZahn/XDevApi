package de.ruben.xdevapi.labymod;

import java.util.HashMap;
import java.util.UUID;

public class LabyUsers {

    private final HashMap<UUID, String> users;

    public LabyUsers() {
        this.users = new HashMap<>();
    }

    public void addUser(UUID uuid, String version){
        users.put(uuid, version);
    }

    public String getVersion(UUID uuid){
        if(isLabyUser(uuid)) {
            return users.get(uuid);
        }else{
            return null;
        }
    }

    public Long getVersionAsLong(UUID uuid){
        if(isLabyUser(uuid)){
            return Long.parseLong(users.get(uuid).replace(".",""));
        }else {
            return -1L;
        }
    }

    public boolean isLabyUser(UUID uuid){
        return users.containsKey(uuid);
    }

    public void removeLabyUser(UUID uuid){
        if(users.containsKey(uuid)) users.remove(uuid);
    }
}
