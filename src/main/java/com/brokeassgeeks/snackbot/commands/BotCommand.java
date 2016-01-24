package com.brokeassgeeks.snackbot.commands;


import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;

public abstract class BotCommand implements  IBotCommand {
    public String name;
    protected String json;
    public String desc;


    public BotCommand(String name) {
        this.name = name;
        loadJson();
    }

    @Override
    public String getFullCmd()
    {
        return Config.CATCH_CHAR + this.name ;
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


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void sendMessage(String target, String message) {
        SnackBot.bot.sendMessage(target, Utils.replaceTags(message));
    }
    public void sendAction(String target, String message) {
        SnackBot.bot.sendAction(target, Utils.replaceTags(message));
    }
}

