package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.listeners.SeenActivityListener;
import org.pircbotx.Channel;
import org.pircbotx.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Timers extends Timer {
    private static final Logger logger = Logger.getLogger(Timers.class.getName());

    private User user;
    private Channel channel;
    private Long time;
    private TimerTask task;
    private boolean isDone;


    public Timers(User user, Channel channel, long time) {
        this.user = user;
        this.channel = channel;
        this.time = time;
        this.isDone = false;

    }

    public void startTells() {
        this.task = new TimerTask() {
            @Override
            public void run() {
                getTells();
                isDone = true;
            }
        };
        this.schedule(this.task,time);
    }

    private void end() {
        this.task.cancel();
    }

    private void getTells() {
        List<String> output = SnackBot.getSeenDataBase().getTells(user);

        if (output.size() > 0) {
            for(String s: output) {
                this.channel.send().message(Utils.replaceTags(s));
            }
        }
        SeenActivityListener.userActivity.remove(user);
    }

    public boolean isDone() {
        return isDone;
    }
}
