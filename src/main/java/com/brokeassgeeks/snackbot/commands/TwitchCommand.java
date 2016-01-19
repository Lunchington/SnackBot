package com.brokeassgeeks.snackbot.commands;

import com.google.gson.Gson;
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
    private String chan;

    private String jsonFile = "data/streamers.json";

    public TwitchCommand() {
        super("twitch");
        streamers = new ArrayList<>();
        loadJson();
        this.setDesc("show users streaming on twitch, use: " + Config.CATCH_CHAR + this.getCommandName() + " add <NAME> to add yourself to twitch");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        this.chan = target;
        String output = "";
        if (args.length() > 0) {
            String[] cmd = splitWords(args);
            if( cmd[0].toLowerCase().equalsIgnoreCase("add")) {
                if(cmd.length <2) {
                    super.sendMessage(target, String.format("<B>%s<N> need to specify a channel to add",sender));
                } else {
                    addChannel(cmd[1]);
                }
            }
        } else {
            if(streamers.size() > 0) {
                for(String s:streamers) {
                    TwitchResponse response = Twitch.getTwitch(s);

                    if(Twitch.isChannelLive(response)) {
                        output += String.format("%s - %s",s,response.stream.channel.url);
                    }
                }

                if(output.length()>0) {
                    super.sendMessage(target, String.format("<B>Currently Streaming:<N> <g>%s<N>",output));

                } else {
                    super.sendMessage(target, "<B>NO Streamers live!<N>");
                }
            } else {
                super.sendMessage(target, "<B>NO Streamers added!<N>");

            }

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
        Gson gson = new Gson();
        String s = gson.toJson(streamers);

        try {
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addChannel(String args) {
        if(Twitch.isvalidUser(args)) {
            if  (streamers.contains(args.toLowerCase())) {
                super.sendMessage(chan, String.format("<B>Channel:<N> %s is already in the list!",args));
            }
            else {
                addStreamer(chan, args);
            }

        } else {
            super.sendMessage(chan, String.format("<B>Channel:<N> %s is not a valid channel",args));

        }
    }
    public void addStreamer(String target, String channel) {
        streamers.add(channel);
        super.sendMessage(target, String.format("<B>Channel:<N> %s added!",channel));

        writeJson();

    }
}
