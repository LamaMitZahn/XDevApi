package de.ruben.xdevapi.performance.concurrent;

import de.ruben.xdevapi.XDevApi;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class XScheduler{

    private final XDevApi plugin;
    private final ScheduledExecutorService scheduledExecutorService;

    public XScheduler(XDevApi plugin) {
        this.plugin = plugin;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(4, new DefaultThreadFactory(plugin.getName()+" - Scheduler"));
    }

    public ScheduledFuture<?> asyncInterval(Runnable task, long delay, long interval) {
        return scheduledExecutorService.scheduleAtFixedRate(new CatchingRunnable(task), delay * 50, interval * 50, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> asyncSchedule(Runnable task, long delay) {
        return scheduledExecutorService.schedule(new CatchingRunnable(task), delay * 50, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> asyncSchedule(Runnable task, @NonNegative long delay, TimeUnit timeUnit) {
        return scheduledExecutorService.schedule(new CatchingRunnable(task), delay, timeUnit);
    }

    public void async(Runnable task) {
        scheduledExecutorService.execute(new CatchingRunnable(task));
    }

    public void shutdown() {
        plugin.consoleMessage("§6Shutting down Scheduler-Service!", true);
        scheduledExecutorService.shutdown();
    }
}
