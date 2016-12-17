package com.brokeassgeeks.snackbot;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(CommandManager.class);
    private static CommandManager instance;

    private List<CommandData> commandData;

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private CommandManager() {
        commandData = new ArrayList<>();
        commandData = load();
    }

    private List<CommandData> load() {
        try {
            ArrayList<CommandData> s;
            Gson gson = new Gson();
            File file = new File("data/commands.json");

            Reader jsonFile = new FileReader(file);
            Type collectionType = new TypeToken<ArrayList<CommandData>>(){}.getType();

            s = gson.fromJson(jsonFile,collectionType );
            jsonFile.close();
            return s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(List<CommandData>  commands) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(commands);

        try {
            String jsonFile = "data/commands.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            logger.error("Cannot write commands config...", e);
        }
    }


    private void reload() {
        commandData = load();
    }

    public List<CommandData> getCommandData() {
        return commandData;
    }


    public CommandData getCommandDataFromTrigger(String trigger) {
        for (CommandData s : getCommandData()) {
            if(s.getTriggers().contains(trigger))
                return s;
        }
        return null;
    }

    public CommandData triggerExists(String trigger) {
        for (CommandData s : getCommandData()) {
            if (s.getTriggers().contains(trigger.toLowerCase()))
                    return s;
        }
        return null;
    }

    public void writeReload(List<CommandData> sc) {
        write(sc);
        reload();
    }


}
