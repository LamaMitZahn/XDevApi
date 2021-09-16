package de.ruben.xdevapi.util.type;

import org.jetbrains.annotations.NotNull;

public class ServerLocation extends GameLocation {
    public final String serverName;

    public ServerLocation(@NotNull String serverName, @NotNull String worldName, double x, double y, double z) {
        super(worldName, x, y, z);
        this.serverName = serverName;
    }

    public ServerLocation withServerName(@NotNull String serverName) {
        return new ServerLocation(serverName, worldName, x, y, z);
    }

    @Override
    public String toString() {
        return "ServerLocation{" +
                "worldName='" + worldName + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}