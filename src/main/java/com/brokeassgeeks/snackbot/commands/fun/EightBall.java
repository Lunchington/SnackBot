package com.brokeassgeeks.snackbot.commands.fun;


import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class EightBall extends Command {


    public EightBall(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (!(event instanceof MessageEvent))
            return;

        if (args.length == 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <QUESTION>", args[0]));
            return;
        }

        String s="";
        try {
            s = Utils.chooseRandomLine(new File("data/fun/8ball.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String out = String.format("shakes the magic 8 ball... %s",s);
        ((MessageEvent)event).getChannel().send().action(Utils.replaceTags(out));
    }
}
