package com.brokeassgeeks.snackbot.commands;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(CommandManager.class);

    private static CommandManager instance;
    @Getter private List<Class<?>> commands;


    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private CommandManager() {
        commands = new ArrayList<>();
    }

    public void addCommand(Class<?> command) {
        commands.add(command);
    }


    public ArrayList<CommandData> load() {
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

    public void write(ArrayList<CommandData>  commands) {

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



    public ArrayList<CommandData> getCommandData() {
        return load();
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


}
