package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.commands.mcserver.MinecraftServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.io.*;


public class MinecraftServerUtils {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(MinecraftServerUtils.class);

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
            logger.error("Cannot load server config ...", e);
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
            logger.error( "Cannot load server config...", e);
        }
    }

    public static boolean setValue(String server, String setting, String value) {
        MinecraftServer servers[] = SnackBot.getServers();
        MinecraftServer s = getServerbyName(servers,server);
        boolean isSet = false;

        if (s == null)
            return false;

        if (setting.equalsIgnoreCase("pack")) {
            isSet = true;
            s.setPack(value);
        }

        if (setting.equalsIgnoreCase("version")) {
            isSet = true;
            s.setVersion(value);
        }

        if (setting.equalsIgnoreCase("host")) {
            isSet = true;
            s.setHost(value);
        }
        if (setting.equalsIgnoreCase("port")) {
            isSet = true;
            s.setPort(Integer.parseInt(value));
        }

        if (isSet)
            writeServerJson(servers);

        return isSet;
    }
}
