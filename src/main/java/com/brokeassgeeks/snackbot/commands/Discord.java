package com.brokeassgeeks.snackbot.commands;

public class Discord extends BotCommand{

    public Discord() {

        super("discord");
        this.setDesc("Show discord info");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        super.sendMessage(target, "<B>Discord info:<N> https://discord.gg/0b0oQx8E6QU63UCV");
    }
}
