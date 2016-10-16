package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.CommandData;
import com.brokeassgeeks.snackbot.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;
import java.util.List;

public class Help extends Command {
    public Help(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void run() {
        if (isFromDiscord()) {
            return;
        }

        ArrayList<String> temp = getHelp();

        int partitionSize = 5;
        for (int i = 0; i < temp.size(); i += partitionSize) {
            String out ="";

            List<String> t = temp.subList(i,
                    Math.min(i + partitionSize, temp.size()));

            for (String st : t) {
                out += st + " ";
            }

            super.respondUser(out);


        }

    }

    private ArrayList<String> getHelp() {
        ArrayList<String> s = new ArrayList<>();
        for(CommandData c: CommandManager.getInstance().getCommandData()) {
            String temp = String.format("<B><b>%s:<N> %s ",c.getName(),c.getTriggers());
            s.add(temp);
        }
        return s;
    }

}
