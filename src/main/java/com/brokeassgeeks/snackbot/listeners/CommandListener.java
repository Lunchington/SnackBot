package com.brokeassgeeks.snackbot.listeners;

import com.brokeassgeeks.snackbot.commands.Command;
import com.brokeassgeeks.snackbot.commands.CommandData;
import com.brokeassgeeks.snackbot.commands.CommandManager;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.commands.simplecommand.SimpleCommand;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandListener extends ListenerAdapter {
    private final Logger logger = (Logger) LoggerFactory.getLogger(CommandListener.class);

    private ExecutorService threadPool;

    public CommandListener() {
        threadPool = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onGenericMessage(final GenericMessageEvent event) throws Exception {

        String message = event.getMessage();
        String sender = event.getUser().getNick();

        if (message.startsWith("<") && message.contains(">")) {
            sender = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
            message = message.replaceAll("^<.*?>", "").trim();
        }

        if (!message.startsWith(Config.CATCH_CHAR)) {
            return;
        }

        String[] args = message.split(" ");
        args[0] = args[0].replaceAll(Config.CATCH_CHAR, "");
        String trigger = args[0].toLowerCase();

        CommandData commandData = CommandManager.getInstance().getCommandDataFromTrigger(trigger);

        if (commandData.isEnabled()) {
            if (commandData.isSimple()) {
                logger.info("Simple Command: " + commandData.getName());
                threadPool.submit(new SimpleCommand(event, args));
            } else {

                for (Class<?> cmd : CommandManager.getInstance().getCommands()) {
                    Command command;
                    try {
                        command = (Command) cmd.getDeclaredConstructor(
                                GenericMessageEvent.class, String[].class).newInstance(event, args);
                    } catch (InstantiationException | IllegalAccessException
                            | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        continue;
                    }

                    if (command.getClass().getSimpleName().equalsIgnoreCase(commandData.getName())) {
                        logger.info("Executing Command: " + command.getClass().getName());
                        command.setSender(sender);
                        threadPool.submit(command);
                    }

                }
            }
        } else {
            logger.warn("Command Disabled: " + commandData.getName());
        }
    }

    public void onDiscord(MessageReceivedEvent event) throws Exception {
        String message = event.getMessage().getContent();
        String sender = event.getAuthor().getUsername();

        if (message.startsWith("<") && message.contains(">")) {
            sender = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
            message = message.replaceAll("^<.*?>", "").trim();
        }

        if (!message.startsWith(Config.CATCH_CHAR)) {
            return;
        }

        String[] args = message.split(" ");
        args[0] = args[0].replaceAll(Config.CATCH_CHAR, "");
        String trigger = args[0].toLowerCase();

        CommandData commandData = CommandManager.getInstance().getCommandDataFromTrigger(trigger);

        if (commandData.isEnabled()) {
            if (commandData.isSimple()) {
                logger.info("Simple Command: " + commandData.getName());
                threadPool.submit(new SimpleCommand(event, args));
            } else {

                for (Class<?> cmd : CommandManager.getInstance().getCommands()) {
                    Command command;
                    try {
                        command = (Command) cmd.getDeclaredConstructor(
                                MessageReceivedEvent.class, String[].class).newInstance(event, args);
                    } catch (InstantiationException | IllegalAccessException
                            | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        continue;
                    }

                    if (command.getClass().getSimpleName().equalsIgnoreCase(commandData.getName())) {
                        logger.info("Executing Command: " + command.getClass().getName());
                        command.setSender(sender);
                        threadPool.submit(command);
                    }

                }
            }
        } else {
            logger.warn("Command Disabled: " + commandData.getName());
        }

    }

}

