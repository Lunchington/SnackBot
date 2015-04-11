package com.pantsareoffensive.snackbot.commands;

import com.pantsareoffensive.snackbot.SnackBot;
import org.jibble.pircbot.Colors;

public class Helper extends BotCommand
{
    public Helper() {
        super("help");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String output = "";
        for (BotCommand b: SnackBot.bot.commands ) {
            if (b != this)
                output += Colors.BOLD + b.getCommandName() + Colors.NORMAL + " - " + b.getDesc() + " ";

        }
        SnackBot.bot.sendMessage(target,output);

    }

}
