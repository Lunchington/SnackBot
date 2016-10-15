package com.brokeassgeeks.snackbot.simplecommand;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.CommandData;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;


public class SimpleCommand extends Command {
    private CommandData commandData;

    public SimpleCommand(GenericMessageEvent event, CommandData data, String[] args) {
        super(event, args);
        this.commandData = data;
    }
    public SimpleCommand(MessageReceivedEvent event, CommandData data, String[] args) {
        super(event, args);
        this.commandData = data;
    }

    @Override
    public void run() {
            if (commandData.isPrivateMessage())
                super.respondUser(commandData.getOutput());
            else
                super.respond(commandData.getOutput());
    }
}
