package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;

public class SimpleCommandAdmin extends Command {
    private ArrayList<SimpleCommandData> sc;

    public SimpleCommandAdmin(GenericMessageEvent event, String[] args) {
        super(event, args);
        sc = SimpleCommand.load();
    }

    @Override
    public void run() {
        if (!AdminUtils.isAdmin(event.getUser(), SnackBot.getAdmins()) || Utils.isBot(sender))
            return;

        if (args.length == 2 && args[1].equalsIgnoreCase("list")) {

            for (SimpleCommandData s : sc) {
                super.respond(String.format("<B><b>NAME:<N> %s: <B><b>Triggers:<N> %s" ,s.getName(), s.getTriggers()));
            }

            return;
        }

        if (args.length >= 3) {
            String msg[] = event.getMessage().split(" ", 3);

            SimpleCommandData sData = getCommandByName(msg[1]);
            if (sData == null) {
                SimpleCommandData newCommand = new SimpleCommandData(msg[1],msg[2]);

                if(event instanceof PrivateMessageEvent)
                    newCommand.setPrivateMessage(true);


                sc.add(newCommand);
                SimpleCommand.write(sc);
                super.respond(String.format("Command <B><b>%s<N> added!" ,msg[1]));
                return;
            } else {
                sData.setOutput(msg[2]);
                SimpleCommand.write(sc);
                super.respond(String.format("Command <B><b>%s<N> output edited" ,msg[1]));
                return;
            }

        }

        super.respond(String.format("<B><b>USAGE:<N> %s <NAME> <OUTPUT>" ,args[0]));
    }

    private SimpleCommandData getCommandByName(String name) {
        for (SimpleCommandData s : sc) {
            if(s.getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }
}
