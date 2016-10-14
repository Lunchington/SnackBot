package com.brokeassgeeks.snackbot.simplecommand;

import ch.qos.logback.classic.Logger;
import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.CommandData;
import com.brokeassgeeks.snackbot.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.LoggerFactory;


public class SimpleCommand extends Command {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SimpleCommand.class);


    public SimpleCommand(GenericMessageEvent event, String[] args) {
        super(event, args);
    }
    public SimpleCommand(MessageReceivedEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        CommandData commandData = CommandManager.getInstance().getCommandDataFromTrigger(args[0].toLowerCase());
        if (commandData != null) {
            if (commandData.isPrivateMessage())
                super.respond(ircEvent.getUser(),commandData.getOutput());
            else
                super.respond(commandData.getOutput());
        }

    }
}
