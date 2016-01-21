package com.brokeassgeeks.snackbot.commands;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Time extends BotCommand{

    public Time() {
        super("time");
        setDesc("get the time in any timezone.");
    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String args) {
        if (args.length() == 0) {
            super.sendMessage(channel, String.format("<g>Local Time: <N> %s", getTimewithZone("America/New_York",0)));
            return;
        }

        String time ="ERROR";
        String newZone="ERROR";
        int offset = 0;
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
            super.sendMessage(channel, String.format("<r>INVALID ZONE: <B>%s<N>", args));
            super.sendMessage(channel, String.format("<g>Local Time: <N> %s", getTimewithZone("America/New_York",0)));
        }
        else
            super.sendMessage(channel,   String.format("<g>Time for %s: <N>%s",args ,time ));

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

    public String getTimewithZone(String zone, int off) {
        long hour = 3600 * 1000;

        Locale locale = Locale.getDefault();
        TimeZone currentTimeZone = TimeZone.getDefault();

        DateFormat formatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT,
                DateFormat.DEFAULT,
                locale);
        formatter.setTimeZone(currentTimeZone);

        Date currentDate = new Date();
        formatter.setTimeZone(TimeZone.getTimeZone(zone));
        currentDate.setTime(currentDate.getTime() + (off*hour));

        return formatter.format(currentDate);
    }


    @Override
    public void loadJson() {


    }

}

