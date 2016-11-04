package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Seen.UserDB;
import com.brokeassgeeks.snackbot.Utils.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.Date;

public class Seen extends Command {

    public Seen(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {
        if (isFromDiscord()) {
            return;
        }

        if (args.length == 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <USER>" ,args[0]));
            return;
        }

        String target = args[1];

        if (sender.equalsIgnoreCase(target)) {
            super.respond(String.format("<B><b>%s<N> you need too look in the mirror.", sender));
            return;
        }

        if (target.equalsIgnoreCase(ircEvent.getBot().getNick())) {
            super.respond(String.format("<B><b>%s<N> of course I am here.", sender));
            return;
        }

        if (ircEvent.getBot().getUserChannelDao().containsUser(target)) {
            User fetchUser = ircEvent.getBot().getUserChannelDao().getUser(target);

            if(SnackBot.getSeenDataBase().isUserInDB(fetchUser) == 0)
                SnackBot.getSeenDataBase().processUserSeenRecord(fetchUser,System.currentTimeMillis());

            super.respond( String.format("<B><b>%s<N> is right here!", target));
            return;
        }

        UserDB userDB = SnackBot.getSeenDataBase().getUserbyNick(target);

        if (userDB != null && userDB.getTimeSeen() > 0) {
            Date currentDate = new Date(userDB.getTimeSeen());
            super.respond(String.format("<B><b>%s<N> was last seen on: <B><b>%s<N>",target, Utils.getTime(currentDate)));

        } else {
            super.respond(String.format("<B><b>%s<N> has not been seen by me",target));

        }
    }



}
