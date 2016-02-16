package com.brokeassgeeks.snackbot.commands.admin;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Utils.Utils;
import com.brokeassgeeks.snackbot.commands.Command;
import com.brokeassgeeks.snackbot.commands.CommandData;
import com.brokeassgeeks.snackbot.commands.CommandManager;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.ArrayList;

public class SimpleCommandAdmin extends Command {
    private ArrayList<CommandData> sc;

    public SimpleCommandAdmin(GenericMessageEvent event, String[] args) {
        super(event, args);
        sc = CommandManager.getInstance().getCommandData();
    }

    @Override
    public void run() {
        if (!AdminUtils.isAdmin(event.getUser(), SnackBot.getAdmins()) || Utils.isBot(sender))
            return;

        if (args.length == 2 && args[1].equalsIgnoreCase("list")) {

            for (CommandData s : sc) {
                if (s.isSimple())
                    super.respond(String.format("<B><b>NAME:<N> %s <B><b>Triggers:<N> %s" ,s.getName(), s.getTriggers()));
            }

            return;
        }

        if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            CommandData sData = getCommandDataByName(args[2]);


            if (sData == null) {
                super.respond(String.format("Command <B><b>%s<N> doesnt exist!" ,args[2]));
            } else {
                if (sData.isSimple()) {
                    sc.remove(sData);
                    CommandManager.getInstance().write(sc);
                    super.respond(String.format("Command <B><b>%s<N> removed!", args[2]));
                } else {
                    super.respond(String.format("Cannot remove complex command: <B><b>%s<N>", args[2]));

                }
            }
            return;
        }

        if (args.length >= 3) {
            String msg[] = event.getMessage().split(" ", 3);

            CommandData sData = getCommandDataByName(msg[1]);


            if (sData == null) {
                CommandData newCommand = new CommandData(msg[1],msg[2]);

                if(event instanceof PrivateMessageEvent)
                    newCommand.setPrivateMessage(true);


                sc.add(newCommand);
                CommandManager.getInstance().write(sc);
                super.respond(String.format("Command <B><b>%s<N> added!" ,msg[1]));
            } else {
                if (sData.isSimple()) {

                    sData.setOutput(msg[2]);
                    CommandManager.getInstance().write(sc);
                    super.respond(String.format("Command <B><b>%s<N> output edited", msg[1]));
                }else {
                    super.respond(String.format("Cannot edit complex command: <B><b>%s<N>", args[1]));

                }
            }
            return;
        }

        super.respond(String.format("<B><b>USAGE:<N> %s <NAME> <OUTPUT>" ,args[0]));
    }
    public CommandData getCommandDataByName(String name) {
        for (CommandData s : sc) {
            if(s.getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }

}
