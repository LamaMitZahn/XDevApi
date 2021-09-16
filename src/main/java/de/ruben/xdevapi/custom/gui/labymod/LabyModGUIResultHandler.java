package de.ruben.xdevapi.custom.gui.labymod;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import de.ruben.xdevapi.XDevApi;

import java.util.UUID;
import java.util.function.BiConsumer;

public class LabyModGUIResultHandler {

    private final Table<Integer, UUID, BiConsumer<UUID, String>> table;

    public LabyModGUIResultHandler(){
        this.table = HashBasedTable.create();
    }

    public void addPendingResult(int id, UUID playerId, BiConsumer<UUID, String> consumer){
        table.put(id, playerId, consumer);
    }

    public BiConsumer<UUID, String> getConsumer(int id){
        return table.rowMap().get(id).values().stream().findFirst().orElseThrow();
    }

    public UUID getUUID(int id){
        return table.rowMap().get(id).keySet().stream().findFirst().orElseThrow();
    }

    public boolean isPendingResult(int id){
        return table.containsRow(id);
    }

    public void removePendingResult(int id, UUID uuid){
        XDevApi.getInstance().consoleMessage("Before: "+table, true);
        table.remove(id, uuid);
        XDevApi.getInstance().consoleMessage("After: "+table, true);
    }

    public void removeAll(){
        table.clear();
    }


}
