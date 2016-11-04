package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.SnackBot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Tell extends Command {


    public Tell(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }
    @Override
    public void processCommand() {
        if (isFromDiscord()) {
            return;
        }

        if (args.length < 3 ) {
            super.respond(String.format("<B><b>USAGE:<N> %s <USER> <MESSAGE>" ,args[0]));
            return;
        }

        String target = args[1];

        if (sender.equalsIgnoreCase(target)) {
            super.respond(String.format("<B><b>%s<N> talking to yourself again?", sender));
            return;
        }

        if (target.equalsIgnoreCase(ircEvent.getBot().getNick())) {
            super.respond(String.format("<B><b>%s<N>you can just tell me directly.", sender));
            return;
        }


        if (ircEvent.getBot().getUserChannelDao().containsUser(target)) {
            super.respond( String.format("<B><b>%s<N> why dont you tell <B><b>%s yourself!", sender,target));
            return;
        }


        String tellMsg[] = ircEvent.getMessage().split(" ", 3);
        SnackBot.getSeenDataBase().addTell(target,String.format("<B><b>%s, %s <N>said: %s",target, sender,tellMsg[2]));
        super.respond(String.format("<B><b>%s <N>I will let <B><b>%s<N> know when i see them" ,sender,args[1]));


    }



}
