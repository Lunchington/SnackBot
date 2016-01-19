package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;

public class Helper extends BotCommand
{
    public Helper() {
        super("help");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        super.sendMessage(target, String.format("<B>HELP: <N> %s", getHelp()));
    }

    public String getHelp() {
        String output = "";
        for (BotCommand b: SnackBot.bot.commands ) {
            if (b != this)
                output += String.format("<b><B>%s<N> - %s ",b.getCommandName() , b.getDesc());

        }
        return Utils.removeLastChar(output);
    }

}
