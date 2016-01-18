package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.commands.ServerStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
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

    public static String parseforHTML(String str) {
        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern urlPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

        Matcher matcher = urlPattern.matcher(str);

        while (matcher.find()) {
            try {
                String newUrl = matcher.group();
                if (matcher.group().startsWith("www")) {
                    newUrl = "http://" + matcher.group();
                }

                Document doc = Jsoup.connect(newUrl).userAgent("Mozilla").timeout(6000).get();
                String title = doc.title();
               String shorturl = getShortUrl(newUrl);
                return title + " - " + shorturl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getShortUrl(String uri)
    {
        String tinyUrl = "";
        String urlString = "http://tinyurl.com/api-create.php?url=" + uri;

        try {
            URL url = new URL(urlString);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;

            while ((str = in.readLine()) != null) {
                tinyUrl += str;
            }
            in.close();
        }
        catch (Exception e) {
        }
        return tinyUrl;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isBot(String sender) {

        for (ServerStatus.ServerInfo s : SnackBot.bot.cmdServerStatus.getServers())
        {
            if ((sender.equalsIgnoreCase(s.name)) || sender.toLowerCase().startsWith("tom")) {

                return true;
            }
        }

        return false;
    }
}
