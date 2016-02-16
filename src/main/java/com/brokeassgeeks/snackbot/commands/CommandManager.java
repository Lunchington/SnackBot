package com.brokeassgeeks.snackbot.commands;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static CommandManager instance;

    @Getter private List<String> allTriggers;
    @Getter private List<Class<?>> commands;

    private CommandData[] commandData;


    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private CommandManager() {
        commands = new ArrayList<>();
        allTriggers = new ArrayList<>();

    }

    public void addCommand(Class<?> command) {
        commands.add(command);
    }



    private class CommandData {
        @Getter
        private String command;
        private boolean enabled;
        private List<String> triggers;
    }

    public void load() {
        try {
            Gson gson = new Gson();
            File file = new File("data/commands.json");

            Reader jsonFile = new FileReader(file);
            commandData = gson.fromJson(jsonFile,CommandData[].class );
            jsonFile.close();

            for (CommandData d: commandData) {
                allTriggers.addAll(d.triggers);
            }

            ArrayList<SimpleCommandData> sc = SimpleCommand.load();

            assert sc != null;

            for (SimpleCommandData s : sc) {
                allTriggers.addAll(s.getTriggers());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandData getCommand(Command command) {
        load();

        for (CommandData c: commandData) {
            if(c.command.equalsIgnoreCase(command.getClass().getSimpleName()))
                return c;
        }
        return null;
    }

    public boolean isCommandEnabled(Command command) {
        CommandData commandData = getCommand(command);
        return (commandData == null || commandData.enabled);
    }

    public List<String> getTriggers(Command command) {
        CommandData commandData = getCommand(command);
        return commandData.triggers;
    }

}
