package com.brokeassgeeks.snackbot.commands;

import org.jibble.pircbot.PircBot;

public interface IBotCommand {

    public String getCommandName();
    public String getFullCmd();

    public void handlePrivateMessage(String sender, String login, String hostname, String message);
    public abstract void handleMessage(String channel, String sender, String login, String hostname, String message);

    public void reload();
    public void loadJson();

}
