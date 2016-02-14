package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Tell extends Command{


    public Tell(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {

        if (args.length < 3 ) {
            super.respond(String.format("<B><b>USAGE:<N> %s <USER> <MESSAGE>" ,args[0]));
            return;
        }

        String target = args[1];

        if (sender.equalsIgnoreCase(target)) {
            super.respond(String.format("<B><b>%s<N> talking to yourself again?", sender));
            return;
        }

        if (target.equalsIgnoreCase(event.getBot().getNick())) {
            super.respond(String.format("<B><b>%s<N>you can just tell me directly.", sender));
            return;
        }


        if (event.getBot().getUserChannelDao().containsUser(target)) {
            super.respond( String.format("<B><b>%s<N> why dont you tell <B><b>%s yourself!", sender,target));
            return;
        }


        String tellMsg[] = event.getMessage().split(" ", 3);
        SnackBot.getSeenDataBase().addTell(target,String.format("<B><b>%s, %s <N>said: %s",target, sender,tellMsg[2]));
        super.respond(String.format("<B><b>%s <N>I will let <B><b>%s<N> know when i see them" ,sender,args[1]));


    }



}
