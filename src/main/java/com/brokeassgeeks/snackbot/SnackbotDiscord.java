package com.brokeassgeeks.snackbot;


import com.brokeassgeeks.snackbot.listeners.DiscordBouncer;
import net.dv8tion.jda.events.Event;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.EventListener;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.pircbotx.Channel;

import javax.security.auth.login.LoginException;

/**
 * Created by Lunchington on 10/4/2016.
 */
public class SnackbotDiscord implements EventListener {


    @Override
    public void onEvent(Event event)
    {
        if(event instanceof MessageReceivedEvent)
            onMessageReceived((MessageReceivedEvent) event);
    }

    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (!event.isPrivate() && !event.getMessage().getContent().startsWith("["))
        {
            String mirror = DiscordBouncer.getMirror(event.getTextChannel().getName());
            if ( mirror != null) {
                if (SnackBot.getBot().getUserChannelDao().containsChannel(mirror))
                    SnackBot.getBot().getUserChannelDao().getChannel(mirror).send().message("[Discord] <" + event.getAuthor().getUsername() + "> " + event.getMessage().getContent());
            }
        }
    }
}
