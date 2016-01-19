package com.brokeassgeeks.snackbot.commands;

public class TeamSpeak extends BotCommand {
    public TeamSpeak() {

        super("ts");
        this.setDesc("Show teamspeak info");
    }

    @Override
    public void handleMessage(String target, String sender, String login, String hostname, String args) {
        super.sendMessage(target, "<B>TeamSpeak info: <N>ts.breakfastcraft.com password: wilson");

    }
}
