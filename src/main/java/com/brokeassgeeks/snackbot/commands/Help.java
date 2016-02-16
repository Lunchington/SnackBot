package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Configuration.Config;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Help extends Command{
    public Help(GenericMessageEvent event, String[] args) {
        super(event, args);
    }

    @Override
    public void run() {
        ArrayList<String> temp = getHelp();

        int partitionSize = 5;
        for (int i = 0; i < temp.size(); i += partitionSize) {
            List<String> t = temp.subList(i,
                    Math.min(i + partitionSize, temp.size()));
            String out ="";

            for (String st : t) {
                out += st + " ";
            }

            super.respond(event.getUser(),out);


        }

    }

    public ArrayList<String> getHelp() {
        ArrayList<String> s = new ArrayList<>();
        for(CommandData c: CommandManager.getInstance().getCommandData()) {
            String temp = String.format("<B><b>%s:<N> %s ",c.getName(),c.getTriggers());
            s.add(temp);
        }
        return s;
    }

}
