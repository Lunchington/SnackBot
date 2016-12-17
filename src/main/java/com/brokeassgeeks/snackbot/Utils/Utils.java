package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.DataManager;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

public final class Utils {

    public static boolean stringContainsItemFromList(String string, String[] items) {
        for (String s : items) {
            if (string.toLowerCase().contains(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String readUrl(String urlString) throws IOException{
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }


    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isServer(String sender) {

        for (MinecraftServer s : DataManager.getInstance().getServers())
        {
            if ((sender.equalsIgnoreCase(s.getName())) || sender.toLowerCase().startsWith("tom")) {

                return true;
            }
        }
        return false;
    }

    public static String chooseRandomLine(File f) throws FileNotFoundException
    {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }


    public static Date getTime(Long time) {
        return new Date(time);
    }

    public  static String getTime(Date time) {
        Locale locale = Locale.getDefault();
        TimeZone currentTimeZone = TimeZone.getDefault();

        DateFormat formatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT,
                DateFormat.LONG,
                locale);
        formatter.setTimeZone(currentTimeZone);

        return formatter.format(time);
    }


}
