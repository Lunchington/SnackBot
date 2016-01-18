package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;

public class TeamSpeak extends BotCommand {
    public TeamSpeak() {

        super("ts");
        this.setDesc("Show teamspeak info");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String _output = "TeamSpeak info: ";
       _output += "ts.breakfastcraft.com";
        _output += " password: wilson";

        SnackBot.bot.sendMessage(target, _output);

    }
}
