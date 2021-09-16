package de.ruben.xdevapi.util.keys;

import de.ruben.xdevapi.util.type.WorldChunk;
import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ChunkKey {

    protected final WorldChunk worldChunk;

    public ChunkKey(WorldChunk worldChunk) {
        this.worldChunk = worldChunk;
    }

    public Set<SplitChunkKey> splitChunkKey() {
        Set<SplitChunkKey> split = new HashSet<>();
        for (int y = 0; y <= 256; y += 16) {
            split.add(new SplitChunkKey(worldChunk, y));
        }
        return split;
    }

    public final boolean isChunkLoadedIn(World world) {
        return world.isChunkLoaded(this.worldChunk.x, this.worldChunk.z);
    }

    public final CompletableFuture<Chunk> getChunkIn(World world) {
        if (isChunkLoadedIn(world))
            return PaperLib.getChunkAtAsync(world, worldChunk.x, worldChunk.z);
        return null;
    }

    public final CompletableFuture<Chunk> loadChunkIn(World world) {
        return PaperLib.getChunkAtAsync(world, worldChunk.x, worldChunk.z);
    }

    public String toString() {
        return worldChunk.x + "_" + worldChunk.z;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkKey)) return false;
        ChunkKey chunkKey = (ChunkKey) o;
        return Objects.equals(worldChunk, chunkKey.worldChunk);
    }

    public int hashCode() {
        return Objects.hash(worldChunk);
    }
}