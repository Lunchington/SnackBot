package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.UserDB;
import com.brokeassgeeks.snackbot.Utils.Utils;

import java.util.Date;

public class Seen extends BotCommand{
    public Seen() {
        super("seen");
        setDesc("see when a user was last online");

    }
    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        if (args.length() == 0) {
            super.sendMessage(channel, String.format("<B><b>USAGE:<N> %s <USER>" ,this.getFullCmd()));
            return;
        }

        if (sender.equalsIgnoreCase(args)) {
            super.sendMessage(channel, String.format("<B><b>[%s]<N> you need too look in the mirror.", sender));
            return;
        }

        if (args.equalsIgnoreCase(SnackBot.bot.getNick())) {
            super.sendMessage(channel, String.format("<B><b>[%s]<N> of course I am here.", sender));
            return;
        }

        if (SnackBot.bot.isUserInChannel(channel, args)) {
            if(SnackBot.bot.seenDataBase.isUserInDB(login+"@"+hostname) == 0) {
                SnackBot.bot.seenDataBase.processUserSeenRecord(channel,login, hostname, args, System.currentTimeMillis());
            }
            super.sendMessage(channel, String.format("<B><b>%s<N> is right here!", args));
            return;
        }

        UserDB userDB = SnackBot.bot.seenDataBase.getUserbyNick(args);

        if (userDB != null) {
            if (SnackBot.bot.isUserInChannel(channel, userDB.getLastNick())) {
                String output = String.format("<B><b>%s<N> is right here! Disguised as <B><b>%s<N>", args, userDB.getLastNick());
                super.sendMessage(channel, output);
            } else {

                Date currentDate = new Date(userDB.getTimeSeen());

                super.sendMessage(channel, String.format("I last saw <B><b>%s<N> in %s on: <B><b>%s<N>",args,channel, Utils.getTime(currentDate)));
            }
        } else {
            super.sendMessage(channel, String.format("I have not seen <B><b>%s<N>",args));

        }

    }
}
