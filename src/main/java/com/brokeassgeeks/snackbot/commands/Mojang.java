package com.brokeassgeeks.snackbot.commands;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Mojang extends Command {

    private ArrayList<HashMap<String, String>> servers;
    private static HashMap<String, String> shortNames = new HashMap<String, String>()  {};

    public Mojang(GenericMessageEvent event, String[] args) {
        super(event, args);
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
    public void init() {
        triggers.add("mojang");
        triggers.add("mineccraft");
    }

    @Override
    public void run() {
        queryMojang();
        super.respond(getStatus());
    }

    public void queryMojang() {
        String site  = "http://status.mojang.com/check";
        Gson gson = new Gson();

        try {
            String json = Utils.readUrl(site);
            Type collectionType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            servers = gson.fromJson(json,collectionType);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getStatus() {
        String output = "";

        for (HashMap<String, String> g: servers) {
            for (String key:g.keySet()) {
                String color =  (g.get(key).toLowerCase().equals("green")) ? "<g>": "<r>" ;

                output += String.format("%s <N>", color + getShortName(key));
            }
        }
        return output;
    }

    private String getShortName(String s) {
        return shortNames.get(s);
    }


}
