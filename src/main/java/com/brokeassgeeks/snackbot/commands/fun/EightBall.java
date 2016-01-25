package com.brokeassgeeks.snackbot.commands.fun;


import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class EightBall extends Command {


    public EightBall(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("8ball");
        triggers.add("8");
    }

    @Override
    public void run() {
        if (!(event instanceof MessageEvent))
            return;
        String out = "";

        if (args.length == 1) {
            out = String.format("<B><b>USAGE:<N> %s <QUESTION>", args[0]);
            ((MessageEvent)event).getChannel().send().message(Utils.replaceTags(out));
            return;
        }

        String s="";
        try {
            s = Utils.chooseRandomLine(new File("data/fun/8ball.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        out = String.format("shakes the magic 8 ball... %s",s);

        ((MessageEvent)event).getChannel().send().action(Utils.replaceTags(out));
    }
}
