package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.SnackBot;

import java.util.TimerTask;

public class TimedMessage extends TimerTask {
    public long time;
    public String message;
    public int count=0;

    public TimedMessage() {
    }

    public void run() {

        if (count >= Config.MESSAGECOUNT) {
            SnackBot.bot.sendMessage(Config.CHANNEL, message);
            count = 0;
        }

    }
}
