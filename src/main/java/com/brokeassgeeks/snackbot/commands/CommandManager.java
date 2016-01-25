package com.brokeassgeeks.snackbot.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

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

}
