package de.ruben.xdevapi;

import de.ruben.xdevapi.custom.gui.labymod.LabyModGUIResultHandler;
import de.ruben.xdevapi.labymod.LabyModDisplay;
import de.ruben.xdevapi.listener.LabyModMessageListener;
import de.ruben.xdevapi.listener.PlayerListener;
import de.ruben.xdevapi.message.MessageService;
import de.ruben.xdevapi.performance.concurrent.CatchingRunnable;
import de.ruben.xdevapi.performance.concurrent.TaskBatch;
import de.ruben.xdevapi.performance.concurrent.XScheduler;
import de.ruben.xdevapi.performance.efficiency.Benchmark;
import de.ruben.xdevapi.labymod.LabyUsers;
import de.ruben.xdevapi.util.XUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public final class XDevApi extends JavaPlugin {

    public static XDevApi xDevApi;

    private XScheduler xScheduler;
    private LabyUsers labyUsers;
    private LabyModGUIResultHandler labyModGUIResultHandler;
    private XUtil xUtil;
    private MessageService messageService;
    private Benchmark benchmark;
    private LabyModDisplay labyModDisplay;

    @Override
    public void onEnable() {
        this.xDevApi = this;

        this.xScheduler = new XScheduler(this);
        this.labyUsers = new LabyUsers();
        this.labyModGUIResultHandler = new LabyModGUIResultHandler();
        this.xUtil = new XUtil();
        this.messageService = new MessageService();
        this.benchmark = new Benchmark();
        this.labyModDisplay = new LabyModDisplay();

        messages().loadMessagesWithDefaults();

        getServer().getMessenger().registerIncomingPluginChannel(this, "labymod3:main", new LabyModMessageListener());

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public static XDevApi getInstance(){
        return xDevApi;
    }

    public void consoleMessage(String message, boolean debug){
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&7[&9&lAddictZone&7] &f- "+message));
    }

    public XScheduler getxScheduler() {
        return xScheduler;
    }

    public LabyUsers getLabyUsers() {
        return labyUsers;
    }

    public LabyModGUIResultHandler getLabyModGUIResultHandler() {
        return labyModGUIResultHandler;
    }

    public XUtil getxUtil() {
        return xUtil;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public MessageService getMessages() {
        return messageService;
    }

    public MessageService messages() {
        return messageService;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public LabyModDisplay getLabyModDisplay() {
        return labyModDisplay;
    }



    public TaskBatch<JavaPlugin> createTaskBatch() {

        return new TaskBatch<>(this) {
            @Override
            public void runSync(@Nonnull Runnable runnable) {
                Bukkit.getScheduler().runTask(getPlugin(), runnable);
            }

            @Override
            public void runAsync(@Nonnull Runnable runnable) {
                getxScheduler().async(new CatchingRunnable(runnable));
            }

            @Override
            public void onFinishBatch() {

            }
        };
    }
}
