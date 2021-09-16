package de.ruben.xdevapi.labymod;

import de.ruben.xdevapi.labymod.display.EconomyDisplay;
import de.ruben.xdevapi.labymod.display.SubtitleDisplay;
import de.ruben.xdevapi.labymod.display.TablistDisplay;

public class LabyModDisplay {
    private EconomyDisplay economyDisplay;
    private SubtitleDisplay subtitleDisplay;
    private TablistDisplay tablistDisplay;

    public LabyModDisplay(){
        this.economyDisplay = new EconomyDisplay();
        this.subtitleDisplay = new SubtitleDisplay();
        this.tablistDisplay = new TablistDisplay();
    }

    public EconomyDisplay getEconomyDisplay() {
        return economyDisplay;
    }

    public SubtitleDisplay getSubtitleDisplay() {
        return subtitleDisplay;
    }

    public TablistDisplay getTablistDisplay() {
        return tablistDisplay;
    }
}
