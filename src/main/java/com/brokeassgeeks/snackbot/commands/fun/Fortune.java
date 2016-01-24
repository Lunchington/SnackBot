package com.brokeassgeeks.snackbot.commands.fun;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.BotCommand;

import java.io.File;
import java.io.FileNotFoundException;


public class Fortune extends BotCommand{
    public Fortune() {
        super("fortune");
        setDesc("get a super awesome fortune!");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String s="";
        try {
            s = Utils.chooseRandomLine(new File("data/fun/fortunes.txt"));
            System.out.println(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.sendMessage(target, String.format("<B><b>%s,<N> %s",sender, s));
    }
}
