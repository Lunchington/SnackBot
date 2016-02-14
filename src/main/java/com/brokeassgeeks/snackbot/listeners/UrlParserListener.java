package com.brokeassgeeks.snackbot.listeners;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParserListener extends ListenerAdapter {
    private String[]  blacklist = { "goo.gl", "pastebin", "redd.it"};

    @Override
    public void onMessage(MessageEvent event){

        if(event.getMessage().startsWith(Config.CATCH_CHAR) ||
                Utils.stringContainsItemFromList(event.getMessage(), this.blacklist)) {
            return;
        }

        String string = parseforHTML(event.getMessage());
        System.out.println(string);

        if (string != null && string.length() >0) {
            event.respondWith(Utils.replaceTags(string));

        }

    }

    private String parseforHTML(String str) {
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
                title = title.replaceAll("\\P{Print}", "");


                return String.format("%s - %s",title,shorturl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getShortUrl(String uri)
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
            e.printStackTrace();
        }
        return tinyUrl;
    }
}
