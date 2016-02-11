package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Seen.UserDB;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.Date;

public class Seen extends Command{

    public Seen(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {

        if (args.length == 1) {
            super.respond(String.format("<B><b>USAGE:<N> %s <USER>" ,args[0]));
            return;
        }

        String target = args[1];

        if (sender.equalsIgnoreCase(target)) {
            super.respond(String.format("<B><b>%s<N> you need too look in the mirror.", sender));
            return;
        }

        if (target.equalsIgnoreCase(event.getBot().getNick())) {
            super.respond(String.format("<B><b>%s<N> of course I am here.", sender));
            return;
        }

        if (event.getBot().getUserChannelDao().containsUser(target)) {
            User fetchUser = event.getBot().getUserChannelDao().getUser(target);

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
            super.respond(String.format("<B><b>%s<N> has not beeb seen by me",target));

        }
    }



}
