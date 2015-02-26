package com.pantsareoffensive.snackbot.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pantsareoffensive.snackbot.SnackBot;
import com.pantsareoffensive.snackbot.Utils.Twitch;
import com.pantsareoffensive.snackbot.Utils.TwitchResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class TwitchCommand extends BotCommand{
    protected List<String> streamers;
    String chan;

    private String jsonFile = "data/streamers.json";

    public TwitchCommand() {
        super("twitch");
        streamers = new ArrayList<String>();
        loadJson();
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        this.chan = target;
        String output = "";
        if (args.length() > 0) {
            String[] cmd = BotCommand.splitWords(args);
            if( cmd[0].toLowerCase().equalsIgnoreCase("add")) {
                if(cmd.length <2) {
                    SnackBot.bot.sendMessage(target, sender + " need to specify a channel to add");
                } else {
                    addChannel(cmd[1]);
                }
            }
        } else {
            if(streamers.size() > 0) {
                for(String s:streamers) {
                    if(Twitch.isChannelLive(s)) {
                        output += " " + s;
                    }
                }

                if(output.length()>0) {
                    SnackBot.bot.sendMessage(target, "Currently Streaming:" + output);

                } else {
                    SnackBot.bot.sendMessage(target, "NO Streamers live!");

                }
            } else {
                SnackBot.bot.sendMessage(target, "NO Streamers added!");

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addChannel(String args) {
        if(Twitch.isvalidUser(args)) {
            if  (streamers.contains(args.toLowerCase())) {
                SnackBot.bot.sendMessage(chan, "Channel: " + args + " is already in list!");
            }
            else {
                addStreamer(chan, args);
            }

        } else {
            SnackBot.bot.sendMessage(chan, args + " is not a valid Twitch Channel");

        }
    }
    public void addStreamer(String target, String channel) {
        streamers.add(channel);
        SnackBot.bot.sendMessage(target, "Channel: " + channel + " added!");

        writeJson();

    }
}
