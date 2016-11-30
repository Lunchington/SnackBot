package com.brokeassgeeks.snackbot;


import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.listeners.DiscordBouncer;
import net.dv8tion.jda.events.Event;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.EventListener;

public class SnackbotDiscord implements EventListener {


    @Override
    public void onEvent(Event event)
    {
        if(event instanceof MessageReceivedEvent)
            try {
                onMessageReceived((MessageReceivedEvent) event);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void onMessageReceived(MessageReceivedEvent event) throws Exception {
        if (!event.isPrivate() && !event.getAuthor().isBot())
        {
            String mirror = DiscordBouncer.getMirror(event.getTextChannel().getName());
            if(event.getMessage().getContent().startsWith(Config.CATCH_CHAR))
                SnackBot.getCommandListener().onDiscord(event);
            else {
                if (mirror != null) {
                    if (SnackBot.getBot().getUserChannelDao().containsChannel(mirror)) {
                        SnackBot.getBot().getUserChannelDao().getChannel(mirror).send().message("<" + event.getAuthorName() + "> " + event.getMessage().getContent());
                    }
                }
            }
        }
    }
}
