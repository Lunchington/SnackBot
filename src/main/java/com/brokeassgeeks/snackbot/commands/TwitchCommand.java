package com.brokeassgeeks.snackbot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.brokeassgeeks.snackbot.Twitch.Twitch;
import com.brokeassgeeks.snackbot.Twitch.TwitchResponse;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;


public class TwitchCommand extends Command{

    private static List<String> streamers;

    public TwitchCommand(GenericMessageEvent event, String[] args) {
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

                TwitchResponse response = Twitch.getTwitch(s);
                System.out.println(response);

                if (response != null && Twitch.isChannelLive(response)) {
                    output += String.format("%s - %s", s, response.getStream().getChannel().getUrl());
                }
            }

            if (output.length() > 0)
                super.respond(String.format("<B><b>Currently Streaming:<N> <g>%s<N>", output));
            else
                super.respond("<B><b>NO Streamers live!<N>");

        }


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
        if(Twitch.isvalidUser(args)) {
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
