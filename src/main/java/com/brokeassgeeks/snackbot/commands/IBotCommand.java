package com.brokeassgeeks.snackbot.commands;

public interface IBotCommand {

    String getFullCmd();

    void handlePrivateMessage(String sender, String login, String hostname, String message);
    void handleMessage(String channel, String sender, String login, String hostname, String message);

    void reload();
    void loadJson();

}
