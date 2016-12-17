package com.brokeassgeeks.snackbot.listeners;

import com.brokeassgeeks.snackbot.DataManager;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;


import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class ServerActivityListener extends ListenerAdapter {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ServerActivityListener.class);

    @Override
    public void onMessage(MessageEvent event) {
        if (Utils.isServer(event.getUser().getNick())) {
            String bot  = event.getUser().getNick();
            String message = event.getMessage();
            String sender ="";
            boolean update = false;

            if (message.startsWith("<") && message.contains(">")) {
                sender = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
                message = message.replaceAll("^<.*?>", "").trim();
                update = true;
            }

            if (message.endsWith("the game") && !update) {
                String arr[] = message.split(" ", 2);
                sender = arr [0];
                message = arr[1];
                update = true;
            }

            if(update)
                DataManager.getInstance().updateServerActivity(bot,sender,System.currentTimeMillis());
        }
    }
}
