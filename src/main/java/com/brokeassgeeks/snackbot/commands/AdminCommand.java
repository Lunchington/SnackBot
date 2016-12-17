package com.brokeassgeeks.snackbot.commands;


import com.brokeassgeeks.snackbot.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class AdminCommand extends Command {
    public AdminCommand(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {



    }
}
