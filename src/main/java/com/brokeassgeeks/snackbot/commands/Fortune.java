package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.Utils.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;


public class Fortune extends Command {

    public Fortune(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {

        try {
            String s = Utils.chooseRandomLine(new File("data/fun/fortunes.txt"));

            super.respond(String.format("<B><b>%s,<N> %s",getSender(), s));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
