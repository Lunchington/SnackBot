package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.twitch.TwitchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Twitch extends Command {

    private static List<String> streamers;

    public Twitch(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) {
        super(ircEvent,discordEvent, args);
        load();
    }
    @Override
    public void run() {
        if (streamers.size() == 0) {
            super.respond("<B><b>NO Streamers added!<N>");
            return;
        }

        if(args.length >= 2) {
            if(args.length < 3 || !args[1].equalsIgnoreCase("add")) {
                super.respond(String.format("<B><b>USAGE: %s add <channel>",args[0]));
            } else {
                super.respond(addChannel(args[2]));
            }
        } else {
            ArrayList<String> livestreamers = new ArrayList<>();
            load();

            for (String s : streamers) {

                TwitchResponse response = getTwitch(s);
                System.out.println(response);

                if (response != null && isChannelLive(response)) {
                    livestreamers.add(String.format("<B><b>%s<N> - %s", s, response.getStream().getChannel().getUrl()));
                }
            }

            if (livestreamers.size() > 0) {
                super.respond("<B><b>Currently Streaming<N>");
                for (String s: livestreamers)
                    super.respond(s);
            }
            else
                super.respond("<B><b>NO Streamers live!<N>");

        }


    }

    private boolean isvalidUser(String channel) {
        return getTwitch(channel) != null;
    }

    private boolean isChannelLive(TwitchResponse response) {
        return response.getStream() != null;
    }

    private TwitchResponse getTwitch(String channel) {
        try {
            String url = Utils.readUrl(String.format("https://api.twitch.tv/kraken/streams/%s?client_id=%s",channel,Config.TWITCH_CLIENT_ID));
            Gson gson = new Gson();

            return gson.fromJson(url, TwitchResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }


    private void load() {

        try {
            Gson gson = new Gson();
            File file = new File("data/streamers.json");

            Reader jsonFile = new FileReader(file);

            Type collectionType = new TypeToken<List<String>>(){}.getType();

            streamers = gson.fromJson(jsonFile,collectionType );
            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void writeJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(streamers);

        try {
            String jsonFile = "data/streamers.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addChannel(String args) {
        if(isvalidUser(args)) {
            for(String s: streamers) {
                if (s.equalsIgnoreCase(args))
                    return  String.format("<B><b>Channel:<N> %s is already in the list!",args);
            }
            return addStreamer(args);
        } else {
            return String.format("<B><b>Channel:<N> %s is not a valid channel",args);
        }
    }
    private String  addStreamer(String channel) {
        streamers.add(channel);
        writeJson();
        return  String.format("<B><b>Channel:<N> %s added!",channel);
    }

}
