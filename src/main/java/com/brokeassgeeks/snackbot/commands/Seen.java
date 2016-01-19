package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.SeenDataBase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Seen extends BotCommand{
    public Seen() {
        super("seen");
        setDesc("see when a user was last online");

    }
    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        if (args.length() > 0) {
            if (SnackBot.bot.isUserInChannel(channel, args)) {
                if(SnackBot.bot.seenDataBase.isUserInDB(login+"@"+hostname) == 0) {
                    SnackBot.bot.seenDataBase.processNickRecord(login, hostname, args, System.currentTimeMillis() / 1000L);
                }
                super.sendMessage(channel, String.format("<B>%s<N> is right here!", args));
            } else {

                SeenDataBase.UserDB userDB = SnackBot.bot.seenDataBase.getUserbyNick(args);

                if (userDB != null) {
                    if (SnackBot.bot.isUserInChannel(channel, userDB.lastNick)) {
                        String output = String.format("<B>%s<N> is right here! ", args);

                        if (!userDB.lastNick.equalsIgnoreCase(args))
                            output += String.format("Disguised as <B>%s<N>",userDB.lastNick);

                        super.sendMessage(channel, output);
                    } else {
                        Locale locale = Locale.getDefault();
                        TimeZone currentTimeZone = TimeZone.getDefault();

                        DateFormat formatter = DateFormat.getDateTimeInstance(
                                DateFormat.DEFAULT,
                                DateFormat.DEFAULT,
                                locale);
                        formatter.setTimeZone(currentTimeZone);
                        Date currentDate = new Date(userDB.timeSeen * 1000L);


                        super.sendMessage(channel, String.format("I last saw <B>%s<N> on: <B>%s<N>",args,formatter.format(currentDate)));
                    }
                } else {
                    super.sendMessage(channel, String.format("I have not seen <B>%s<N>",args));

                }
            }

        } else {
            super.sendMessage(channel, String.format("<b>USAGE:<N> %s%s <USER>" ,Config.CATCH_CHAR,this.getCommandName()));

        }
    }
}
