package com.brokeassgeeks.snackbot.listeners;

import com.brokeassgeeks.snackbot.commands.Command;
import com.brokeassgeeks.snackbot.commands.CommandManager;
import com.brokeassgeeks.snackbot.Configuration.Config;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class CommandListener extends ListenerAdapter{
    private static final Logger logger = Logger.getLogger(CommandListener.class.getName());
    private ExecutorService threadPool;

    public CommandListener() {
        threadPool = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onGenericMessage(final GenericMessageEvent event) throws Exception {

        if (!canUseCommand(event.getUser())) {
            return;
        }

        if(!event.getMessage().startsWith(Config.CATCH_CHAR)) {
            return;
        }


        String[] args = event.getMessage().split(" ");

        for(Class<?> cmd : CommandManager.getInstance().getCommands()) {
            Command command;
            try {
                command = (Command) cmd.getDeclaredConstructor(
                        GenericMessageEvent.class, String[].class).newInstance(event, args);
            } catch (InstantiationException | IllegalAccessException
                    | InvocationTargetException |NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }

            args[0] = args[0].replaceAll(Config.CATCH_CHAR,"");
            if (command.getTriggers().contains(args[0].toLowerCase())) {
                threadPool.submit(command);
            }

        }
    }

    private boolean canUseCommand(User user) {
        return true;
    }
}
