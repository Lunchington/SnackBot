package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements Runnable{
    protected String[] args;
    protected GenericMessageEvent event;

    @Getter@Setter
    protected String sender;
    @Getter@Setter
    protected String message;

    public Command(GenericMessageEvent event, String[] args) {
        this.event = event;
        this.args = args;
        this.sender = event.getUser().getNick();
    }

    public void respond(String message) {
        event.respondWith(Utils.replaceTags(message));
    }

}
