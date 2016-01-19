package com.brokeassgeeks.snackbot.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;


public class Twitch {

    public static boolean isvalidUser(String channel) {
        Boolean isvalid = true;
        try {
            String url = Utils.readUrl("https://api.twitch.tv/kraken/streams/" + channel);
            GsonBuilder builder = new GsonBuilder();
            Object o = builder.create().fromJson(url, Object.class);
            System.out.println(o.toString());

        } catch (IOException e) {
            isvalid = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isvalid;

    }
    public static boolean isChannelLive(String channelName) {
        TwitchResponse reponse = null;
        try {
            reponse = getTwitch(channelName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isChannelLive(reponse);
    }

    public static boolean isChannelLive(TwitchResponse response) {
            if (response.stream != null) {
                return true;
            }

        return false;
    }

    public static TwitchResponse getTwitch(String channel) {
        try {
            String url = Utils.readUrl("https://api.twitch.tv/kraken/streams/" + channel);
            Gson gson = new Gson();

            return gson.fromJson(url, TwitchResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
