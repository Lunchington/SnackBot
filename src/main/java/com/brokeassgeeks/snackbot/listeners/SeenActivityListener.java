package com.brokeassgeeks.snackbot.listeners;


import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.Timers;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SeenActivityListener extends ListenerAdapter{
    private static final Logger logger = Logger.getLogger(SeenActivityListener.class.getName());

    public static Map<User, Timers> userActivity = new HashMap<>();

    @Override
    public void onMessage(MessageEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(),System.currentTimeMillis());

    }

    @Override
    public void onJoin(JoinEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
        if (!userActivity.containsKey(event.getUser())) {
            logger.info("Creating timer for" + event.getUser());
            Timers timer = new Timers(event.getUser(),event.getChannel(),10000L);
            timer.startTells();
            userActivity.put(event.getUser(),timer);
        }
    }

    @Override
    public void onPart(PartEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
        if (userActivity.containsKey(event.getUser())) {
                userActivity.get(event.getUser()).cancel();
                userActivity.remove(event.getUser());
        }
    }

    @Override
    public void onQuit(QuitEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
        if (userActivity.containsKey(event.getUser())) {
            userActivity.get(event.getUser()).cancel();
            userActivity.remove(event.getUser());
        }
    }

    @Override
    public void onNickChange(NickChangeEvent event) {
        if(event.getUser() == event.getBot().getUserBot())
            return;
        SnackBot.getSeenDataBase().processUserSeenRecord(event.getUser(), System.currentTimeMillis());
    }


}
