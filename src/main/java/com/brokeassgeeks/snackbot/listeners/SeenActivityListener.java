package com.brokeassgeeks.snackbot.listeners;


import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

public class SeenActivityListener extends ListenerAdapter{

    @Override
    public void onMessage(MessageEvent event) {
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(),System.currentTimeMillis());
        for (String s : SnackBot.getSeenDataBase().getTells(event.getUser())){
            event.getChannel().send().message(Utils.replaceTags(s));
        }
    }

    @Override
    public void onJoin(JoinEvent event) {
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
        for (String s : SnackBot.getSeenDataBase().getTells(event.getUser())){
            event.getChannel().send().message(Utils.replaceTags(s));
        }
    }

    @Override
    public void onPart(PartEvent event) {
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
    }

    @Override
    public void onQuit(QuitEvent event) {
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
    }
}
