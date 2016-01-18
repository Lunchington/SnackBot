package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;


public class Discord extends BotCommand{

    public Discord() {

        super("discord");
        this.setDesc("Show discord info");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        String _output = "Discord info: ";
        _output += "https://discord.gg/0b0oQx8E6QU63UCV";

        SnackBot.bot.sendMessage(target, _output);

    }
}
