package com.brokeassgeeks.snackbot.Utils;

import org.pircbotx.Colors;

import java.util.HashMap;

/**
 * Created by Lunchington on 10/29/2016.
 */
public final class MessageUtils {

    private static HashMap<String, String> replaceMap = new HashMap<String, String>()  {{
        put("<B>", Colors.BOLD);
        put("<U>",Colors.UNDERLINE);
        put("<N>",Colors.NORMAL);

        put("<r>",Colors.RED);
        put("<b>",Colors.BLUE);
        put("<y>",Colors.YELLOW);
        put("<g>",Colors.GREEN);

    }};

    private MessageUtils() {}


    public static String replaceTags(String string) {
        String s = string;
        for (String key:replaceMap.keySet()) {
            if (s.contains(key))
                s = s.replaceAll(key, replaceMap.get(key));
        }
        return s;
    }

    public static String replaceTagsDiscord(String string) {
        String s = string;
        for (String key:replaceMap.keySet()) {
            if (s.contains(key))
                s = s.replaceAll(key, "");
        }
        return s;
    }

}
