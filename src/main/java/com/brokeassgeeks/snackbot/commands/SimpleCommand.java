package com.brokeassgeeks.snackbot.commands;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimpleCommand extends Command{
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SimpleCommand.class);

    private ArrayList<SimpleCommandData> scommands;
    @Getter private List<String> triggers;


    public SimpleCommand(GenericMessageEvent event, String[] args) {
        super(event, args);
        this.triggers = new ArrayList<>();

        scommands = SimpleCommand.load();

        assert scommands != null;
        for(SimpleCommandData s: scommands) {
            triggers.addAll(s.getTriggers());
        }
    }

    public static ArrayList<SimpleCommandData> load() {
        try {
            ArrayList<SimpleCommandData> s;
            Gson gson = new Gson();
            File file = new File("data/simplecommands.json");

            Reader jsonFile = new FileReader(file);

            Type collectionType = new TypeToken<ArrayList<SimpleCommandData>>(){}.getType();

            s = gson.fromJson(jsonFile,collectionType );
            jsonFile.close();
            return s;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return null;
    }

    public static void write(ArrayList<SimpleCommandData>  commands) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(commands);

        try {
            String jsonFile = "data/simplecommands.json";
            FileOutputStream out = new FileOutputStream(jsonFile);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(s);
            writer.close();
            out.close();
        }  catch (IOException e) {
            logger.error("Cannot write simplecommands config...", e);
        }
    }

    @Override
    public void run() {
        for (SimpleCommandData s : scommands) {
            if (s.getTriggers().contains(args[0].toLowerCase())) {
                if (s.isPrivateMessage())
                    event.getUser().send().message(s.getOutput());
                else
                    super.respond(s.getOutput());
            }
        }
    }
}
