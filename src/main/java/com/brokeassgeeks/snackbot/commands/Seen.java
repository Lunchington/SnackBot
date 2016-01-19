package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.SeenDataBase;
import org.jibble.pircbot.Colors;

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
                SnackBot.bot.sendMessage(channel, Colors.NORMAL + Colors.BOLD + args + Colors.NORMAL + " is right here in the channel." + Colors.NORMAL);
            } else {

                SeenDataBase.UserDB userDB = SnackBot.bot.seenDataBase.getUserbyNick(args);

                if (userDB != null) {
                    if (SnackBot.bot.isUserInChannel(channel, userDB.lastNick)) {
                        String output = Colors.NORMAL + Colors.BOLD + args + Colors.NORMAL + " is right here in the channel." + Colors.NORMAL;

                        if (!userDB.lastNick.equalsIgnoreCase(args))
                            output += " Disguised as " + Colors.BOLD + userDB.lastNick + Colors.NORMAL;

                        SnackBot.bot.sendMessage(channel, output);
                    } else {
                        Locale locale = Locale.getDefault();
                        TimeZone currentTimeZone = TimeZone.getDefault();

                        DateFormat formatter = DateFormat.getDateTimeInstance(
                                DateFormat.DEFAULT,
                                DateFormat.DEFAULT,
                                locale);
                        formatter.setTimeZone(currentTimeZone);
                        Date currentDate = new Date(userDB.timeSeen * 1000L);


                        SnackBot.bot.sendMessage(channel, Colors.NORMAL + "I last saw " + Colors.BOLD + args + Colors.NORMAL + " on :" + formatter.format(currentDate) + Colors.NORMAL);
                    }
                } else {
                    SnackBot.bot.sendMessage(channel, Colors.NORMAL + "I have not seen " + Colors.BOLD + args + Colors.NORMAL);

                }
            }

        } else {
            SnackBot.bot.sendMessage(channel, Colors.RED + "USAGE: " + Config.CATCH_CHAR + this.getCommandName() + "  <USER>" + Colors.NORMAL);

        }
    }
}
