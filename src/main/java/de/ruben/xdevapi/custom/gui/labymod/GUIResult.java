package de.ruben.xdevapi.custom.gui.labymod;

import java.util.UUID;

public class GUIResult {
    private String jsonResult;
    private UUID playerId;

    public GUIResult(String jsonResult, UUID playerId) {
        this.jsonResult = jsonResult;
        this.playerId = playerId;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
