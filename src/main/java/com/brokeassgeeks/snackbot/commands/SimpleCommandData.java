package com.brokeassgeeks.snackbot.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SimpleCommandData {
    @Getter private String name;
    @Getter private List<String> triggers;
    @Getter@Setter private String output;
    @Getter@Setter private boolean privateMessage;

    public SimpleCommandData(String name, String output) {
        this.triggers = new ArrayList<>();
        this.name = name;
        this.triggers.add(name);
        this.output = output;
        this.privateMessage = false;
    }
}
