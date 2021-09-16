package de.ruben.xdevapi.custom.bossbar;

import de.ruben.xdevapi.XDevApi;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class XBossBar {

    private final BossBar bossBar;
    private final Map<UUID, BukkitTask> hideTasks = new ConcurrentHashMap<>();

    public XBossBar(String title, BarColor barColor, BarStyle barStyle, BarFlag... barFlags) {
        this.bossBar = Bukkit.createBossBar(title, barColor, barStyle, barFlags);
    }

    public void setProgress(float progress) {
        bossBar.setProgress(progress);
    }

    public void showToPlayer(Player player) {
        stopHideTask(player.getUniqueId());
        bossBar.addPlayer(player);
    }

    public void hideFromPlayer(Player player) {
        stopHideTask(player.getUniqueId());
        bossBar.removePlayer(player);
    }

    public void showForTime(Player player, long timeInTicks) {
        bossBar.addPlayer(player);
        startHideTask(player.getUniqueId(), timeInTicks);
    }

    private void startHideTask(UUID uuid, long whenToHideInTicks) {
        hideTasks.put(uuid, Bukkit.getScheduler().runTaskLater(XDevApi.getInstance(), () -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null)
                return;
            hideFromPlayer(player);
        }, whenToHideInTicks));
    }

    public void clear() {
        List<Player> list = new ArrayList<>(bossBar.getPlayers());
        for (Player player : list)
            bossBar.removePlayer(player);
        bossBar.setVisible(false);
        hideTasks.forEach((uuid, bukkitTask) -> {
            if (!bukkitTask.isCancelled())
                bukkitTask.cancel();
        });
        hideTasks.clear();
    }

    private void stopHideTask(UUID uuid) {
        if (!hideTasks.containsKey(uuid))
            return;
        BukkitTask task = hideTasks.get(uuid);
        if (!task.isCancelled())
            task.cancel();
        hideTasks.remove(uuid);
    }
}
