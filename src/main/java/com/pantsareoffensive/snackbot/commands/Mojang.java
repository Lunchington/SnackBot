package com.pantsareoffensive.snackbot.commands;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pantsareoffensive.snackbot.SnackBot;
import org.jibble.pircbot.Colors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Mojang extends BotCommand {

    ArrayList<HashMap<String, String>> jsonObject;
    HashMap<String, String> shortNames = new HashMap<String, String>()  {};


    public Mojang() {

        super("minecraft");
        shortNames.put("minecraft.net", "MineCraft.Net");
        shortNames.put("session.minecraft.net", "Sesssion");
        shortNames.put("account.mojang.com", "Account");
        shortNames.put("auth.mojang.com", "Auth");
        shortNames.put("skins.minecraft.net", "Skins");
        shortNames.put("authserver.mojang.com", "AuthServer");
        shortNames.put("sessionserver.mojang.com", "Session");
        shortNames.put("api.mojang.com", "Api");
        shortNames.put("textures.minecraft.net", "Textures");

    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        loadJson();
        SnackBot.bot.sendMessage(target, getStatus());
    }

    @Override
    public void loadJson() {
        String site  = "http://status.mojang.com/check";
        Gson gson = new Gson();


        try {
            json = BotCommand.readUrl(site);
            Type collectionType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            jsonObject = gson.fromJson(json,collectionType);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    private String getStatus() {
        String output = "";

        for (HashMap<String, String> g: jsonObject) {
            for (String key:g.keySet()) {
                String color =  (g.get(key).toLowerCase().equals("green")) ? Colors.GREEN : Colors.RED ;

                output += color + getShortName(key) + Colors.NORMAL +" ";

            }
        }

        return output;
    }

    private String getShortName(String s) {
        return shortNames.get(s);
    }


}
