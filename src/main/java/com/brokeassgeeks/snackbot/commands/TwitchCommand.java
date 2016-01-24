package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Twitch;
import com.brokeassgeeks.snackbot.Utils.TwitchResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TwitchCommand extends BotCommand{
    private List<String> streamers;

    public TwitchCommand() {
        super("twitch");
        streamers = new ArrayList<>();
        loadJson();
        this.setDesc("show users streaming on twitch, use: " + this.getFullCmd() + " add <NAME> to add a channel");
    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        String output = "";
        if (args.length() > 0) {
            String[] cmd = Utils.splitWords(args);
            if( cmd[0].equals("add")) {
                if(cmd.length <2) {
                    super.sendMessage(channel, String.format("<B><b>USAGE: %s add <channel>",getFullCmd()));
                } else {
                    super.sendMessage(channel,addChannel(cmd[1]));
                }
            }
        } else {

            if (streamers.size() == 0) {
                super.sendMessage(channel, "<B><b>NO Streamers added!<N>");
                return;
            }

            for (String s : streamers) {
                TwitchResponse response = Twitch.getTwitch(s);

                if (Twitch.isChannelLive(response)) {
                    output += String.format("%s - %s", s, response.getStream().getChannel().getUrl());
                }
            }

            if (output.length() > 0)
                super.sendMessage(channel, String.format("<B><b>Currently Streaming:<N> <g>%s<N>", output));
            else
                super.sendMessage(channel, "<B><b>NO Streamers live!<N>");

        }
    }


    @Override
    public void loadJson() {

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
