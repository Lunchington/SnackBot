package com.brokeassgeeks.snackbot.commands.fun;

import com.brokeassgeeks.snackbot.commands.Command;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;


public class Fortune extends Command {

    public Fortune(GenericMessageEvent event, String[] args) {
        super(event, args);
    }


    @Override
    public void init() {
        triggers.add("fortune");
        triggers.add("f");
    }

    @Override
    public void run() {

        try {
            super.respond(String.format("<B><b>%s,<N> %s",event.getUser().getNick(), Utils.chooseRandomLine(new File("data/fun/fortunes.txt"))));
        } catch (FileNotFoundException e) {}


    }
}
