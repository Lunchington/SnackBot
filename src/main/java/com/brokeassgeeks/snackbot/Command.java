package com.brokeassgeeks.snackbot;

import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Utils.MessageUtils;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public abstract class Command implements Runnable{
    protected String[] args;
    @Getter protected GenericMessageEvent ircEvent;
    @Getter protected MessageReceivedEvent discordEvent;

    public Command(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) {
        this.ircEvent = ircEvent;
        this.discordEvent = discordEvent;
        this.args = args;
    }


    public void respond(String message) {
        if (isFromDiscord())
            respondDiscord(message);
        else
            respondIRC(message);
    }

    public void action(String message) {
        if(isFromDiscord())
            discordEvent.getTextChannel().sendMessage(String.format("*%s %s*",getSender(),MessageUtils.replaceTagsDiscord(getMessage())));
        else
            ((MessageEvent)ircEvent).getChannel().send().action(MessageUtils.replaceTags(getMessage()));
    }

    private void respondIRC(String message) {
            ircEvent.respondWith(MessageUtils.replaceTags(message));
    }

    private void respondDiscord(String message) {
        discordEvent.getChannel().sendMessage("```\n" + MessageUtils.replaceTagsDiscord(message) + "```\n");
    }

    private void respondDiscordPlain(String message) {
        discordEvent.getChannel().sendMessage(MessageUtils.replaceTagsDiscord(message));
    }

    protected void respondUser(String message) {
        if (isFromDiscord())
            discordEvent.getPrivateChannel().sendMessage(message);
        else
            ircEvent.getUser().send().message(MessageUtils.replaceTags(message));
    }

    public boolean isFromDiscord() {
        return discordEvent != null;
    }

    public Boolean isAdminCommand() {
        return false;
    }

    public abstract void processCommand();

    @Override
    public void run() {
        if (isAdminCommand() && AdminUtils.isAdmin(this))
                processCommand();
        else
            processCommand();
    }

    protected String getSender() {
        if (isFromDiscord())
            return discordEvent.getAuthorNick();
        else
            return ircEvent.getUser().getNick();
    }

    protected String getChannel() {
        if (isFromDiscord())
            return discordEvent.getTextChannel().getName();
        else
            return ((MessageEvent) ircEvent).getChannel().getName();
    }

    protected String getMessage() {
        if(isFromDiscord())
            return discordEvent.getMessage().getContent();
        else
            return ircEvent.getMessage();
    }


}
