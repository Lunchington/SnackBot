package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.mcserver.MinecraftServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.pircbotx.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUtils {
    private static final Logger logger = Logger.getLogger(AdminUtils.class.getName());

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
            logger.log(Level.SEVERE, "Cannot load admin config ...", e);
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
            logger.log(Level.SEVERE, "Cannot load admin config...", e);
        }
    }

    public static boolean isAdmin(User user, List<String> admins) {
        String uString = String.format("%s!%s@%s",user.getNick(),user.getLogin(),user.getHostname());
        String uStringWC = String.format("%s!*@%s",user.getNick(),user.getHostname());

        for (String s: admins) {
            String sWC ="NOTHING";
            if (s.equalsIgnoreCase(uString) || s.equalsIgnoreCase(sWC))
                    return true;
        }
        return false;
    }
}
