package com.brokeassgeeks.snackbot.commands.fun;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.BotCommand;
import org.jibble.pircbot.Colors;

import java.io.File;
import java.io.FileNotFoundException;

public class EightBall extends BotCommand {
    public EightBall() {
        super("8ball");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        if (args.length() > 0) {
            String s="";
            try {
                s = Utils.chooseRandomLine(new File("data/fun/8ball.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            s = s.replaceAll("<g>", Colors.GREEN);
            s = s.replaceAll("<r>", Colors.RED);
            s = s.replaceAll("<y>", Colors.YELLOW);

            SnackBot.bot.sendAction(target, "shakes the magic 8 ball... " + s);

        } else {
            SnackBot.bot.sendMessage(target, "8ball <question>");
        }

    }


}
