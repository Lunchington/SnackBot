package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.Command;
import com.brokeassgeeks.snackbot.CommandData;
import com.brokeassgeeks.snackbot.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.List;

public class TriggerAdmin extends Command {
    private List<CommandData> sc;

    public TriggerAdmin(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) {
        super(ircEvent,discordEvent, args);
        sc = CommandManager.getInstance().getCommandData();
    }
    @Override
    public void processCommand() {

        if (args[0].equalsIgnoreCase("getcommand")) {
            if (args.length < 2) {
                super.respond(String.format("<B><b>USAGE:<N> %s <TRIGGER>",args[0]));
            } else {
                CommandData data = CommandManager.getInstance().getCommandDataFromTrigger(args[1]);
                if (data != null) {
                    super.respond(String.format("<B><b>NAME:<N> %s <B><b>Triggers:<N> %s", data.getName(), data.getTriggers()));
                } else {
                    super.respond(String.format("No such command <B><b>%s<N>",args[1]));

                }
            }
            return;
        }

        if (args[0].equalsIgnoreCase("addtrigger")) {
            if (args.length < 3) {
                super.respond("<B><b>USAGE:<N> `addtrigger  <COMMAND> <TRIGGER>");
                return;
            }

            CommandData data = getCommandDataByName(args[1]);
            if (data != null) {
                if (data.getName().equalsIgnoreCase(this.getClass().getSimpleName())) {
                    super.respond(String.format("Cannot add triggers to <B><b>%s<N>",args[1]));
                } else {
                    CommandData triggerIn = CommandManager.getInstance().triggerExists(args[2]);
                    if (triggerIn != null) {
                        super.respond(String.format("<B><b>Trigger:<N> <B>%s<N>  exists in <B><b>%s<N>",args[2],triggerIn.getName()));

                    } else {
                        data.getTriggers().add(args[2]);
                        CommandManager.getInstance().writeReload(sc);
                        super.respond(String.format("<B><b>Trigger:<N> <B>%s<N> added for <B><b>%s<N> command",args[2],data.getName()));

                    }
                }
            } else {
                super.respond(String.format("No such command <B><b>%s<N>",args[1]));
            }
            return;
        }
        if (args[0].equalsIgnoreCase("removetrigger") ) {
            if (args.length < 2) {
                super.respond("<B><b>USAGE:<N> `removetrigger <TRIGGER>");
                return;
            }

            CommandData data = getCommandDataFromTrigger(args[1]);
            if (data != null) {
                if (data.getName().equalsIgnoreCase(this.getClass().getSimpleName())) {
                    super.respond(String.format("Cannot remove triggers from <B><b>%s<N>",data.getName()));
                } else {
                    data.getTriggers().remove(args[1].toLowerCase());

                    if (data.getTriggers().size() == 0)
                        data.getTriggers().add(data.getName());

                    CommandManager.getInstance().writeReload(sc);
                    super.respond(String.format("<B><b>Trigger:<N> <B>%s<N>  removed from <B><b>%s<N>",args[1],data.getName()));

                }
            } else {
                super.respond(String.format("No such trigger <B><b>%s<N>",args[1]));

            }
        }
    }

    private CommandData getCommandDataFromTrigger(String trigger) {
        for (CommandData s : sc) {
            if(s.getTriggers().contains(trigger))
                return s;
        }
        return null;
    }

    private CommandData getCommandDataByName(String name) {
        for (CommandData s : sc) {
            if(s.getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }
}
