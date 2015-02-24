package com.pantsareoffensive.snackbot.Utils;

import com.google.gson.Gson;
import com.pantsareoffensive.snackbot.commands.TimedMessage;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Timer;

/**
 * Created by Lunchbox on 2/24/2015.
 */
public class MessageQueue {
    public TimedMessage[] tMsg;
    public Timer timer;

    public MessageQueue() {
        loadJson();
    }
    public void loadJson() {
        try {
            Gson gson = new Gson();
            File file = new File("data/messages.json");

            Reader jsonFile = new FileReader(file);

            this.tMsg = gson.fromJson(jsonFile, TimedMessage[].class);

            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        timer = new Timer();

        for(TimedMessage t: tMsg) {
            timer.schedule(t, 0, t.time * 1000);
        }
    }

    public void sendNewMsg() {
        for(TimedMessage t: tMsg) {
            t.count++;
        }
    }
}
