package com.pantsareoffensive.snackbot.commands;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

public class Mojang extends BotCommand {
    private List<String> minecraftStatus; // This is for the inner array

    public Mojang() {
        super("minecraft");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {


    }

    @Override
    public void loadJson() {
        String site  = "http://status.mojang.com/check";


        Reader reader;
        try {
            reader = new InputStreamReader(new URL(site).openStream());
            Gson gson = new GsonBuilder().create();
            minecraftStatus = gson.fromJson(reader,List.class);
            System.out.println(minecraftStatus);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
