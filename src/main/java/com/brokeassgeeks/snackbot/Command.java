package com.brokeassgeeks.snackbot;

import com.brokeassgeeks.snackbot.Utils.Utils;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public abstract class Command implements Runnable{
    protected String[] args;
    @Getter protected GenericMessageEvent ircEvent;
    @Getter protected MessageReceivedEvent discordEvent;
    @Getter@Setter protected String sender;
    @Getter@Setter protected String message;

    public Command(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) {
        this.ircEvent = ircEvent;
        this.discordEvent = discordEvent;
        this.args = args;

        setDefaultSender();
    }

    private void setDefaultSender() {
        if (isFromDiscord())
            this.sender =  discordEvent.getAuthor().getUsername();
        else
            this.sender =  ircEvent.getUser().getNick();
    }


    public void respond(String message) {
        if (isFromDiscord())
            respondDiscord(message);
        else
            respondIRC(message);
    }

    private void respondIRC(String message) {
            ircEvent.respondWith(Utils.replaceTags(message));
    }

    private void respondDiscord(String message) {
        discordEvent.getChannel().sendMessage("```\n" + Utils.replaceTagsDiscord(message) + "```\n");
    }

    protected void respondUser(String message) {
        if (isFromDiscord())
            discordEvent.getPrivateChannel().sendMessage(message);
        else
            ircEvent.getUser().send().message(Utils.replaceTags(message));
    }

    public boolean isFromDiscord() {
        return discordEvent != null;
    }



}
