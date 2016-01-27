package com.brokeassgeeks.snackbot.listeners;


import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Timers;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;
import org.pircbotx.hooks.types.GenericUserEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SeenActivityListener extends ListenerAdapter{
    private static final Logger logger = Logger.getLogger(SeenActivityListener.class.getName());

    public static Map<String, Timers> userActivity = new HashMap<>();

    @Override
    public void onMessage(MessageEvent event) {
        processEvents(event);
        addTimer(event.getUser(),event.getChannel());

    }

    @Override
    public void onJoin(JoinEvent event) {
        processEvents(event);
        addTimer(event.getUser(),event.getChannel());
    }

    @Override
    public void onPart(PartEvent event) {
        processEvents(event);
        removeTimer(event.getUser().getNick());
    }

    @Override
    public void onQuit(QuitEvent event) {
        processEvents(event);
        removeTimer(event.getUser().getNick());
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        processEvents(event);
        removeTimer(event.getOldNick());
        addTimer(event.getUser(),null);
    }

    public void processEvents(GenericUserEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
    }

    public void addTimer(User user, Channel channel) {
        if(user == user.getBot().getUserBot() || !hasTells(user.getNick()))
            return;
        if (!userActivity.containsKey(user.getNick())) {
            logger.info("Creating timer for" + user.getNick());
            Timers timer = new Timers(user,channel,10000L);
            timer.startTells();
            userActivity.put(user.getNick(),timer);
        }
    }

    public boolean hasTells(String user) {
        return SnackBot.getSeenDataBase().getTellsbyNick(user).size() > 0;
    }

    public void removeTimer(String user) {
        if (userActivity.containsKey(user)) {
            userActivity.get(user).cancel();
            userActivity.remove(user);
        }
    }
}
