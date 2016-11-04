package com.brokeassgeeks.snackbot.commands;

import com.brokeassgeeks.snackbot.SnackBot;
import com.brokeassgeeks.snackbot.Utils.AdminUtils;
import com.brokeassgeeks.snackbot.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class AdminCommand extends Command {
    public AdminCommand(GenericMessageEvent ircEvent, MessageReceivedEvent discordEvent, String[] args) { super(ircEvent,discordEvent, args);  }

    @Override
    public void processCommand() {

        if (args.length < 3) {
            super.respond(String.format("<B><b>USAGE:<N> %s add <USER>",args[0]));
        } else {

            if(ircEvent.getBot().getUserChannelDao().containsUser(args[2])) {
                User u = ircEvent.getBot().getUserChannelDao().getUser(args[2]);
                String uMask = String.format("%s!%s@%s",u.getNick(),u.getLogin(),u.getHostname());
                if(AdminUtils.isAdmin(u, SnackBot.getAdmins())) {
                    super.respond(String.format("<B><b>User:<N> %s is already an admin",u.getNick()));
                } else {
                    SnackBot.getAdmins().add(uMask);
                    AdminUtils.writeAdmins(SnackBot.getAdmins());
                    super.respond(String.format("<B><b>User:<N> %s added as BotAdmin",u.getNick()));
                }
            } else {
                super.respond(String.format("<B><b>Invalid user:<N> %s",args[2]));
            }
        }

    }
}
