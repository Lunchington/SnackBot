package com.pantsareoffensive.snackbot.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
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


    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
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
        Gson gson =  new GsonBuilder().create();

        OAuthService oAuthService = new ServiceBuilder()
                .provider(GoogleApi.class).apiKey("anonymous")
                .apiSecret("anonymous")
                .scope("https://www.googleapis.com/auth/urlshortener").build();

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST,"https://www.googleapis.com/urlshortener/v1/url");
        oAuthRequest.addHeader("Content-Type", "application/json");
        String json = "{\"longUrl\": \"" + uri + "\"}";
        oAuthRequest.addPayload(json);
        Response response = oAuthRequest.send();
        Type typeOfMap = new TypeToken<Map<String, String> >() {}.getType();
        Map<String, String> responseMap = gson.fromJson(response.getBody(), typeOfMap);

        return (String)responseMap.get("id");
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
