package com.pantsareoffensive.snackbot.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pantsareoffensive.snackbot.SnackBot;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lunchbox on 2/20/2015.
 */
public class Insult extends BotCommand {
    public Insults insults;

    public Insult() {
        super("insult");
        loadJson();
    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String message) {
        SnackBot.bot.sendMessage(channel, String.format("%s you, %s",sender,getInsult()) );
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

    private String getInsult() {
        Random r = new Random();

        String adj1 = insults.adj[r.nextInt(insults.adj.length)];
        String adj2 = adj1;
        while(adj1 == adj2) {
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
