package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.commands.ServerStatus;
import org.jibble.pircbot.Colors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static HashMap<String, String> replaceMap = new HashMap<String, String>()  {{
        put("<B>",Colors.BOLD);
        put("<U>",Colors.UNDERLINE);
        put("<N>",Colors.NORMAL);

        put("<r>",Colors.RED);
        put("<b>",Colors.BLUE);
        put("<y",Colors.YELLOW);
        put("<g>",Colors.GREEN);

    }};

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

    public static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }

    public static  boolean hostAvailabilityCheck(String _server, int _port) {
        try (Socket s = new Socket(_server, _port)) {
            return true;
        } catch (IOException ex) {
        /* ignore */
        }
        return false;
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


    public static String replaceTags(String string) {
        String s = string;
        for (String key:replaceMap.keySet()) {
            s = s.replaceAll(key, replaceMap.get(key));
        }
        return s;
    }

}
