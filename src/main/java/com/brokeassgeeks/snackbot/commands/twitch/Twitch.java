package com.brokeassgeeks.snackbot.commands.twitch;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;


public class Twitch extends Command {

    private static List<String> streamers;

    public Twitch(GenericMessageEvent event, String[] args) {
        super(event, args);
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

            String output = "";
            load();
            for (String s : streamers) {

                TwitchResponse response = getTwitch(s);
                System.out.println(response);

                if (response != null && isChannelLive(response)) {
                    output += String.format("%s - %s", s, response.getStream().getChannel().getUrl());
                }
            }

            if (output.length() > 0)
                super.respond(String.format("<B><b>Currently Streaming:<N> <g>%s<N>", output));
            else
                super.respond("<B><b>NO Streamers live!<N>");

        }


    }

    private boolean isvalidUser(String channel) {
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

    public boolean isChannelLive(TwitchResponse response) {
        return response.getStream() != null;
    }

    private TwitchResponse getTwitch(String channel) {
        try {
            String url = Utils.readUrl("https://api.twitch.tv/kraken/streams/" + channel);
            Gson gson = new Gson();

            return gson.fromJson(url, TwitchResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }


    public void load() {

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

    public void writeJson() {
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

    public String addChannel(String args) {
        if(isvalidUser(args)) {
            if  (streamers.contains(args.toLowerCase())) {
                return String.format("<B><b>Channel:<N> %s is already in the list!",args);
            }
            else {
                return addStreamer(args);
            }

        } else {
            return String.format("<B><b>Channel:<N> %s is not a valid channel",args);
        }
    }
    public String  addStreamer(String channel) {
        streamers.add(channel);
        writeJson();
        return  String.format("<B><b>Channel:<N> %s added!",channel);
    }

}
