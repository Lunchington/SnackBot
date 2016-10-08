package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.listeners.DiscordBouncer;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

public abstract class Command implements Runnable{
    protected String[] args;
    protected GenericMessageEvent ircEvent;
    protected MessageReceivedEvent discordEvent;


    @Getter@Setter
    protected String sender;
    @Getter@Setter
    protected String message;

    public Command(GenericMessageEvent event, String[] args) {
        this.ircEvent = event;
        this.discordEvent = null;
        this.args = args;
        this.sender = event.getUser().getNick();
    }
    public Command(MessageReceivedEvent event, String[] args) {
        this.ircEvent = null;
        this.discordEvent = event;
        this.args = args;
        this.sender = event.getAuthor().getUsername();
    }


    public void respond(String message) {
        if (isFromDiscord())
            respondDiscord(message);
        else
            respondIRC(message);
    }

    public void respondIRC(String message) {
            ircEvent.respondWith(Utils.replaceTags(message));
    }

    public void respondDiscord (String message) {
        discordEvent.getChannel().sendMessage("```\n" + Utils.replaceTagsDiscord(message) + "```\n");
    }

    public void respond(User user, String message) {
            ircEvent.getUser().send().message(Utils.replaceTags(message));
    }

    public boolean isFromDiscord() {
        return discordEvent != null;
    }

}
