package com.brokeassgeeks.snackbot.commands;

import com.almworks.sqlite4java.SQLiteConnection;
import com.brokeassgeeks.snackbot.SnackBot;
import org.jibble.pircbot.Colors;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Time extends BotCommand{
    private SQLiteConnection _db;

    public Time() {
        super("time");
        setDesc("get the time in any timezone.");
    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        String time ="ERROR";
        String newZone="ERROR";
        int offset = 0;

        if (args.length() > 0) {
            String[] d = new String[0];

            if (args.contains("-")) {
                d = args.split("\\-");
                d[1] = "-" + d[1];
            }else if (args.contains("+")) {
                d = args.split("\\+");
                d[1] = "+" + d[1];
            }

            if (d.length > 0) {
                newZone = getTimeZoneProper(d[0]);
                offset = Integer.parseInt(d[1]);

            } else {
                newZone= getTimeZoneProper(args);
            }


            if (newZone != "ERROR")
                time = getTimewithZone(newZone,offset);

            if (time == "ERROR") {
                SnackBot.bot.sendMessage(channel, Colors.RED + "INVALID ZONE: " + Colors.BOLD + args+ Colors.NORMAL );
                SnackBot.bot.sendMessage(channel,  Colors.GREEN + "Local Time: " + Colors.NORMAL + getTimewithZone("America/New_York") );
            }
            else
                SnackBot.bot.sendMessage(channel,  Colors.GREEN + "Time for " + args + ": "+ Colors.NORMAL + time );

        }
        else {
            SnackBot.bot.sendMessage(channel,  Colors.GREEN + "Local Time: " + Colors.NORMAL + getTimewithZone("America/New_York") );
        }
    }

    public String getTimeZoneProper(String string) {
        String[] validIDs = TimeZone.getAvailableIDs();
        for (String str : validIDs) {
            if (str != null && str.equalsIgnoreCase(string)) {
                return str;
            }
        }
        return "ERROR";
    }

    public String getTimewithZone(String zone) {

        Locale locale = Locale.getDefault();
        TimeZone currentTimeZone = TimeZone.getDefault();

        DateFormat formatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT,
                DateFormat.DEFAULT,
                locale);
        formatter.setTimeZone(currentTimeZone);

        Date currentDate = new Date();
        formatter.setTimeZone(TimeZone.getTimeZone(zone));

        return formatter.format(currentDate);
    }


    public String getTimewithZone(String zone, int off) {
        long hour = 3600 * 1000;
        long offset = hour * off;

        Locale locale = Locale.getDefault();
        TimeZone currentTimeZone = TimeZone.getDefault();

        DateFormat formatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT,
                DateFormat.DEFAULT,
                locale);
        formatter.setTimeZone(currentTimeZone);

        Date currentDate = new Date();
        formatter.setTimeZone(TimeZone.getTimeZone(zone));
        currentDate.setTime(currentDate.getTime() + offset);

        return formatter.format(currentDate);
    }


    @Override
    public void loadJson() {


    }

}

