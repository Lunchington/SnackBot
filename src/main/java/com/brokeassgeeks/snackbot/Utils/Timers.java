package com.brokeassgeeks.snackbot.Utils;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.listeners.SeenActivityListener;
import org.pircbotx.Channel;
import org.pircbotx.User;

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
        List<String> output = SnackBot.getSeenDataBase().getTells(user.getNick());

        if (output.size() > 0) {
            if (output.size() > 1) {
                String paste="";
                for(String s: output) {
                    paste += s +"\r\n";
                }
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String s = String.format("You have %s messages. Get them here: %s",output.size(),Utils.putPaste(paste));

                    this.user.send().message(Utils.replaceTags(s));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            else {
                if (channel == null) {
                    this.user.send().message(Utils.replaceTags(output.get(0)));
                } else {
                    this.channel.send().message(Utils.replaceTags(output.get(0)));
                }

            }

        }
        SeenActivityListener.userActivity.remove(user.getNick());
    }

    public boolean isDone() {
        return isDone;
    }
}
