package com.brokeassgeeks.snackbot.commands;

import com.google.gson.Gson;
import lombok.Getter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class SimpleCommand extends Command{

    private SCommands[] scommands;
    @Getter private List<String> triggers;

    private class SCommands {
        @Getter private List<String> triggers;
        private String output;
        public String getOutput() {
            return output;
        }
    }

    public SimpleCommand(GenericMessageEvent event, String[] args) {
        super(event, args);
        init();
    }

    public void init() {
        load();
        for(SCommands s: scommands) {
            triggers.addAll(s.getTriggers());
        }
    }

    public void load() {
        try {
            Gson gson = new Gson();
            File file = new File("simplecommands.json");

            Reader jsonFile = new FileReader(file);
            scommands = gson.fromJson(jsonFile,SCommands[].class );
            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void run() {
        for (SCommands s : scommands) {
            if (s.getTriggers().contains(args[0].toLowerCase()))
                super.respond(s.getOutput());
        }
    }
}
