package com.brokeassgeeks.snackbot.Utils;

import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.SnackBot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminUtils {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AdminUtils.class);

    public static ArrayList<String> loadAdmins() {

        try {
            Gson gson = new Gson();
            File file = new File("data/admins.json");
            if (!file.exists()) {
                logger.info("Cannot load admin file ... creating default");

                writeAdmins(new ArrayList<String>());
            }
            Reader jsonFile = new FileReader(file);

            Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();

            ArrayList<String> s = gson.fromJson(jsonFile, collectionType);
            jsonFile.close();
            return s;

        } catch (Exception e) {
            logger.error("Cannot load admin config ...", e);
        }
        return null;
    }

    public static void writeAdmins(ArrayList<String> servers) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(servers);

        try {
            String jsonFile = "data/admins.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            logger.error("Cannot load admin config...", e);
        }
    }

    public static boolean isAdmin(User user, List<String> admins) {
        if (user == SnackBot.getBot().getUserBot())
            return false;

        String uString = String.format("%s!%s@%s",user.getNick(),user.getLogin(),user.getHostname());
        String uStringWC = String.format("%s!*@%s",user.getNick(),user.getHostname());

        for (Channel c :SnackBot.getBot().getUserChannelDao().getAllChannels()) {
            if (c.isOp(user))
                    return true;
        }

        for (String s: admins) {
            String sWC ="NOTHING";
            if (s.equalsIgnoreCase(uString) || s.equalsIgnoreCase(sWC))
                    return true;
        }
        return false;
    }

    public static boolean match(String host, String mask) {
        String[] sections = mask.split("\\*");
        String text = host;
        for (String section : sections) {
            int index = text.indexOf(section);
            if (index == -1) {
                return false;
            }
            text = text.substring(index + section.length());
        }
        return true;
    }
}
