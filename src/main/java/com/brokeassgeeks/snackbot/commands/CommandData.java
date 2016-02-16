package com.brokeassgeeks.snackbot.commands;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CommandData {
    @Getter protected String name;
    @Getter protected boolean enabled;
    @Getter protected List<String> triggers;
    @Getter@Setter protected boolean privateMessage;
    @Getter@Setter protected boolean simple;
    @Getter@Setter private String output;

    public CommandData(String name, String output) {
        this.triggers = new ArrayList<>();

        this.name = name;
        this.enabled = true;
        this.triggers.add(name);
        this.privateMessage = false;

        //ONLY USED FOR SIMPLE COMMANDS;
        this.simple = true;
        this.output = output;
    }

}
