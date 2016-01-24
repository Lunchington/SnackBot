package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import java.util.Random;

public class Insult extends BotCommand {
    private Insults insults;

    public Insult() {
        super("insult");
        loadJson();
        setDesc("get a fun description!");
    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        String message =  "<B>%s<N> you, %s";
        if (args.length() > 0) {
            args = Utils.splitWords(args)[0];

            if (SnackBot.bot.isUserInChannel(channel,args))
                sender = args;
            else
                message = String.format("%s %s %s is not even here", message ,sender,args);

        }
        super.sendMessage(channel,String.format(message,sender,getRandomInsult()));
    }

    @Override
    public void loadJson() {

        try {
            Gson gson = new Gson();
            File file = new File("data/insults.json");

            Reader jsonFile = new FileReader(file);

            this.insults= gson.fromJson(jsonFile, Insults.class);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public String getRandomInsult() {
        Random r = new Random();

        String adj1 = insults.adj[r.nextInt(insults.adj.length)];
        String adj2 = adj1;
        while(adj1.equals(adj2)) {
            adj2 = insults.adj[r.nextInt(insults.adj.length)];
        }

        String amt = insults.amt[r.nextInt(insults.amt.length)];
        String noun = insults.noun[r.nextInt(insults.noun.length)];

        return String.format("%s %s of %s %s", adj1, amt,adj2,noun);
    }

    private class Insults {
        String[] adj;
        String[] amt;
        String[] noun;
    }
}
