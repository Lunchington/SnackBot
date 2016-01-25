package com.brokeassgeeks.snackbot.mcserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftServerUtils {
    private static final Logger logger = Logger.getLogger(MinecraftServerUtils.class.getName());

    public static void updateServerActivity(MinecraftServer[] servers, String name, String sender, long time) {
        MinecraftServer s = getServerbyName(servers,name);

        if (s != null) {
            s.updateActivity(sender, time);
            writeServerJson(servers);
        }
    }

    public static MinecraftServer getServerbyName(MinecraftServer[] servers, String name) {
        for (MinecraftServer s : servers) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public static MinecraftServer[] loadServers() {

        try {
            Gson gson = new Gson();
            File file = new File("data/servers.json");
            if (!file.exists()) {
                logger.info("Cannot load server file ... creating default");
                MinecraftServer minecraftServer = new MinecraftServer();
                MinecraftServer[] servers = new MinecraftServer[1];
                servers[0] = minecraftServer;
                writeServerJson(servers);
            }
            Reader jsonFile = new FileReader(file);
            MinecraftServer[] s = gson.fromJson(jsonFile, MinecraftServer[].class);
            jsonFile.close();
            return s;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Cannot load server config ...", e);
        }
        return null;
    }

    public static void writeServerJson(MinecraftServer[] servers) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(servers);

        try {
            String jsonFile = "data/servers.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot load server config...", e);
        }
    }
}
