package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.Configuration.Config;
import com.pantsareoffensive.snackbot.SnackBot;

import java.util.TimerTask;

public class TimedMessage extends TimerTask {
    private long time;
    private String message;
    private int count=0;


    public long getTime() { return this.time; }

    public void addCount(int i) { this.time += i; }

    public TimedMessage() {
    }

    public void run() {

        if (count >= Config.MESSAGECOUNT) {
            SnackBot.bot.sendMessage(Config.CHANNEL, message);
            count = 0;
        }

    }

}
