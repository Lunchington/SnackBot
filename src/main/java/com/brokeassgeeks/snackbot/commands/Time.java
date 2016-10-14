package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Time extends Command {


    public Time(GenericMessageEvent event, String[] args) {
        super(event, args);
    }
    public Time(MessageReceivedEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        if(args.length == 1) {
            super.respond(String.format("<B><b>Local Time: <N> %s", getTimewithZone("America/New_York",0)));
            return;
        }

        String time ="ERROR";
        String newZone;

        int offset = 0;
        String[] d = new String[0];

        if (args[1].contains("-")) {
            d = args[1].split("\\-");
            d[1] = "-" + d[1];
        }else if (args[1].contains("+")) {
            d = args[1].split("\\+");
            d[1] = "+" + d[1];
        }

        if (d.length > 0) {
            newZone = getTimeZoneProper(d[0]);
            offset = Integer.parseInt(d[1]);

        } else {
            newZone= getTimeZoneProper(args[1]);
        }


        if (!newZone.equalsIgnoreCase("ERROR"))
            time = getTimewithZone(newZone,offset);

        if (time.equalsIgnoreCase("ERROR")) {
            super.respond(String.format("<B><r>INVALID ZONE:<N> %s", args[1]));
            super.respond(String.format("<B><b>Local Time:<N> %s", getTimewithZone("America/New_York",0)));
        }
        else
            super.respond(String.format("<B><b>Time for %s:<N> %s",args[1] ,time ));


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
        currentDate.setTime(currentDate.getTime() + (off * hour));

        return formatter.format(currentDate);
    }

}

