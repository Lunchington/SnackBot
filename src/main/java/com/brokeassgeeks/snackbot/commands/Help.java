package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.lang.reflect.InvocationTargetException;

public class Help extends Command{
    public Help(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("help");
        triggers.add("h");
    }

    @Override
    public void run() {
        String out = "";
        for (Class<?> cmd: CommandManager.getInstance().getCommands()) {
            Command command;
            try {
                command = (Command) cmd.getDeclaredConstructor(
                        GenericMessageEvent.class, String[].class).newInstance(event, args);
            } catch (InstantiationException | IllegalAccessException
                    | InvocationTargetException |NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }

            for (String s: command.getTriggers()) {
                out += Config.CATCH_CHAR + s+ " ";
            }
        }

        if (out.length() > 0) {
            event.getUser().send().notice(Utils.replaceTags("<B><b>Commands: <N>" + out));
        }
    }
}
