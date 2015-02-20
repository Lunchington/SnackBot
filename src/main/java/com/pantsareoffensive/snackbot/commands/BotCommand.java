package com.pantsareoffensive.snackbot.commands;


import com.pantsareoffensive.snackbot.Configuration.Config;

public abstract class BotCommand implements  IBotCommand {
    private String name;
    protected String json;


    public BotCommand(String name) {
        this.name = name;
        loadJson();
    }

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public String getFullCmd()
    {
        return Config.CATCH_CHAR + getCommandName();
    }

    @Override
    public void handlePrivateMessage(String sender, String login, String hostname, String message){

    }

    @Override
    public void handleMessage(String channel, String sender, String login, String hostname, String message) {

    }

    @Override
    public void reload() {
        loadJson();

    }

    @Override
    public void loadJson() {

    }

}
