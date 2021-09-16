package de.ruben.xdevapi.util;

import net.md_5.bungee.api.ChatColor;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private DecimalFormat moneyFormat = new DecimalFormat("###,###.##");

    public String fullyFormattedString(String string){
        return chatColorFormattedString(chatColorFormattedString(string));
    }

    public String chatColorFormattedString(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public String hexColorFormattedString(String string){
        Pattern HEX_PATTERN = Pattern.compile("#[A-Fa-f0-9]{6}");

        Matcher matcher = HEX_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        return matcher.appendTail(buffer).toString();
    }

    public String moneyFormat(Double input){
        return moneyFormat.format(input);
    }

    public String moneyFormat(Integer input){
        return moneyFormat.format(input);
    }

    public String moneyFormat(Long input){
        return moneyFormat.format(input);
    }

    public String moneyFormat(String input){
        return moneyFormat.format(input);
    }

    public String moneyFormat(Object input){
        return moneyFormat.format(input);
    }
}
