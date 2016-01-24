package com.brokeassgeeks.snackbot.commands.fun;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.BotCommand;

import java.io.File;
import java.io.FileNotFoundException;

public class EightBall extends BotCommand {
    public EightBall() {
        super("8ball");
        setDesc("Ask the magic 8ball a question");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        if (args.length() == 0) {
            super.sendMessage(target, String.format("<B><b>USAGE:<N> %s <SERVER> <MOD>", getFullCmd()));
            return;
        }

        String s="";
        try {
            s = Utils.chooseRandomLine(new File("data/fun/8ball.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.sendAction(target, String.format("shakes the magic 8 ball... %s",s));
    }
}
