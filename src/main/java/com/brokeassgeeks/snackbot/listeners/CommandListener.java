package com.brokeassgeeks.snackbot.listeners;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.CommandData;
import com.brokeassgeeks.snackbot.CommandManager;
import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.simplecommand.SimpleCommand;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.ListenerAdapter;
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
    public void onGenericMessage(GenericMessageEvent event) throws Exception {
        processComand(event,null);

    }

    public void onDiscord(MessageReceivedEvent event) throws Exception {
        processComand(null,event);
    }

    private void processComand(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent) throws Exception {
        String message;
        String sender;

        if (discordEvent != null) {
            message = discordEvent.getMessage().getContent();
            sender = discordEvent.getAuthor().getUsername();

        }
        else {
            message = ircEvent.getMessage();
            sender = ircEvent.getUser().getNick();
        }


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

        if (commandData == null )
            return;

        if (commandData.isEnabled()) {
            if (commandData.isSimple()) {
                logger.info("Simple Command: " + commandData.getName());
                threadPool.submit(new SimpleCommand(ircEvent,discordEvent,commandData, args));
            } else {

                Class<?> cmd = Class.forName("com.brokeassgeeks.snackbot.commands." + commandData.getName() );

                Command command = (Command) cmd.getDeclaredConstructor(
                        GenericMessageEvent.class, MessageReceivedEvent.class, String[].class).newInstance(ircEvent, discordEvent, args);

                logger.info("Executing Command: " + command.getClass().getName());
                threadPool.submit(command);
            }
        } else {
            logger.warn("Command Disabled: " + commandData.getName());
        }
    }

}

