package de.ruben.xdevapi.custom.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemPreset {

    public static GuiItem backItem(GuiAction<InventoryClickEvent> clickEventGuiAction){
        return backItem(Material.ARROW, "&9Zurück", new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem backItem(Material material, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return backItem(material, "&9Zurück", new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem backItem(Material material, String title, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return backItem(material, title, new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem backItem(Material material, List<Component> lore, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return backItem(material, "&9Zurück", lore, clickEventGuiAction);
    }

    public static GuiItem backItem(Material material, String title, List<Component> lore, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return ItemBuilder
                .from(material)
                .name(Component.text(ChatColor.translateAlternateColorCodes('&', title)))
                .lore(lore)
                .asGuiItem(clickEventGuiAction);
    }

    public static GuiItem closeItem(GuiAction<InventoryClickEvent> clickEventGuiAction){
        return closeItem(Material.BARRIER, "&4Schließen", new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem closeItem(Material material, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return closeItem(material, "&4Schließen", new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem closeItem(Material material, String title, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return closeItem(material, title, new ArrayList<>(), clickEventGuiAction);
    }

    public static GuiItem closeItem(Material material, List<Component> lore, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return closeItem(material, "&4Schließen", lore, clickEventGuiAction);
    }

    public static GuiItem closeItem(Material material, String title, List<Component> lore, GuiAction<InventoryClickEvent> clickEventGuiAction){
        return ItemBuilder
                .from(material)
                .name(Component.text(ChatColor.translateAlternateColorCodes('&', title)))
                .lore(lore)
                .asGuiItem(clickEventGuiAction);
    }

    public static GuiItem fillItem(GuiAction<InventoryClickEvent> clickEventGuiAction){
        return ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem(clickEventGuiAction);
    }

}
