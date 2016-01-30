package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements Runnable{
    @Getter protected List<String> triggers;
    protected String[] args;
    protected GenericMessageEvent event;

    @Getter@Setter
    protected String sender;
    @Getter@Setter
    protected String message;

    public Command(GenericMessageEvent event, String[] args) {
        this.triggers = new ArrayList<>();
        this.event = event;
        this.args = args;
        this.sender = event.getUser().getNick();
        init();
    }

    public abstract void init();

    public void respond(String message) {
        event.respondWith(Utils.replaceTags(message));
    }

}
