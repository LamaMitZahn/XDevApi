package de.ruben.xdevapi.custom.gui;

import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.custom.gui.consumer.TriConsumer;
import de.ruben.xdevapi.custom.gui.response.ConfirmationResponse;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.Function;

public class NoLabyGUITemplate {

    public static AnvilGUI.Builder createIntegerInputGUI(XDevApi xDevApi, String title, String notValidNumber, Function<Integer, AnvilGUI.Response> callback){
        return createIntegerInputGUI(xDevApi, title, null, notValidNumber, callback);
    }

    public static AnvilGUI.Builder createDoubleInputGUI(Locale parsingLocale, XDevApi xDevApi, String title,  String notValidNumber, Function<Double, AnvilGUI.Response> callback){
        return createDoubleInputGUI(parsingLocale, xDevApi, title, null, notValidNumber, callback);
    }

    public static AnvilGUI.Builder createStringInput(XDevApi xDevApi, String title, Function<String, AnvilGUI.Response> callback){
        return createStringInput(xDevApi, title, null, callback);
    }

    public static AnvilGUI.Builder createIntegerInputGUI(XDevApi xDevApi, String title, String defaultInput, String notValidNumber, Function<Integer, AnvilGUI.Response> callback){
        return new AnvilGUI.Builder()
                .plugin(xDevApi)
                .title(ChatColor.translateAlternateColorCodes('&', title))
                .itemLeft(ItemBuilder.from(Material.GOLD_INGOT).name(Component.text(defaultInput == null ? "" : defaultInput)).build())
                .onComplete((player1, s) -> {
                    try {
                        Integer number = Integer.parseInt(s);
                        return callback.apply(number);
                    } catch (NumberFormatException e) {
                        return AnvilGUI.Response.text(notValidNumber);
                    }
                });
    }

    public static AnvilGUI.Builder createDoubleInputGUI(Locale parsingLocale, XDevApi xDevApi, String title, String defaultInput,  String notValidNumber, Function<Double, AnvilGUI.Response> callback) {
        return new AnvilGUI.Builder()
                .plugin(xDevApi)
                .title(ChatColor.translateAlternateColorCodes('&', title))
                .itemLeft(ItemBuilder.from(Material.GOLD_INGOT).name(Component.text(defaultInput == null ? "" : defaultInput)).build())
                .onComplete((player1, s) -> {
                    try {
                        NumberFormat format = NumberFormat.getInstance(parsingLocale);
                        Number number = format.parse(s);
                        return callback.apply(number.doubleValue());
                    } catch (NumberFormatException | ParseException e) {
                        return AnvilGUI.Response.text(notValidNumber);
                    }
                });
    }

    public static AnvilGUI.Builder createStringInput(XDevApi xDevApi, String title, String defaultInput, Function<String, AnvilGUI.Response> callback) {
        return new AnvilGUI.Builder()
                .plugin(xDevApi)
                .title(ChatColor.translateAlternateColorCodes('&', title))
                .itemLeft(ItemBuilder.from(Material.GOLD_INGOT).name(Component.text(defaultInput == null ? "" : defaultInput)).build())
                .onComplete((player1, s) -> callback.apply(s));
    }

    public static void createStandardSelectConfirmationGUI(Player player, TriConsumer<Gui, ConfirmationResponse, Player> callback){
        GuiItem yesItem = ItemBuilder.from(Material.GREEN_WOOL).amount(1).name(Component.text(ChatColor.translateAlternateColorCodes('&', "&a&lJa"))).asGuiItem();
        GuiItem noItem = ItemBuilder.from(Material.RED_WOOL).amount(1).name(Component.text(ChatColor.translateAlternateColorCodes('&', "&c&lNein"))).asGuiItem();
        createSelectConfirmationGUI(player, "§bBist du dir sicher?", yesItem, noItem, callback);
    }

    public static void createStandardSelectConfirmationGUI(Player player, String title, TriConsumer<Gui, ConfirmationResponse, Player> callback){
        GuiItem yesItem = ItemBuilder.from(Material.GREEN_WOOL).amount(1).name(Component.text(ChatColor.translateAlternateColorCodes('&', "&a&lJa"))).asGuiItem();
        GuiItem noItem = ItemBuilder.from(Material.RED_WOOL).amount(1).name(Component.text(ChatColor.translateAlternateColorCodes('&', "&c&lNein"))).asGuiItem();
        createSelectConfirmationGUI(player, title, yesItem, noItem, callback);
    }

    public static <TriFunction> void createSelectConfirmationGUI(Player player, String title, GuiItem yesItem, GuiItem noItem, TriConsumer<Gui, ConfirmationResponse, Player> consumer) {
        Gui gui = Gui.gui()
                .title(Component.text(ChatColor.translateAlternateColorCodes('&', title)))
                .rows(3)
                .disableAllInteractions()
                .create();

        fillGUI(gui, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());

        gui.addSlotAction(11, event -> consumer.accept(gui, ConfirmationResponse.NO, player));
        gui.addSlotAction(15, event -> consumer.accept(gui, ConfirmationResponse.YES, player));



        gui.setItem(11, noItem);
        gui.setItem(15, yesItem);

        Bukkit.getScheduler().runTask(XDevApi.getInstance(), () -> gui.open(player));
    }

    public static void fillGUI(Gui gui, GuiItem fillItem){
        for(int i = 0; i < gui.getInventory().getSize(); ++i){
            gui.addItem(fillItem);
        }
    }
}
