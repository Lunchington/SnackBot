package com.brokeassgeeks.snackbot.commands.fun;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class Lart extends Command{

    public Lart(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void init() {
        triggers.add("lart");

    }

    @Override
    public void run() {

        String target = event.getUser().getNick();
        String s="";

        try {
            s = Utils.chooseRandomLine(new File("data/fun/lart.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (args.length > 1) {
            if (event.getBot().getUserChannelDao().containsUser(args[1]))
                target = args[1];
            else {
                super.respond(String.format("<B><b>%s<N> is not even here", args[1]));
                return;
            }
        }
        if (target.equalsIgnoreCase(event.getBot().getNick()))
            target = event.getUser().getNick();

        ((MessageEvent)event).getChannel().send().action(Utils.replaceTags(replaceNick(s,target)));
    }
    public static String replaceNick(String string,String target) {

        return  string.replaceAll("<user>","<B><b>"+target+"<N>");

    }

    }
