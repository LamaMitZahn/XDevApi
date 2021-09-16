package de.ruben.xdevapi.labymod.display;

import com.google.gson.JsonObject;
import de.ruben.xdevapi.labymod.LabyModProtocol;
import org.bukkit.entity.Player;

public class EconomyDisplay {

    public void updateBalanceDisplay(Player player, EnumBalanceType type, int balance){
        updateBalanceDisplay(player, type, null, true, balance);
    }

    public void updateBalanceDisplay(Player player, EnumBalanceType type, boolean visible, int balance){
        updateBalanceDisplay(player, type, null, visible, balance);
    }

    public void updateBalanceDisplay(Player player, EnumBalanceType type, String iconUrl, boolean visible, int balance ) {
        JsonObject economyObject = new JsonObject();
        JsonObject cashObject = new JsonObject();

        cashObject.addProperty( "visible", visible );

        cashObject.addProperty( "balance", balance );


        if(iconUrl != null && !iconUrl.equals("")) {
            cashObject.addProperty("icon", "<url to image>");
        }

        JsonObject decimalObject = new JsonObject();
        decimalObject.addProperty("format", "##,###.##");
        decimalObject.addProperty("divisor", 1);
        cashObject.add( "decimal", decimalObject );

        economyObject.add(type.getKey(), cashObject);

        LabyModProtocol.sendLabyModMessage( player, "economy", economyObject );
    }

    public enum EnumBalanceType {
        CASH("cash"),
        BANK("bank");

        private final String key;

        EnumBalanceType(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }
}
